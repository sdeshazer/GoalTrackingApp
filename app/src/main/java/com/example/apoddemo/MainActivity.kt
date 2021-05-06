package com.example.apoddemo

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormatSymbols
import java.util.*


var debug = true
class MainActivity : AppCompatActivity(){


    private val TAG = "MainActivity"

    private var calendar :Calendar = Calendar.getInstance()
    private final var currentYear : Int = calendar.get(Calendar.YEAR)
    private final  var currentMonth : Int = calendar.get(Calendar.MONTH)+1
    private final var currentDay : Int = calendar.get(Calendar.DAY_OF_MONTH)

    private lateinit var button1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var statsView: TextView = findViewById(R.id.stats)
        statsView.setMovementMethod(ScrollingMovementMethod())

    }



        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calendar.get(Calendar.YEAR)
            calendar.get(Calendar.MONTH) + 1         // android SDK states that months begin at 0, thus the +1:

        }

    fun onClick(view:View){

        var button: View = view
        if(chooseDate(view, button)) {
            setDates(button, calendar)
        }
    }

    fun chooseDate(view: View, button: View) :Boolean {
        DatePickerDialog(this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        return true
    }

    fun setDates(view : View, calendar: Calendar){

            if (!ValidDate.checkIfValidDate(calendar, currentYear, currentMonth, currentDay)) {
                //TODO: set message.
                if (debug) Log.d(TAG, "Date is out of range.")
            } else {
                if (debug) Log.d(TAG, "Date is valid (of this month)")


                var day = calendar.get(Calendar.DAY_OF_MONTH)
                when (view.id) {
                    R.id.button1 -> {

                        ValidDate.trackDates(day, 1)
                    }
                    R.id.button2 -> {

                        ValidDate.trackDates(day, 2)
                    }
                    R.id.button3 -> {

                        ValidDate.trackDates(day, 3)
                    }
                }

                calendar.set(Calendar.YEAR, currentYear)
                calendar.set(Calendar.MONTH, (currentMonth - 1))
                calendar.set(Calendar.DAY_OF_MONTH, currentDay)
            }
    }

     fun changeGoalText(view:View){
        var statsView: TextView = findViewById(R.id.stats)
        var goalOneResult = ValidDate.calculateGoalStats(1,calendar)
        var goalTwoResult = ValidDate.calculateGoalStats(2,calendar)
        var goalThreeResult = ValidDate.calculateGoalStats(3,calendar)
        var goalOneDays = ValidDate.getGoalCount(1)
        var goalTwoDays = ValidDate.getGoalCount(2)
        var goalThreeDays = ValidDate.getGoalCount(3)
        var monthName = ValidDate.getMonthForInt(currentMonth-1)
        statsView.text = "Goal Statistics:\n " +
                        "Total days met for first goal: $goalOneDays. \n" +
                        "That is a success rate of $goalOneResult for the month of $monthName.\n"+
                        "Total days met for first goal: $goalTwoDays. \n" +
                        "That is a success rate of $goalTwoResult for the month of $monthName.\n" +
                        "Total days met for first goal: $goalThreeDays. \n" +
                        "That is a success rate of $goalThreeResult for the month of $monthName.\n "

    }

}




