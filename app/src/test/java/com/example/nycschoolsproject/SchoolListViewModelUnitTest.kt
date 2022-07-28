package com.example.nycschoolsproject

import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nycschoolsproject.data.NYCSchoolAPIService
import com.example.nycschoolsproject.model.SchoolItem
import com.example.nycschoolsproject.viewmodel.SchoolListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.lang.Exception

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SchoolListViewModelUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var service : NYCSchoolAPIService

    private lateinit var viewModel: SchoolListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        viewModel = SchoolListViewModel(service)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testViewModel() = runBlocking {
        // empty data test
        coEvery { service.getSchools() } returns emptyList()

        viewModel.onRefresh()
        delay(100)
        var value = viewModel.items.value
        assertEquals(0, value?.size)

        // dummy data
        val mockList = listOf(mockk<SchoolItem>())
        coEvery { service.getSchools()} returns mockList

        viewModel.onRefresh()
        delay(100)
        value = viewModel.items.value

        assertEquals(mockList, value)

        // Throw an error
        coEvery { service.getSchools() } throws Exception("Test Exception")

        viewModel.onRefresh()
        delay(100)
        value = viewModel.items.value
        assertEquals(0, value?.size)
    }
}