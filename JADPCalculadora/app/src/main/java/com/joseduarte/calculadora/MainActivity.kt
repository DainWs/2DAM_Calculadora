package com.joseduarte.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

const val NORMAL_TYPE = "Normal"
const val BINARY_TYPE = "Binary"
const val OCTAL_TYPE = "Octal"
const val HEXADECIMAL_TYPE = "Hexadecimal"

class MainActivity : AppCompatActivity() {



    var calculatorType = NORMAL_TYPE

    var firstNumber = ""
    var lastOperator = ""
    var lastResult = ""
    var numberPressed = false
    var operatorPressed = false
    var commaPressed = false

    var errorByZero = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentViewFromCalculatorType()
    }

    private fun setContentViewFromCalculatorType() {
        when(calculatorType) {
            NORMAL_TYPE -> {
                setContentView(R.layout.activity_main)
            }
            BINARY_TYPE -> {
                setContentView(R.layout.binary_calculator)
            }
            OCTAL_TYPE -> {
                setContentView(R.layout.octal_calculator)
            }
            HEXADECIMAL_TYPE -> {
                setContentView(R.layout.hexadecimal_calculator)
            }
        }
    }

    fun operatorButton(view : View) {

        if(!operatorPressed && numberPressed) {
            operatorPressed = true
            firstNumber = numberTV.getText().toString()
            lastOperator = findViewById<Button>(view.id).getText().toString()

            resultTV.text = firstNumber + " " + lastOperator
            numberTV.setText("")

            numberPressed = false
        }
        else if (numberPressed) {

            operatorPressed = true

            firstNumber = calculateFromString(firstNumber, lastOperator, numberTV.getText().toString())
            lastOperator = findViewById<Button>(view.id).getText().toString()

            resultTV.text = firstNumber + " " + lastOperator
            numberTV.setText("")

        } else if (lastResult.isNotEmpty() && lastOperator.isEmpty()) {

            operatorPressed = true

            firstNumber = lastResult
            lastOperator = findViewById<Button>(view.id).getText().toString()

            resultTV.text = firstNumber + " " + lastOperator
            numberTV.setText("")

            lastResult = ""

        }

    }

    fun commaButton(view : View) {
        if(!commaPressed) {
            commaPressed = true

            var numberTV = findViewById<TextView>(R.id.numberTV)
            numberTV.text = numberTV.getText().toString() + "."
        }
    }

    fun numberButton(view : View) {
        numberPressed = true

        var number = findViewById<Button>(view.id).getText().toString()

        numberTV.setText(numberTV.getText().toString() + number)
    }

    fun submitButton(view : View) {

        if (firstNumber.isNotEmpty() && lastOperator.isNotEmpty() && numberTV.getText().toString().isNotEmpty()) {

            lastResult = calculateFromString(firstNumber, lastOperator, numberTV.getText().toString())

            if(!errorByZero) resultTV.text = lastResult
            else resultTV.text = "Error"

            numberTV.setText("")

            firstNumber = ""
            lastOperator = ""
            numberPressed = false
            operatorPressed = false
            commaPressed = false
        }
    }

    fun resetButton(view : View) {
        resultTV.text = ""
        numberTV.setText("")

        firstNumber = ""
        lastOperator = ""
        numberPressed = false
        operatorPressed = false
        commaPressed = false
    }

    private fun calculateFromString(numberOne : String, operator : String, numberTwo : String): String {
        var result = ""

        when(calculatorType) {
            NORMAL_TYPE -> {
                result = calculate(numberOne.toDouble(), operator, numberTwo.toDouble()).toString()
            }
            BINARY_TYPE -> {
                var convertedNumberOne = Integer.parseInt(numberOne, 2).toDouble()
                var convertedNumberTwo = Integer.parseInt(numberTwo, 2).toDouble()

                var unconvertedResult = calculate(convertedNumberOne, operator, convertedNumberTwo)

                result = Integer.toBinaryString(unconvertedResult.toInt())
            }
            OCTAL_TYPE -> {
                var convertedNumberOne = Integer.parseInt(numberOne, 8).toDouble()
                var convertedNumberTwo = Integer.parseInt(numberTwo, 8).toDouble()

                var unconvertedResult = calculate(convertedNumberOne, operator, convertedNumberTwo)

                result = Integer.toOctalString(unconvertedResult.toInt())
            }
            HEXADECIMAL_TYPE -> {
                var convertedNumberOne = Integer.parseInt(numberOne, 16).toDouble()
                var convertedNumberTwo = Integer.parseInt(numberTwo, 16).toDouble()

                var unconvertedResult = calculate(convertedNumberOne, operator, convertedNumberTwo)

                result = Integer.toHexString(unconvertedResult.toInt()).toUpperCase()
            }
        }

        return result
    }

    private fun calculate(numberOne : Double, operator : String, numberTwo : Double): Double {
        var finalNum = 0.0

        when(operator){
            "+"->{
                finalNum = numberOne + numberTwo
            }
            "-"->{
                finalNum = numberOne - numberTwo
            }
            "÷"->{
                if(numberTwo == 0.0) errorByZero = true

                finalNum = numberOne / numberTwo
            }
            "*"->{
                finalNum = numberOne * numberTwo
            }
        }

        return finalNum
    }

    fun setNormalView(view: View) {
        calculatorType = NORMAL_TYPE
        setContentViewFromCalculatorType()
        resetButton(view)
        lastResult = ""
    }

    fun setBinaryView(view : View) {
        calculatorType = BINARY_TYPE
        setContentViewFromCalculatorType()
        resetButton(view)
        lastResult = ""
    }

    fun setOctalView(view : View) {
        calculatorType = OCTAL_TYPE
        setContentViewFromCalculatorType()
        resetButton(view)
        lastResult = ""
    }

    fun setHexadecimalView(view : View) {
        calculatorType = HEXADECIMAL_TYPE
        setContentViewFromCalculatorType()
        resetButton(view)
        lastResult = ""
    }

}

