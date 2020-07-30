package com.redmadrobot.sample.input_pin

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.redmadrobot.sample.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_pin_fragment.*

@AndroidEntryPoint
class InputPinFragment : Fragment() {

    private val viewModel: InputPinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.input_pin_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pinIsValid.observe(
            viewLifecycleOwner,
            Observer { isValid ->
                if (isValid) {
                    findNavController().popBackStack(R.id.mainFragment, false)
                } else {
                    Toast.makeText(context, "Invalid PIN", Toast.LENGTH_SHORT).show()
                    @Suppress("MagicNumber")
                    Handler().postDelayed({ pin_view.empty() }, 500)
                }
            }
        )

        pin_view.onFilledListener = { viewModel.validatePin(it) }
        keyboard.keyboardClickListener = { pin_view.add(it) }
    }
}
