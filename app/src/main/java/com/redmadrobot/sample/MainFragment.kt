package com.redmadrobot.sample

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.redmadrobot.pinkman.Pinkman
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var pinkman: Pinkman

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (pinkman.isPinSet()) {
            pin_button.text = "Remove PIN"
            pin_button.setOnClickListener {
                pinkman.removePin()

                parentFragmentManager.beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit()
            }
        } else {
            pin_button.text = "Create PIN"
            pin_button.setOnClickListener { findNavController().navigate(R.id.createPinFragment) }
        }
    }
}
