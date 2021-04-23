package com.redmadrobot.pinkman_ui.internal

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources

internal fun View.requireDrawable(@DrawableRes drawableRes: Int): Drawable {
    return requireNotNull(AppCompatResources.getDrawable(context, drawableRes)) {
        "Cannot inflate drawable from resource ID $drawableRes"
    }
}
