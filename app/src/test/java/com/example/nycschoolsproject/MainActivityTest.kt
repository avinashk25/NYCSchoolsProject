package com.example.nycschoolsproject

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.nycschoolsproject.data.NYCSchoolAPIService
import com.example.nycschoolsproject.data.NetWorkModule
import com.example.nycschoolsproject.model.SchoolItem
import dagger.Module
import dagger.Provides
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class MainActivityTest {

    @Inject lateinit var service : NYCSchoolAPIService

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        hiltRule.inject()
        scenario = ActivityScenario.launch(Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java))
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testMainActivity():Unit = runBlocking {
        // set up data, initially a list with some items and then an empty list
        val mockList = listOf(mockk<SchoolItem>())
        coEvery { service.getSchools() } returnsMany listOf(mockList, emptyList())

        //Scroll down to the third item
        onView(withId(R.id.recyclerView)).perform(
            scrollToPosition<RecyclerView.ViewHolder>(2)
        )

        //Scroll up to the top
        onView(withId(R.id.recyclerView))
            .perform(
                scrollToPosition<RecyclerView.ViewHolder>(0)
            )

        //Swipe down the refresh layout
        onView(withId(R.id.swipe_container)).perform(ViewActions.swipeDown())

        //wait for snackbar
        delay(200)

        //Check if the snackbar is visible
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.there_is_no_data)))

    }
}

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [NetWorkModule::class])
class FakeDataServiceModule {

    @Provides
    fun bind(): NYCSchoolAPIService {
        return mockk(relaxed = true, relaxUnitFun = true)
    }
}
