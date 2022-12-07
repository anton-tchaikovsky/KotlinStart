package com.example.kotlinstart

enum class Colors {
    BLACK, RED{
        override fun toString(): String {
            return "RED"
        }
    }, BLUE, YELLOW{
        override fun toString(): String {
            return "YELLOW"
        }
    }
}