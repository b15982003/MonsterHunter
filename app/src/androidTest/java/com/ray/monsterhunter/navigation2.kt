package com.ray.monsterhunter


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
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
class navigation2 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun navigation2() {
        Thread.sleep(7000)

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

//        swipeDown()

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_chatroom), withContentDescription("chatRoom"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNav),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.navigation_history), withContentDescription("history"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNav),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        Thread.sleep(7000)
        bottomNavigationItemView2.perform(click())

        val bottomNavigationItemView3 = onView(
            allOf(
                withId(R.id.navigation_profile), withContentDescription("profile"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNav),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        Thread.sleep(7000)
        bottomNavigationItemView3.perform(click())
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
