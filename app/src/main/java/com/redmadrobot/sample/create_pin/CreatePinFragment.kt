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
import com.redmadrobot.sample.databinding.CreatePinFragmentBinding
import com.redmadrobot.sample.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePinFragment : Fragment() {

    private val viewModel: CreatePinViewModel by viewModels()

    private val viewBinding by viewBinding(CreatePinFragmentBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_pin_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(viewBinding) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pinIsCreated.observe(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.mainFragment, false)
        }

        pinView.onFilledListener = { viewModel.createPin(it) }
        keyboard.keyClickListener = KeyClickListener { pinView.add(it) }
    }
}
