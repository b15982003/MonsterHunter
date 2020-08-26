package com.ray.monsterhunter


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class crawling_leave_message {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun crawling_leave_message() {
//        Thread.sleep(7000)
//
//        val appCompatTextView = onView(
//            allOf(
//                withId(R.id.google_sign_in_button), withText("Google SignIn"),
//                childAtPosition(
//                    childAtPosition(
//                        withClassName(`is`("android.widget.FrameLayout")),
//                        0
//                    ),
//                    2
//                ),
//                isDisplayed()
//            )
//        )
//        appCompatTextView.perform(click())
//
//        Thread.sleep(7000)

        val recyclerView = onView(
            allOf(
                withId(R.id.home_data_recy),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    2
                )
            )
        )

        Thread.sleep(7000)
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(3, scrollTo()))
        Thread.sleep(7000)
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(3, click()))
        Thread.sleep(7000)


        val appCompatEditText = onView(
            allOf(
                withId(R.id.crawling_detail_item_editText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.myNavHostFragment),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        Thread.sleep(7000)
        appCompatEditText.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.crawling_detail_item_editText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.myNavHostFragment),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        Thread.sleep(7000)
        appCompatEditText2.perform(replaceText("哈哈"), closeSoftKeyboard())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.crawling_detail_sentMessage),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.myNavHostFragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        Thread.sleep(7000)
        appCompatImageView.perform(click())

        Thread.sleep(1000)


        pressBack()

        val appCompatImageView3 = onView(
            allOf(
                withId(R.id.crawling_detail_back),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    1
                )
            )
        )
        Thread.sleep(7000)
        appCompatImageView3.perform(scrollTo(), click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
