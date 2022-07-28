package com.example.nycschoolsproject.model

import com.google.gson.annotations.SerializedName

/**
 * Structure of the School SAT scores to be displayed.
 */

data class SchoolSATScoreResponse(

    @field:SerializedName("dbn")
    var dbn : String,

    @field:SerializedName("school_name")
    var schoolName : String,

    @field:SerializedName("num_of_sat_test_takers")
    var numOfSatTestTakers : String,

    @field:SerializedName("sat_critical_reading_avg_score")
    var satCriticalReadingAvgScore : String,

    @field:SerializedName("sat_math_avg_score")
    var satMathAvgScore : String,

    @field:SerializedName("sat_writing_avg_score")
    var satWritingAvgScore : String
)