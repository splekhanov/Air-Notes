package com.example.myapplication.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.NoteEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var editTextPersonName: TextView
    private lateinit var startAppButton: Button
    private val vm: MainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm.clearTable()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAppButton = view.findViewById(R.id.startAppButton)
        startAppButton.isEnabled = false
        editTextPersonName = view.findViewById(R.id.editTextPersonName)
        editTextPersonName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                startAppButton.isEnabled = s.toString().trim().length >= 3
            }
        })

        startAppButton.setOnClickListener { view ->
            viewLifecycleOwner.lifecycleScope.launch {
                NoteEntity().apply{
                    note = editTextPersonName.text.toString()
                }.also { vm.saveNote(it) }
                Log.e("AAA", "Launched")
                replaceFragment(ColorChangerFragment.newInstance())
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentHome, fragment)
        fragmentTransition.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}