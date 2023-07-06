package com.example.myapplication.presentation

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ColorChangerFragment : Fragment() {

    private val vm: MainViewModel by viewModels<MainViewModel>()
    private lateinit var name: TextView
    private lateinit var colorChangeButton: Button
    private lateinit var counter : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        container?.removeAllViews()
        return inflater.inflate(R.layout.fragment_color_changer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = view.findViewById(R.id.name)
        counter = view.findViewById(R.id.counter)
        colorChangeButton = view.findViewById(R.id.colorChangeButton)

        viewLifecycleOwner.lifecycleScope.launch {
            name.text = vm.getNote()
        }
        name.setTextColor(Color.BLACK)

        colorChangeButton.setOnClickListener{ view ->
            val list = listOf(Color.GREEN, Color.RED, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.BLACK)
            var randomColor = list.random()
            while (name.currentTextColor === randomColor) {
                randomColor = list.random()
            }
            name.setTextColor(randomColor)
            val cntrCurrent = counter.text.toString().toInt() + 1
            counter.text = (cntrCurrent).toString()
            Log.e("AAA", "Name is: " + vm.getNames())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ColorChangerFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}