package com.example.nycschoolsproject.model

import com.google.gson.annotations.SerializedName

/**
 * Structure of the School items to be displayed as list.
 */

data class SchoolItem(

    @field:SerializedName("dbn")
    var dbn : String,

    @field:SerializedName("school_name")
    var schoolName : String,

    @field:SerializedName("location")
    var location : String
    )
