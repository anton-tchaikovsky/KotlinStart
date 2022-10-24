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

        // Создание функциональной переменной для создание button по id
        val thirdButton: (Int) -> Button = {
            findViewById(it)
        }

        // Установка начального текста в TextView пользовательского экрана
        setTextData("${Greeting.greeting} ${textData.titleStart}")
        setText(textData)

        firstButton.setOnClickListener(this)
        secondButton.setOnClickListener(this)

        //Повесили слушатель с помощью анонимного экземпляра класса и лямбда-выражения
        thirdButton(R.id.button3).setOnClickListener {
            setTextData("Clicked ${thirdButton(R.id.button3).text}")
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
    private fun <T:CharSequence> setTextData (title: T ){ // функция использует generic-тип
        textData.title = title.toString()
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
        // используем функцию apply
        textView.apply {
            setTextColor(creatorColor.getColor(textData.color))
            text = textData.title
        }
    }

    private fun cycle() {
        for (i in 0 until CreatorColorImp.colorsList.size) {
            if (i == 0) Log.i(TAG, creatorColor.title)
            Log.i(TAG, CreatorColorImp.colorsList[i].toString())
        }

        Log.i(TAG, "------------------------")

        for (i in (CreatorColorImp.colorsList.size - 1) downTo 0 step 1) {
            if (i == CreatorColorImp.colorsList.size - 1) Log.i(TAG, "${creatorColor.title} reverse")
            Log.i(TAG, CreatorColorImp.colorsList[i].toString())
        }

        Log.i(TAG, "------------------------")

        for (i in 0 until CreatorColorImp.colorsList.size step 2) {
            if (i == 0) Log.i(TAG, "${creatorColor.title} step2")
            Log.i(TAG, CreatorColorImp.colorsList[i].toString())
        }

        Log.i(TAG, "------------------------")

        Log.i(TAG, "${creatorColor.title} filter for Blue and Yellow")
        CreatorColorImp.colorsList.filter { it==Colors.YELLOW || it==Colors.BLUE}.forEach { Log.i(TAG, it.toString()) }

        Log.i(TAG, "------------------------")

        Log.i(TAG, "${creatorColor.title} transformer to String")
        CreatorColorImp.colorsList.map {it.toString()}.forEach { Log.i(TAG, it) }

        Log.i(TAG, "------------------------")

        Log.i(TAG, "${creatorColor.title} find Black")
        Log.i (TAG, CreatorColorImp.colorsList.find {it==Colors.BLACK}.toString())

        Log.i(TAG, "------------------------")

        Log.i(TAG, "${creatorColor.title} count BLACK and YELLOW")
        Log.i (TAG, CreatorColorImp.colorsList.count {it==Colors.BLACK || it==Colors.YELLOW}.toString())

        Log.i(TAG, "------------------------")

        Log.i(TAG, "Is ${creatorColor.title} any BLACK?" )
        Log.i (TAG, CreatorColorImp.colorsList.any{it==Colors.BLACK}.toString())

        Log.i(TAG, "------------------------")

        Log.i(TAG, "Is ${creatorColor.title} all BLACK?" )
        Log.i (TAG, CreatorColorImp.colorsList.all{it==Colors.BLACK}.toString())

        Log.i(TAG, "------------------------")

        // используем функцию run
        Log.i(TAG, "Is ${creatorColor.title} have YELLOW?" )
        Log.i (TAG, CreatorColorImp.colorsList.run { contains(Colors.YELLOW).toString()})


        Log.i(TAG, "------------------------")

        // используем функцию also
        Log.i(TAG, "${creatorColor.title} get title, get RED, get YELLOW" )
        creatorColor
            .also { Log.i(TAG, it.title) }
            .also {Log.i(TAG, it.getColor(android.R.color.holo_red_dark).toString())}
            .also {Log.i(TAG, it.getColor(android.R.color.holo_orange_light).toString())}

        Log.i(TAG, "------------------------")

        Log.i(TAG, "Return interval 'a'..'f' to the List")
        Log.i(TAG, ('a'..'f').toList().toString())

        Log.i(TAG, "------------------------")

        // используем функцию let
        Log.i(TAG, "Return the word begins with last letter from the List 'a'..'f'")
        Log.i(TAG, ('a'..'f').toList().last().let {"$it" +"unction" })

        Log.i(TAG, "------------------------")

        // используем функцию with
        Log.i(TAG, "StringFirst > StringSecond?")
        Log.i(TAG, with ("StringFirst"){
            length > "StringSecond".length
        }.toString())

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

