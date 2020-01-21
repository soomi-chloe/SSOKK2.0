package com.example.ssokk20ex.ui.alarm

data class PillDTO(var userName: String? = null,
                   var pillName: String? = null,
                   var alarmTotalN: String? = null,
                   var howManyInOnce: String? = null,

                   var MealBeforeOrAfter:String? = null,
                   var howMuchTimeBeforeOrAfter: String? = null,

                   var alarm_1_Time:String? = null,
                   var alarm_2_Time:String? = null,
                   var alarm_3_Time:String? = null
)