package infosys.com.kotlinmvvmsample

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import infosys.com.kotlinmvvmsample.EspressoUITest.RecyclerViewItemCountAssertion.Companion.withItemCount
import infosys.com.kotlinmvvmsample.base.AbstractTest
import infosys.com.kotlinmvvmsample.view.ui.MainActivity
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class EspressoUITest : AbstractTest(){

    @get:Rule
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun A_useAppContext() {
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals(appContext.packageName, appContext.packageName)
    }

    @Test
    fun B_checkTextFailed() {
        onView(withId(R.id.loading_facts)).check(matches(withText(mActivityRule.activity.getString(R.string.loading_facts))))
    }

    /**
     * Check if RecyclerView is visible. this will be example of successful UI test
     */
    @Test
    fun C_checkRecyclerViewSuccess() {
        waitFor(1000)
        onView(withId(R.id.fact_list)).check(matches(isDisplayed()))
    }

    @Test
    fun E_recyclerViewItemCountTest() {
        waitFor(1000)
        onView(withId(R.id.fact_list)).check(withItemCount(13));
    }

    class RecyclerViewItemCountAssertion private constructor(private val matcher: Matcher<Int>) : ViewAssertion {
        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            assertThat(adapter!!.itemCount, matcher)
        }

        companion object {

            fun withItemCount(expectedCount: Int): RecyclerViewItemCountAssertion {
                return withItemCount(`is`(expectedCount))
            }

            fun withItemCount(matcher: Matcher<Int>): RecyclerViewItemCountAssertion {
                return RecyclerViewItemCountAssertion(matcher)
            }
        }
    }

}