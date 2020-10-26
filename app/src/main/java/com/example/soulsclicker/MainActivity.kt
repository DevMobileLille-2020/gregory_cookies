package com.example.soulsclicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private fun saveInfo(name: String, elem: Int) {
        var pref = getSharedPreferences("SOULS_CLICKER_DATA", MODE_PRIVATE)
        var editor = pref.edit()

        editor.putInt(name, elem)
        editor.apply()
    }

    private fun getSaveInfo(name: String): Int {
        var shared = getSharedPreferences("SOULS_CLICKER_DATA", MODE_PRIVATE)
        var stringTemp = shared.getInt(name, 0)

        return stringTemp
    }

    var score = 0
        set(value) {
            field = value
            score_text.text = "Score : $value"
            saveInfo("score", value)
        }

    var level = 1
        set(value) {
            field = value
            level_text.text = "Level : $value"
            saveInfo("level", value)
        }

    var stepLevel = 1000
        set(value) {
            field = value
            step_level.text = "$score/$value"
            saveInfo("stepLevel", value)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        score = getSaveInfo("score")
        level = getSaveInfo("level")
        stepLevel = getSaveInfo("stepLevel")

        demons_souls_icon.setOnClickListener() {
            score += level
            level_up.visibility = View.VISIBLE
        }

        dark_souls_icon.setOnClickListener() {
            score += level
            level_up.visibility = View.VISIBLE
        }

        dark_souls_2_icon.setOnClickListener() {
            score += level
            level_up.visibility = View.VISIBLE
        }

        dark_souls_3_icon.setOnClickListener() {
            score += level
            level_up.visibility = View.VISIBLE
        }

        level_up_button.setOnClickListener() {
            if (score > stepLevel) {
                level++
                score -= stepLevel
                stepLevel = 1000*level
                level_up.visibility = View.VISIBLE
            }
        }

        reset.setOnClickListener() {
            score = 0
            level = 1
            stepLevel = 1000
            val intent = intent
            finish()
            startActivity(intent)
        }

        val mainHandler = Handler(Looper.getMainLooper())

        if (level > 1) {
            mainHandler.post(object : Runnable {
                override fun run() {
                    mainHandler.postDelayed(this, 1000)
                    score += level
                }
            })
        }

        mainHandler.post(object : Runnable {
            override fun run() {
                mainHandler.postDelayed(this, 1000)
                level_up.visibility = View.INVISIBLE
            }
        })

        mainHandler.post(object : Runnable {
            override fun run() {
                mainHandler.postDelayed(this, 100)
                step_level.text = "$score/$stepLevel"
                level_up.text = "+$level"

                if (level >= 300) {
                    dark_souls_3_bg.visibility = View.VISIBLE
                    dark_souls_3_icon.visibility = View.VISIBLE
                    dark_souls_2_bg.visibility = View.INVISIBLE
                    dark_souls_2_icon.visibility = View.INVISIBLE
                    dark_souls_bg.visibility = View.INVISIBLE
                    dark_souls_icon.visibility = View.INVISIBLE
                    demons_souls_bg.visibility = View.INVISIBLE
                    demons_souls_icon.visibility = View.INVISIBLE
                }

                if (level in 200..299) {
                    dark_souls_3_bg.visibility = View.INVISIBLE
                    dark_souls_3_icon.visibility = View.INVISIBLE
                    dark_souls_2_bg.visibility = View.VISIBLE
                    dark_souls_2_icon.visibility = View.VISIBLE
                    dark_souls_bg.visibility = View.INVISIBLE
                    dark_souls_icon.visibility = View.INVISIBLE
                    demons_souls_bg.visibility = View.INVISIBLE
                    demons_souls_icon.visibility = View.INVISIBLE
                }

                if (level in 100..199) {
                    dark_souls_3_bg.visibility = View.INVISIBLE
                    dark_souls_3_icon.visibility = View.INVISIBLE
                    dark_souls_2_bg.visibility = View.INVISIBLE
                    dark_souls_2_icon.visibility = View.INVISIBLE
                    dark_souls_bg.visibility = View.VISIBLE
                    dark_souls_icon.visibility = View.VISIBLE
                    demons_souls_bg.visibility = View.INVISIBLE
                    demons_souls_icon.visibility = View.INVISIBLE
                }

                if (level in 1..99) {
                    dark_souls_3_bg.visibility = View.INVISIBLE
                    dark_souls_3_icon.visibility = View.INVISIBLE
                    dark_souls_2_bg.visibility = View.INVISIBLE
                    dark_souls_2_icon.visibility = View.INVISIBLE
                    dark_souls_bg.visibility = View.INVISIBLE
                    dark_souls_icon.visibility = View.INVISIBLE
                    demons_souls_bg.visibility = View.VISIBLE
                    demons_souls_icon.visibility = View.VISIBLE
                }
            }
        })
    }
}