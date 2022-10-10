package com.example.kotlinstart

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var textView: TextView
    private lateinit var firstButton: Button
    private lateinit var secondButton: Button
    private lateinit var thirdButton: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.tv)
        firstButton = findViewById(R.id.button1)
        secondButton = findViewById(R.id.button2)
        thirdButton = findViewById(R.id.button3)
        firstButton.setOnClickListener(this)
        secondButton.setOnClickListener(this)
        thirdButton.setOnClickListener { textView.text = "Clicked ${thirdButton.text}" }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(p0: View?) {
        if (p0 != null) {
            when(p0.id){
                firstButton.id -> textView.text = "Clicked ${firstButton.text}"
                secondButton.id -> textView.text = "Clicked ${secondButton.text}"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun onClickFourthButton(view:View?){
        if (view!=null){
            textView.text = "Clicked ${findViewById<Button>(R.id.button4).text}"
        }
    }
}