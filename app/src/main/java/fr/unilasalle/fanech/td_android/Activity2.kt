package fr.unilasalle.fanech.td_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.unilasalle.fanech.td_android.databinding.Activity2Binding

class Activity2 : AppCompatActivity() {
    private lateinit var binding2: Activity2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = Activity2Binding.inflate(layoutInflater)
        setContentView(binding2.root)

        val textToDisplay = intent.extras?.getString("textToShow")
        binding2.textView.text = textToDisplay

    }
}