package com.redmadrobot.sample.create_pin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.redmadrobot.pinkman_ui.KeyClickListener
import com.redmadrobot.sample.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_pin_fragment.*

@AndroidEntryPoint
class CreatePinFragment : Fragment() {

    private val viewModel: CreatePinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_pin_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pinIsCreated.observe(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.mainFragment, false)
        }

        pin_view.onFilledListener = { viewModel.createPin(it) }
        keyboard.keyClickListener = KeyClickListener { pin_view.add(it) }
    }
}
