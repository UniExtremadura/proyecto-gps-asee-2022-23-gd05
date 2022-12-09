package es.unex.dcadmin;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CU01 {

    /***********************************/
    public static ViewAction waitFor(long delay) {
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return ViewMatchers.isRoot();
            }

            @Override public String getDescription() {
                return "wait for " + delay + "milliseconds";
            }

            @Override public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }
    /*************************************/

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void cU01() throws InterruptedException {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.addTokenView),
                        childAtPosition(
                                allOf(withId(R.id.entireScreen),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("prueba"), closeSoftKeyboard());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.access),
                        childAtPosition(
                                allOf(withId(R.id.entireScreen),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.addTokenView), withText("prueba"),
                        childAtPosition(
                                allOf(withId(R.id.entireScreen),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());


        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.addTokenView), withText("prueba"),
                        childAtPosition(
                                allOf(withId(R.id.entireScreen),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        onView(isRoot()).perform(waitFor(5000));
        onView(withId(R.id.infoMessage)).check(matches(isDisplayed()));
        onView(withId(R.id.infoMessage)).check(matches(withText("No se ha podido iniciar sesion el Discord. ¿El token es correcto?")));


        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.addTokenView), withText("prueba"),
                        childAtPosition(
                                allOf(withId(R.id.entireScreen),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText(""));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.addTokenView),
                        childAtPosition(
                                allOf(withId(R.id.entireScreen),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.addTokenView),
                        childAtPosition(
                                allOf(withId(R.id.entireScreen),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.addTokenView),
                        childAtPosition(
                                allOf(withId(R.id.entireScreen),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText7.perform(longClick());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.addTokenView),
                        childAtPosition(
                                allOf(withId(R.id.entireScreen),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("<TOKEN_AQUI>"), closeSoftKeyboard());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.access),
                        childAtPosition(
                                allOf(withId(R.id.entireScreen),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageView2.perform(click());



        ViewInteraction constraintLayout = onView(
                allOf(withId(R.id.entireScreen),
                        childAtPosition(
                                allOf(withId(android.R.id.content),
                                        childAtPosition(
                                                withId(androidx.constraintlayout.widget.R.id.decor_content_parent),
                                                0)),
                                0),
                        isDisplayed()));
        constraintLayout.perform(click());

        onView(isRoot()).perform(waitFor(5000));
        onView(withId(R.id.infoMessage)).check(matches(withText("Pulsa en cualquier lugar para continuar")));

        ViewInteraction constraintLayout1 = onView(
                allOf(withId(R.id.entireScreen),
                        childAtPosition(
                                allOf(withId(android.R.id.content),
                                        childAtPosition(
                                                withId(androidx.constraintlayout.widget.R.id.decor_content_parent),
                                                0)),
                                0),
                        isDisplayed()));
        constraintLayout1.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.page_1), withContentDescription("Power"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        System.out.println("terminado");

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
