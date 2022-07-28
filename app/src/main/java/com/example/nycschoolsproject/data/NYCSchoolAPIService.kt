package com.example.nycschoolsproject.data

import com.example.nycschoolsproject.model.SchoolItem
import com.example.nycschoolsproject.model.SchoolSATScoreResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Communicates with the NYC School backend to obtain school data.
 */

interface NYCSchoolAPIService {

    /**
     * @return [List<SchoolItem>] : List of schools from the server dataset
     */

    @GET("s3k6-pzi2.json")
    suspend fun getSchools(): List<SchoolItem>

    /**
     * @return [List<SchoolSATScoreResponse>] : SAT Score details for individual school
     */

    @GET("f9bf-2cp4.json")
    suspend fun getSchoolSATScores(@Query("dbn") dbn : String) : List<SchoolSATScoreResponse>
}