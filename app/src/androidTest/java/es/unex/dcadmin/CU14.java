package es.unex.dcadmin;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CU14 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void cU14() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
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
        appCompatEditText.perform(longClick());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.addTokenView),
                        childAtPosition(
                                allOf(withId(R.id.entireScreen),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("<TOKEN_AQUI>"), closeSoftKeyboard());

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

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.infoMessage), withText("Pulsa en cualquier lugar para continuar"),
                        withParent(allOf(withId(R.id.entireScreen),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        textView.check(matches(withText("Pulsa en cualquier lugar para continuar")));

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

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction deleteButton = onView(
                allOf(withId(R.id.deleteAllCommands))
        );
        deleteButton.perform(click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.page_2), withContentDescription("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.command_trigger), withText("Seleccionar trigger"),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Predeterminado"));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.command_name), withText("Nombre del comando"),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Comando Predeterminado"));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_command),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content_to_do_manager),
                                        5),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        onView(
                allOf(withId(R.id.commandRecyclerView),
                        childAtPosition(
                                withId(R.id.content_to_do_manager),
                                0)))
                .perform(
                        RecyclerViewActions.actionOnItem(hasDescendant(withText("Comando Predeterminado")), click()));

        ViewInteraction checkbox = onView(
                allOf(withId(R.id.setAsDefault), withText("Marcar como predeterminado"),
                        withParent(allOf(withId(R.id.detailScreen),
                                withParent(withId(R.id.content_to_do_manager)))),
                        isDisplayed()));
        checkbox.check(matches(isNotChecked()));

        ViewInteraction materialCheckBox = onView(
                allOf(withId(R.id.setAsDefault), withText("Marcar como predeterminado"),
                        childAtPosition(
                                allOf(withId(R.id.detailScreen),
                                        childAtPosition(
                                                withId(R.id.content_to_do_manager),
                                                5)),
                                5),
                        isDisplayed()));
        materialCheckBox.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.saveView),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageView = onView(
                allOf(withId(R.id.isDefaultIcon),
                        withParent(allOf(withId(R.id.RelativeLayout1),
                                withParent(withId(R.id.commandRecyclerView)))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        onView(
                allOf(withId(R.id.commandRecyclerView),
                        childAtPosition(
                                withId(R.id.content_to_do_manager),
                                0)))
                .perform(
                        RecyclerViewActions.actionOnItem(hasDescendant(withText("Comando Predeterminado")), click()));

        ViewInteraction checkBox = onView(
                allOf(withId(R.id.setAsDefault), withText("Marcar como predeterminado"),
                        withParent(allOf(withId(R.id.detailScreen),
                                withParent(withId(R.id.content_to_do_manager)))),
                        isDisplayed()));
        checkBox.check(matches(isDisplayed()));
        checkBox.check(matches(isChecked()));

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.page_1), withContentDescription("Power"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());
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
