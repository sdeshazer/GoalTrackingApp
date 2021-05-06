package com.example.apoddemo

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import java.lang.Exception
import java.text.DateFormatSymbols
import java.util.*

object ValidDate {
    private var goalOneCount  = 0
    private var goalTwoCount  = 0
    private var goalThreeCount  = 0

    private val daysOne: ArrayList<Int> = arrayListOf()
    private val daysTwo: ArrayList<Int> = arrayListOf()
    private val daysThree: ArrayList<Int> = arrayListOf()



    private val TAG = "ValidDate"




     fun checkIfValidDate(calendar: Calendar, currentYear : Int, currentMonth : Int, currentDay : Int) : Boolean{
         var year = calendar.get(Calendar.YEAR)
         // android SDK states that months begin at 0, thus the +1:
         var month = calendar.get(Calendar.MONTH)+1
         var day = calendar.get(Calendar.DAY_OF_MONTH)
         if(debug)Log.d(TAG,"checkIfValidDate invoked")
         var minDay: Int = 1

         Log.d(TAG,"$year, $month, $day")
         Log.d(TAG,"CURRENT: $currentYear, $currentMonth, $currentDay")

         // check that date chosen is set later than the start date according to NASA APOD archives
         if (year != currentYear) return false
         if (year == currentYear && month != currentMonth)return false
         // check that date chosen is set before the current date
         if(debug){Log.d(TAG,"DATE IS VALID")}
         return true
     }

    fun getGoalCount(goal:Int) : Int{
        if(goal == 1){
            return goalOneCount
        }
        if(goal ==2){
            return goalTwoCount
        }
        return goalThreeCount
    }


    fun trackDates(day: Int, goal:Int){
        if (goal == 1){
            if(!daysOne.contains(day)){
            daysOne.add(day)
                goalOneCount++

                if(debug) Log.d(TAG, "$day added to goal one list new count: $goalOneCount")
            }else{
                daysOne.remove(day)
                goalOneCount--

                if(debug) Log.d(TAG, "$day removed from goal one list new count: $goalOneCount")
            }
        }
        if (goal == 2){
            if(!daysTwo.contains(day)){
                daysTwo.add(day)
                goalTwoCount++

                if(debug) Log.d(TAG, "$day added to goal two list new count: $goalTwoCount")
            }else{
                daysTwo.remove(day)
                goalTwoCount--

                if(debug) Log.d(TAG, "$day removed from goal two list new count: $goalTwoCount")
            }
        }
        if (goal == 3){
            if(!daysThree.contains(day)){
                daysThree.add(day)
                goalThreeCount++

                if(debug) Log.d(TAG, "$day added to goal three list new count: $goalThreeCount")
            }else{
                daysThree.remove(day)
                goalThreeCount--

                if(debug) Log.d(TAG, "$day removed from goal three list new count: $goalThreeCount")
            }
        }
    }

    /*
    * Gets the maximum day of the month to calculate goal results.
    *
    */

    fun calculateGoalStats(goal:Int, calendar: Calendar):Double{

        var result = 0.0
        var count = 0.0
        var maxDay : Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        if(goal == 1){
             count = goalOneCount.toDouble()
        }
        if(goal == 2){
             count = goalTwoCount.toDouble()
        }
        if(goal == 3){
             count = goalThreeCount.toDouble()
        }
        if(debug){
            Log.d(TAG,"TotalDays of this month: $maxDay, we divide $maxDay by $count which is the number of days my by a goal.")
        }

        result  = String.format("%.2f",(count / maxDay)).toDouble()

        return result

    }

    fun getMonthForInt(num: Int): String {
        var month = "wrong"
        val dfs = DateFormatSymbols()
        val months: Array<String> = dfs.getMonths()
        if (num >= 0 && num <= 11) {
            month = months[num]
        }
        return month
    }


}