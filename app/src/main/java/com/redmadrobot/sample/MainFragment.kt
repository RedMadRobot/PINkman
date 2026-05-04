package com.redmadrobot.sample

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.navigation.fragment.findNavController
import com.redmadrobot.pinkman.Pinkman
import com.redmadrobot.sample.databinding.MainFragmentBinding
import com.redmadrobot.sample.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var pinkman: Pinkman

    private val viewBinding by viewBinding(MainFragmentBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (pinkman.isPinSet()) {
            findNavController().navigate(R.id.inputPinFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(viewBinding) {
        super.onViewCreated(view, savedInstanceState)

        if (pinkman.isPinSet()) {
            pinButton.text = "Remove PIN"
            pinButton.setOnClickListener {
                pinkman.removePin()

                parentFragmentManager.apply {
                    commitNow { detach(this@MainFragment) }
                    commitNow { attach(this@MainFragment) }
                }
            }
        } else {
            pinButton.text = "Create PIN"
            pinButton.setOnClickListener { findNavController().navigate(R.id.createPinFragment) }
        }
    }
}
