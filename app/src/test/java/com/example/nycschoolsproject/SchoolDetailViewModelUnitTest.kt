package com.example.nycschoolsproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nycschoolsproject.data.NYCSchoolAPIService
import com.example.nycschoolsproject.model.SchoolSATScoreResponse
import com.example.nycschoolsproject.viewmodel.SchoolDetailViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class SchoolDetailViewModelUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var service : NYCSchoolAPIService

    private lateinit var viewModel: SchoolDetailViewModel

    var dbn : String = "21K728"

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        viewModel = SchoolDetailViewModel(service)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testViewModel() = runBlocking {
        coEvery { service.getSchoolSATScores(dbn) } returns emptyList()

        viewModel.updateSchoolDetails(dbn)
        delay(100)
        assertEquals(null, viewModel.schoolSATScoreResponse.value)

        val mockList = mockk<SchoolSATScoreResponse>()
        coEvery { service.getSchoolSATScores(dbn) } returns listOf(mockList)

        viewModel.updateSchoolDetails(dbn)
        delay(100)
        assertEquals(mockList, viewModel.schoolSATScoreResponse.value)
    }
}