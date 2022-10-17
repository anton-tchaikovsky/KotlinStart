package com.example.kotlinstart

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val TAG = "MyLog"

class MainActivity : AppCompatActivity(), View.OnClickListener {
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

        // Установка начального текста в TextView пользовательского экрана
        setTextData("${Greeting.greeting} ${textData.titleStart}")
        setText(textData)

        firstButton.setOnClickListener(this)
        secondButton.setOnClickListener(this)

        //Повесили слушатель с помощью анонимного экземпляра класса и лямбда-выражения
        thirdButton.setOnClickListener {
            setTextData("Clicked ${thirdButton.text}")
            setText(textData)
        }

        //Работа с циклом for и интервалами
        cycle()

        //Проверка класса Greeting на то,что он синглтон
        checkingSingleton()

    }

    //Повесили слушателя с помощью имплементации View.OnClickListener для класса MainActivity
    @SuppressLint("SetTextI18n")
    override fun onClick(p0: View?) {
        if (p0 != null) {
            //Работаем с условием when
            when(p0.id){
                firstButton.id -> setTextData ("Clicked ${firstButton.text}")
                secondButton.id -> setTextData ("Clicked ${secondButton.text}")
            }
            setText(textData)
        }
    }

    //Повесили слушателя через layout
    @SuppressLint("SetTextI18n")
    fun onClickFourthButton(view:View?){
        if (view!=null){
            //Работа с условным выражением if/else
            val checkedColor:Colors = if (findViewById<RadioButton>(R.id.black).isChecked) Colors.BLACK
            else if (findViewById<RadioButton>(R.id.red).isChecked) Colors.RED
            else if (findViewById<RadioButton>(R.id.blue).isChecked) Colors.BLUE
            else if (findViewById<RadioButton>(R.id.yellow).isChecked) Colors.YELLOW
            else Colors.BLACK
            //Копирование экземпляра класса TextData
            val textDataFourth = textData.copy(title = "Clicked ${(view as Button).text}",
                color = checkedColor )
            setText(textDataFourth)


        }
    }

    // Метод обновляет данные в экземпляре TextData:title - из переданного атрибута, color - в зависимости от выбранной radioButton
    private fun setTextData (title: String){
        textData.title = title
        val radioButton: RadioButton = findViewById(findViewById<RadioGroup>(R.id.radio_group).checkedRadioButtonId)
        val checkedColor:Colors = creatorColor.getColor(radioButton.textColors.defaultColor)

        //Работа с for, как с foreach
        for (Colors in CreatorColorImp.colorsList) {
            if (Colors == checkedColor) {
                textData.color = Colors
            }
        }
    }

    // Метод заполняет TextView на пользовательском экране
    private fun setText(textData: TextData) {
        textView.setTextColor(creatorColor.getColor(textData.color))
        textView.text = textData.title
    }

    private fun cycle() {
        for (i in 0 until CreatorColorImp.colorsList.size) {
            if (i == 0) Log.i(TAG, "Colors")
            Log.i(TAG, CreatorColorImp.colorsList[i].toString())
        }

        Log.i(TAG, "------------------------")

        for (i in (CreatorColorImp.colorsList.size - 1) downTo 0 step 1) {
            if (i == CreatorColorImp.colorsList.size - 1) Log.i(TAG, "Colors reverse")
            Log.i(TAG, CreatorColorImp.colorsList[i].toString())
        }

        Log.i(TAG, "------------------------")

        for (i in 0 until CreatorColorImp.colorsList.size step 2) {
            if (i == 0) Log.i(TAG, "Colors step2")
            Log.i(TAG, CreatorColorImp.colorsList[i].toString())
        }

        Log.i(TAG, "------------------------")

        Log.i(TAG, "Colors filter for Blue and Yellow")
        CreatorColorImp.colorsList.filter { it==Colors.YELLOW || it==Colors.BLUE}.forEach { Log.i(TAG, it.toString()) }

        Log.i(TAG, "------------------------")

        Log.i(TAG, "Colors transformer to String")
        CreatorColorImp.colorsList.map {it.toString()}.forEach { Log.i(TAG, it) }

        Log.i(TAG, "------------------------")

        Log.i(TAG, "Colors find Black")
        Log.i (TAG, CreatorColorImp.colorsList.find {it==Colors.BLACK}.toString())

        Log.i(TAG, "------------------------")

        Log.i(TAG, "Colors count BLACK and YELLOW")
        Log.i (TAG, CreatorColorImp.colorsList.count {it==Colors.BLACK || it==Colors.YELLOW}.toString())

        Log.i(TAG, "------------------------")

        Log.i(TAG, "Is colors any BLACK?" )
        Log.i (TAG, CreatorColorImp.colorsList.any{it==Colors.BLACK}.toString())

        Log.i(TAG, "------------------------")

        Log.i(TAG, "Is colors all BLACK?" )
        Log.i (TAG, CreatorColorImp.colorsList.all{it==Colors.BLACK}.toString())

        Log.i(TAG, "------------------------")

        Log.i(TAG, "Return interval 'a'..'f' to List")
        Log.i(TAG, ('a'..'f').toList().toString())
    }

    private fun checkingSingleton() {
        Log.i(TAG, "------------------------")

        val greetingFirst = Greeting
        val greetingSecond = Greeting
        val isSameObject =
            if (greetingFirst === greetingSecond) "greetingFirst и greetingSecond ссылаются на один и тот же объект"
            else "greetingFirst и greetingSecond ссылаются на разные объекты"
        Log.i(TAG, isSameObject)

        val textDataFirst = TextData()
        val textDataSecond = TextData()
        Log.i(
            TAG,
            if (textDataFirst === textDataSecond) "textDataFirst и textDataSecond ссылаются на один и тот же объект"
            else "textDataFirst и textDataSecond ссылаются на разные объекты"
        )

    }

}

