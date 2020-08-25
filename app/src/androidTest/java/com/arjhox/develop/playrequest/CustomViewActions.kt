package com.arjhox.develop.playrequest

import android.view.View
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers

fun setTextInTextView(text: String): ViewAction {
    return object: ViewAction {

        override fun getDescription(): String {
            return "Replace text"
        }

        override fun getConstraints(): Matcher<View> {
            return Matchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.isAssignableFrom(TextView::class.java))
        }

        override fun perform(uiController: UiController?, view: View?) {
            (view as TextView).text = text
        }

    }
}