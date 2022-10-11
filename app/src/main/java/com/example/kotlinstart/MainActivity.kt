package com.example.kotlinstart

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var textView: TextView
    private lateinit var firstButton: Button
    private lateinit var secondButton: Button
    private val creatorColor = CreatorColorImp(this)
    private val textData = TextData()


    @SuppressLint("SetTextI18n", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.tv)
        firstButton = findViewById(R.id.button1)
        secondButton = findViewById(R.id.button2)
        val thirdButton: Button = findViewById(R.id.button3)

        setTextData(Greeting.greeting)
        setText(textData)

        firstButton.setOnClickListener(this)
        secondButton.setOnClickListener(this)
        thirdButton.setOnClickListener {
            setTextData ("Clicked ${thirdButton.text}")
            setText(textData)}
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(p0: View?) {
        if (p0 != null) {
            when(p0.id){
                firstButton.id -> setTextData ("Clicked ${firstButton.text}")
                secondButton.id -> setTextData ("Clicked ${secondButton.text}")
            }
            setText(textData)
        }
    }

    @SuppressLint("SetTextI18n")
    fun onClickFourthButton(view:View?){
        if (view!=null){
            val checkedColor:Colors = if (findViewById<RadioButton>(R.id.black).isChecked) Colors.BLACK
            else if (findViewById<RadioButton>(R.id.red).isChecked) Colors.RED
            else if (findViewById<RadioButton>(R.id.blue).isChecked) Colors.BLUE
            else if (findViewById<RadioButton>(R.id.yellow).isChecked) Colors.YELLOW
            else Colors.BLACK
            val textDataFourth = textData.copy(title = "Clicked ${(view as Button).text}",
                color = checkedColor )
            setText(textDataFourth)
        }
    }

    private fun setTextData (title: String){
        textData.title = title
        val radioButton: RadioButton = findViewById(findViewById<RadioGroup>(R.id.radio_group).checkedRadioButtonId)
        val checkedColor:Colors = creatorColor.getColor(radioButton.textColors.defaultColor)
        val colors:Set<Colors> = setOf(Colors.BLACK, Colors.RED, Colors.BLUE, Colors.YELLOW)

        for (Colors in colors){
            if (Colors == checkedColor){
                textData.color = Colors
            }
        }
    }

    private fun setText(textData: TextData){
        textView.setTextColor(creatorColor.getColor(textData.color))
        textView.text = textData.title
    }

}

