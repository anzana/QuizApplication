package com.example.quizapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), OnCheckedChangeListener {

    private val answer1: String = "True"
    private val answer2 = mutableListOf("onCreate()", "onPause()")

    private var userAnswer1: String = ""
    private var userAnswer2 = mutableListOf<String>()

    private lateinit var rgOption1: RadioGroup

    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var btnSubmit: Button
    private lateinit var btnReset: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rgOption1 = findViewById(R.id.rgOption1)
        checkBox1 = findViewById(R.id.checkBox1)
        checkBox2 = findViewById(R.id.checkBox2)
        checkBox3 = findViewById(R.id.checkBox3)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)

        rgOption1.setOnCheckedChangeListener{
            _, checkedId ->
            when(checkedId) {
                R.id.radioButton1 -> userAnswer1 = "True"
                R.id.radioButton2 -> userAnswer1 = "False"
            }
        }

        checkBox1.setOnCheckedChangeListener(this)
        checkBox2.setOnCheckedChangeListener(this)
        checkBox3.setOnCheckedChangeListener(this)



        btnSubmit.setOnClickListener {

            if (userAnswer1.isEmpty() || userAnswer2.isEmpty()) {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var score = 0
            if (userAnswer1 == answer1) {
                score += 50
            }
            if (userAnswer2.containsAll(answer2)) {
                score += 50
            }
            // current date and time in format
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("MM/dd/yyyy, hh:mm:ss a", Locale.getDefault())
            val currentDateTime = formatter.format(date)


            val message =
                if (score > 0) "Congratulations, you submitted on $currentDateTime. You score is $score%"
                else "Sorry, You submitted on $currentDateTime. You achieved $score%"
            AlertDialog.Builder(this)
                .setTitle("Result")
                .setMessage(message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()


        }


        btnReset.setOnClickListener {
            rgOption1.clearCheck()
            resetCheckbox()
        }

    }

    private fun resetCheckbox() {
        checkBox1.isChecked = false
        checkBox2.isChecked = false
        checkBox3.isChecked = false
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.checkBox1 -> addAnswer("INVISIBLE", isChecked)
            R.id.checkBox2 -> addAnswer("GONE", isChecked)
            R.id.checkBox3 -> addAnswer("VISIBLE", isChecked)
        }
    }

    private fun addAnswer(answer: String, isChecked: Boolean) {
        if (isChecked) userAnswer2.add(answer) else userAnswer2.remove(answer)
    }

}