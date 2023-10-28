package algonquin.cst2335.wang0467;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import algonquin.cst2335.wang0467.MainActivity;
import algonquin.cst2335.wang0467.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Tests the behavior of the main activity when a password "12345" is entered.
     * <p>
     * Given the user is on the login screen,
     * When the user enters the password "12345" and clicks the login button,
     * Then the system should display the message "You shall not pass!".
     */
    @Test
    public void mainActivityTest() {

        ViewInteraction appCompatEditText = onView(withId(R.id.password_EditText));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

//        pressBack();

        ViewInteraction materialButton = onView(withId(R.id.login_btn));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests the behavior when a password missing an uppercase character is entered.
     * <p>
     * Given the user is on the login screen,
     * When the user enters the password "password123#$*" (without any uppercase letters) and clicks the login button,
     * Then the system should display the message "You shall not pass!".
     */
    @Test
    public void testFindMissingUpperCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.password_EditText));
        appCompatEditText.perform(replaceText("password123#$*"));

        ViewInteraction materialBtn = onView(withId(R.id.login_btn));
        materialBtn.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests the behavior when a password missing an uppercase character is entered.
     * <p>
     * Given the user is on the login screen,
     * When the user enters the password "password123#$*" (without any uppercase letters) and clicks the login button,
     * Then the system should display the message "You shall not pass!".
     */
    @Test
    public void testFindMissingDigit() {
        ViewInteraction appCompatEditText = onView(withId(R.id.password_EditText));
        appCompatEditText.perform(replaceText("Password#$*"));

        ViewInteraction materialBtn = onView(withId(R.id.login_btn));
        materialBtn.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests the behavior when a password missing a lowercase character is entered.
     * <p>
     * Given the user is on the login screen,
     * When the user enters the password "PASSWORD123#$*" (without any lowercase letters) and clicks the login button,
     * Then the system should display the message "You shall not pass!".
     */
    @Test
    public void testFindMissingLowerCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.password_EditText));
        appCompatEditText.perform(replaceText("PASSWORD123#$*"));

        ViewInteraction materialBtn = onView(withId(R.id.login_btn));
        materialBtn.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests the behavior when a password missing a special character is entered.
     * <p>
     * Given the user is on the login screen,
     * When the user enters the password "Password123" (without any special characters) and clicks the login button,
     * Then the system should display the message "You shall not pass!".
     */
    @Test
    public void testFindMissingSpecialCharacter() {
        ViewInteraction appCompatEditText = onView(withId(R.id.password_EditText));
        appCompatEditText.perform(replaceText("Password123"));

        ViewInteraction materialBtn = onView(withId(R.id.login_btn));
        materialBtn.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
