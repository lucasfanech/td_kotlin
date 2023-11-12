package fr.unilasalle.fanech.td_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.unilasalle.fanech.td_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = binding.btnIncrement
        val btnSend = binding.btnSend
        var counter = 0

        button.setOnClickListener(){
            counter++
            binding.newText.text = counter.toString()
        }

        btnSend.setOnClickListener(){
            val textToShow = binding.textToShow.text.toString()
            if (textToShow != ""){
                val intent = Intent(applicationContext, Activity2::class.java)
                intent.putExtra("textToShow", textToShow)
                startActivity(intent)

            }else{
                // affiche toast message vide
                val toast = Toast.makeText(applicationContext, "Message vide", Toast.LENGTH_SHORT)
                toast.show()

            }
        }

        // liste d'entiers de 0 à 30 à afficher dans une ListView
        val listInt = ArrayList<Int>()
        for (i in 0..30){
            listInt.add(i)
        }

        val recyclerView = binding.listInt
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = NumbersAdapter(listInt)

    }
}