package com.redmadrobot.pinkman_ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes

class PinView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_CODE_LENGTH = 4
    }

    private var spaceBetween = -1
    private var itemWidth = -1
    private var itemHeight = -1

    @DrawableRes
    private var emptyDrawable = -1

    @DrawableRes
    private var filledDrawable = -1

    private var length = DEFAULT_CODE_LENGTH

    private val items = mutableListOf<View>()

    private var text = ""

    var onFilledListener: (String) -> Unit = {}

    var onTextChangesListener: (String) -> Unit = {}

    init {
        lateinit var attrsArray: TypedArray

        try {
            attrsArray = context.obtainStyledAttributes(attrs, R.styleable.PinView)
            with(attrsArray) {
                spaceBetween = getDimensionPixelSize(R.styleable.PinView_spaceBetween, -1)
                itemWidth = getDimensionPixelSize(R.styleable.PinView_itemWidth, -1)
                itemHeight = getDimensionPixelSize(R.styleable.PinView_itemHeight, -1)
                emptyDrawable = getResourceId(R.styleable.PinView_emptyDrawable, -1)
                filledDrawable = getResourceId(R.styleable.PinView_filledDrawable, -1)
                length = getInteger(R.styleable.PinView_length, DEFAULT_CODE_LENGTH)
            }
        } finally {
            attrsArray.recycle()
        }

        validateAttrs()

        initViews()
    }

    fun add(symbol: Char) {
        if (text.length == length) return

        text += symbol

        updateView()

        onTextChangesListener(text)
        checkIfFilled()
    }

    fun delete() {
        if (text.isEmpty()) return

        text = text.dropLast(1)

        updateView()

        onTextChangesListener(text)
    }

    fun empty() {
        text = ""

        updateView()

        onTextChangesListener(text)
    }

    fun setNewDrawables(
        @DrawableRes newEmptyDrawable: Int = emptyDrawable,
        @DrawableRes newFilledDrawable: Int = filledDrawable
    ) {
        emptyDrawable = newEmptyDrawable
        filledDrawable = newFilledDrawable
        updateView()
    }

    fun getValue() = text

    private fun initViews() {
        orientation = HORIZONTAL

        for (position in 0 until length) {
            val item = createItem(position)

            items.add(item)
            addView(item)
        }

        updateView()
    }

    private fun createItem(itemPosition: Int): View {
        val item = View(context)
        val layoutParams = LinearLayout.LayoutParams(itemWidth, itemHeight)

        if (itemPosition < length - 1) {
            layoutParams.marginEnd = spaceBetween
        }

        item.layoutParams = layoutParams

        return item
    }

    private fun updateView() {
        items.forEachIndexed { index, view ->
            val resource = if (index < text.length) filledDrawable else emptyDrawable

            view.setBackgroundResource(resource)
        }
    }

    private fun checkIfFilled() {
        if (text.length == length) onFilledListener(text)
    }

    private fun validateAttrs() {
        require(spaceBetween != -1) { "PinView: spaceBetween should be set" }
        require(itemWidth != -1) { "PinView: itemWidth should be set" }
        require(itemHeight != -1) { "PinView: itemHeight should be set" }
        require(emptyDrawable != -1) { "PinView: emptyDrawable should be set" }
        require(filledDrawable != -1) { "PinView: filledDrawable should be set" }
    }
}

