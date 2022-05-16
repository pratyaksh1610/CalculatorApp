package com.example.calculator_application

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*
import java.util.regex.Pattern
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private val factorials = arrayOf(
        "0",
        "1",
        "1",
        "2",
        "6",
        "24",
        "120",
        "720",
        "5040",
        "40320",
        "362880",
        "3628800",
        "39916800",
        "479001600",
        "6227020800",
        "87178291200",
        "1307674368000",
        "20922789888000",
        "355687428096000",
        "6402373705728000",
        "121645100408832000",
        "2432902008176640000",
    )

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /* handling clicks */
        etResult.setTextColor(Color.GRAY)
        tvC.setOnClickListener {
            tvFactorial.setBackgroundColor(Color.parseColor("#242530"))
            etResult.text = null
            tvResult.text = null
        }

        //etResult.setText("tan(sin(90))")


        tvOpenBracket.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString(R.string.opening_bracket))
        }

        tvCloseBracket.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString(R.string.closing_bracket))
        }

        imPlus_Minus.setOnClickListener {
            try {
                if (etResult.text.toString().isNotEmpty()) {

                    if (tvResult.text.isEmpty()) {
                        if (etResult.text.toString().first() == '-') {
                            tvResult.text = tvResult.text.toString().substring(1)
                        } else {
                            tvResult.text = '-' + etResult.text.toString()
                        }
                    } else {
                        if (tvResult.text.toString().toDouble() < 0.0) {
                            tvResult.text = tvResult.text.toString().substring(1)
                        } else {
                            tvResult.text = '-' + tvResult.text.toString()
                        }
                    }
                }
            } catch (e: NumberFormatException) {
                tvResult.text = "Error"
            }
        }

        imBack.setOnClickListener {
            if (etResult.text.isNotEmpty()) {
                etResult.setText(removeLastCharacter(etResult.text))
            }
        }

        imEqual.setOnClickListener {
            //regular expression for 1/0, zero divide error
            if ((etResult.text.toString().length == 1 && etResult.text.toString()
                    .first() == '(') || etResult.text.toString().isEmpty()
            ) {
                tvResult.text = null
            } else {
                try {
                    val input = ExpressionBuilder(etResult.text.toString()).build()
                    val output = input.evaluate()
                    val longOutput = output.toLong()
                    if (output == longOutput.toDouble()) {
                        tvResult.text = longOutput.toString()
                    } else {
                        tvResult.text = output.toString()
                    }
                } catch (e: EmptyStackException) {
                    tvResult.text = "Error"
                } catch (e: IllegalFormatException) {
                    tvResult.text = "Error"
                } catch (e: NumberFormatException) {
                    tvResult.text = "Error"
                } catch (e: IllegalArgumentException) {
                    tvResult.text = "Error"
                } catch (e: NumberFormatException) {
                    tvResult.text = "Error"
                } catch (e: ArithmeticException) {
                    tvResult.text = "Error"
                }
            }

//
        }

