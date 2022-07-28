package com.example.nycschoolsproject.data

import com.example.nycschoolsproject.AppConstants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Provides Network communication related instances.
 */

@InstallIn(ViewModelComponent::class)
@Module
class NetWorkModule {

    @Provides
    fun getNYCSchoolAPIService():NYCSchoolAPIService {

        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(AppConstants.BASE_URL)
            .build()

        return retrofit.create(NYCSchoolAPIService::class.java)
    }
}