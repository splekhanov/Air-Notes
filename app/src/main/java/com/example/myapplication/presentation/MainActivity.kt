package com.example.myapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.MainActivityBinding
import com.example.myapplication.presentation.home.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        val mainFragment = MainFragment()
//        replaceFragment(mainFragment)
    }

//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentTransition = supportFragmentManager.beginTransaction()
//        fragmentTransition.add(R.id.container, fragment)
//            .addToBackStack(fragment.javaClass.simpleName)
//        fragmentTransition.commit()
//    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
        finish()
    }
}
