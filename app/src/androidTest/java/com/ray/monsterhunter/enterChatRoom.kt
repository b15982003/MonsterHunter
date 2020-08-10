package com.ray.monsterhunter


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class enterChatRoom {


    fun logout(){
        FirebaseAuth.getInstance().signOut()
        Thread.sleep(3000)
    }

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LogInActivity::class.java)

    @Test
    fun enterChatRoom() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatTextView = onView(
            allOf(
                withId(R.id.google_sign_in_button), withText("Google SignIn"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_chatroom),
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
        Thread.sleep(3000)

        bottomNavigationItemView.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.chatRoom_recy),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(7, click()))

        val appCompatImageView = onView(
            allOf(
                withId(R.id.chatRoom_detail_speaker),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.myNavHostFragment),
                        0
                    ),
                    26
                ),
                isDisplayed()
            )
        )
        Thread.sleep(7000)

        appCompatImageView.perform(click())

        val appCompatTextView2 = onView(
            allOf(
                withId(R.id.chatRoom_detail_speaker_back), withText("退"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.myNavHostFragment),
                        0
                    ),
                    28
                ),
                isDisplayed()
            )
        )
        appCompatTextView2.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.chatRoom_detail_speaker),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.myNavHostFragment),
                        0
                    ),
                    26
                ),
                isDisplayed()
            )
        )
        Thread.sleep(7000)

        appCompatImageView2.perform(click())

        val appCompatSpinner = onView(
            allOf(
                withId(R.id.chatRoom_detail_arms_spinner),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.myNavHostFragment),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        Thread.sleep(7000)

        appCompatSpinner.perform(click())

        val textView = onData(anything())
            .inAdapterView(
                allOf(
                    withId(R.id.select_dialog_listview),
                    childAtPosition(
                        withId(R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(3)
        Thread.sleep(7000)

        textView.perform(click())
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
