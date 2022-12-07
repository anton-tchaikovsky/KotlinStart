package com.example.kotlinstart

import android.content.Context

class CreatorColorImp(private val context: Context) : CreatorColor {

     override fun getColor(color: Colors): Int {
        return when (color) {
            Colors.BLACK -> context.getColor(android.R.color.black)
            Colors.RED -> context.getColor(android.R.color.holo_red_dark)
            Colors.BLUE -> context.getColor(android.R.color.holo_blue_light)
            Colors.YELLOW -> context.getColor(android.R.color.holo_orange_light)
        }
    }

    override val title: String
        get() = "Colors"

    fun getColor (color: Int):Colors{
         return when (color) {
             context.getColor(android.R.color.black) -> Colors.BLACK
             context.getColor(android.R.color.holo_red_dark) -> Colors.RED
             context.getColor(android.R.color.holo_blue_light) -> Colors.BLUE
             context.getColor(android.R.color.holo_orange_light) -> Colors.YELLOW
             else -> Colors.BLACK
         }
     }

     companion object{
        lateinit var colorsList:List<Colors>
     }

     init {
         colorsList = listOf(Colors.BLACK, Colors.RED, Colors.BLUE, Colors.YELLOW)
     }

}