//        demo.setOnClickListener() { requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }

        tvPercent.setOnClickListener {
            try {
                if (etResult.text.toString().isNotEmpty() && (etResult.text.last()
                        .isDigit() || etResult.text.last() == ')')
                ) {
                    if (tvResult.text.isEmpty()) {
                        if (etResult.text.toString().first() != '-')
                            tvResult.text = (etResult.text.toString().toDouble() / 100.0).toString()
                    } else if (tvResult.text.toString().first() != '-') {
                        val decimalPattern = "([0-9]*)\\.([0-9]*)"
                        val num = tvResult.text.toString().toDouble().toString()

                        if (Pattern.matches(decimalPattern, num) || tvResult.text.toString()
                                .isDigitsOnly()
                        ) {
                            val x = num.toDouble() / 100.0
                            tvResult.text = x.toString()
                        }
                    }
                }
            } catch (e: NumberFormatException) {
                tvResult.text = "Error"
            }
        }

        tvDivide.setOnClickListener {
            if (etResult.text.toString().isNotEmpty())
                if (checkFunc(etResult.text.toString()))
                    etResult.text = etResult.text.append(resources.getString(R.string.divide_text))
        }

        imMultiply.setOnClickListener {
            if (etResult.text.toString().isNotEmpty())
                if (checkFunc(etResult.text.toString()))
                    etResult.text =
                        etResult.text.append('*')
        }

        imMinus.setOnClickListener {
            if (etResult.text.toString().isNotEmpty())
                if (checkFunc(etResult.text.toString()))
                    etResult.text =
                        etResult.text.append(resources.getString(R.string.subtract_text))

        }

        tvPlus.setOnClickListener {
            if (etResult.text.toString().isNotEmpty())
                if (checkFunc(etResult.text.toString()))
                    etResult.text = etResult.text.append(resources.getString(R.string.add_text))
        }

        tvDecimal.setOnClickListener {
            if (etResult.text.toString().isNotEmpty())
                if (checkFunc(etResult.text.toString()) && etResult.text.toString()
                        .last() != ')' && etResult.text.toString().last() != '('
                )
                    etResult.text = etResult.text.append(resources.getString(R.string.decimal_text))
//                else return@setOnClickListener
        }

        //handling clicks of digits
        tv7.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString(R.string.seven_text))
        }
        tv8.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString(R.string.eight_text))
        }
        tv9.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString(R.string.nine_text))
        }
        tv4.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString(R.string.four_text))
        }
        tv5.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString(R.string.five_text))
        }
        tv6.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString(R.string.six_text))
        }
        tv1.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString(R.string.one_text))
        }
        tv2.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString(R.string.two_text))
        }
        tv3.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString(R.string.three_text))
        }
        tv0.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString(R.string.zero_text))
        }

        tvSin.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString((R.string.sin_operation)))
        }
        tvCos.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString((R.string.cos_operation)))
        }

        tvTan.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString((R.string.tan_operation)))
        }
        tvSqrt.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString((R.string.sqrt_operation)))
        }
        tvCubeRoot.setOnClickListener {
            etResult.text = etResult.text.append(resources.getString((R.string.cbrt_operation)))
        }

        tvFactorial.setOnClickListener {
            if (etResult.text.toString().isNotEmpty()) {
                if (etResult.text.toString().isDigitsOnly()) {
                    tvFactorial.setBackgroundColor(Color.BLUE)
                    val x = etResult.text.toString().toLong()
                    if (x <= 20 && x.toString().isDigitsOnly()) {
                        tvResult.text = factorials[x.toInt()]
                    } else {
                        tvResult.text = "Error"
                    }
                }
                tvFactorial.setBackgroundColor((Color.parseColor("#ffbb00")))
            } else {
                Toast.makeText(this, "Invalid Expression", Toast.LENGTH_SHORT).show()
            }

        }
        tvPi.setOnClickListener {
            if (etResult.text.toString().isEmpty() || !etResult.text.toString().last()
                    .isDigit() && etResult.text.toString()
                    .last() != ')'
            )
                etResult.text = etResult.text.append(resources.getString((R.string.pi)))
        }

        tvLn.setOnClickListener {
            etResult.text = etResult.text.append("log(")
        }
        tvLog_10.setOnClickListener {
            etResult.text = etResult.text.append("log10(")
        }
        tvEx.setOnClickListener {
            if (etResult.text.toString().isDigitsOnly() && etResult.text.toString().isNotEmpty()) {
                val x = "2.718281828459045".toDouble().pow(etResult.text.toString().toDouble())
                    .toString()
                tvResult.text = x
            }
        }
        tvx2.setOnClickListener {
            if (etResult.text.toString().isNotEmpty()) {
                val check = etResult.text.toString().toDouble()
                Toast.makeText(this, "$check", Toast.LENGTH_SHORT).show()
                if (etResult.text.toString().isNotEmpty()) {
                    val x = check.pow(2).toString()
                    tvResult.text = x
                }
            }
        }
        tv10x.setOnClickListener {
            if (etResult.text.toString().isNotEmpty()) {
                val check = etResult.text.toString().toDouble()
                Toast.makeText(this, "$check", Toast.LENGTH_SHORT).show()
                if (etResult.text.toString().isNotEmpty()) {
                    val x = "10".toDouble().pow(check).toString()
                    tvResult.text = x
                }
            }
        }
        tvPrime.setOnClickListener {
            try {
                if (etResult.text.toString().isEmpty()) {
                    Toast.makeText(this, "Enter a valid integer!", Toast.LENGTH_SHORT).show()
                } else if (etResult.text.toString().isDigitsOnly() && etResult.text.toString()
                        .isNotEmpty()
                ) {
                    if (isPrime(etResult.text.toString().toInt())) tvResult.text = "1"
                    else tvResult.text = "0"
                }
            } catch (e: NumberFormatException) {
                tvResult.text = "Error"
            }
        }


    }
}


private fun removeLastCharacter(s: Editable): String {
    return if (s.last() != ' ')
        s.substring(0, s.length - 1)
    else
        s.substring(0, s.length - 3)
}

private fun checkFunc(s: String): Boolean {
    return s.last().isDigit() || s.last() == ')'
}

private fun isPrime(n: Int): Boolean {
    if (n <= 1) return false else if (n == 2) return true else if (n % 2 == 0) return false
    var i = 3
    while (i <= sqrt(n.toDouble())) {
        if (n % i == 0) return false
        i += 2
    }
    return true
}

