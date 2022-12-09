package es.unex.dcadmin;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CU08 {

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
    public void cU08() {

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
        onView(isRoot()).perform(waitFor(5000));

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
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction appCompatImageView_BORRARTODO = onView(
                allOf(withId(R.id.deleteAllCommands),
                        childAtPosition(
                                allOf(withId(R.id.content_to_do_manager),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageView_BORRARTODO.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.page_2), withContentDescription("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_command),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content_to_do_manager),
                                        5),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.page_2), withContentDescription("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.add_command),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content_to_do_manager),
                                        5),
                                1),
                        isDisplayed()));
        floatingActionButton2.perform(click());
        onView(isRoot()).perform(waitFor(1000));


        onView(allOf(withText(R.string.InsertErr)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.command_name)).perform(replaceText("Nombre del comando1"));
        onView(isRoot()).perform(waitFor(1000));



        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.command_trigger), withText("Seleccionar trigger"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content_to_do_manager),
                                        5),
                                3),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Seleccionar trigger0"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.command_trigger), withText("Seleccionar trigger0"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content_to_do_manager),
                                        5),
                                3),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.add_command),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content_to_do_manager),
                                        5),
                                1),
                        isDisplayed()));
        floatingActionButton3.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.deleteCommand),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout1),
                                        childAtPosition(
                                                withId(R.id.commandRecyclerView),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageView2.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.commandRecyclerView),
                        childAtPosition(
                                withId(R.id.content_to_do_manager),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.command_name), withText("Nombre del comando"),
                        childAtPosition(
                                allOf(withId(R.id.detailScreen),
                                        childAtPosition(
                                                withId(R.id.content_to_do_manager),
                                                5)),
                                2),
                        isDisplayed()));
        onView(withId(R.id.command_name)).perform(replaceText("Nombre del comando1"));

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.command_action), withText("Accion"),
                        childAtPosition(
                                allOf(withId(R.id.detailScreen),
                                        childAtPosition(
                                                withId(R.id.content_to_do_manager),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("Accion1"));

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.saveView),
                        childAtPosition(
                                allOf(withId(R.id.detailScreen),
                                        childAtPosition(
                                                withId(R.id.content_to_do_manager),
                                                5)),
                                4),
                        isDisplayed()));
        appCompatImageView3.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.commandRecyclerView),
                        childAtPosition(
                                withId(R.id.content_to_do_manager),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction editText = onView(
                allOf(withId(R.id.command_name), withText("Nombre del comando1"),
                        withParent(allOf(withId(R.id.detailScreen),
                                withParent(withId(R.id.content_to_do_manager)))),
                        isDisplayed()));
        editText.check(matches(withText("Nombre del comando1")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.command_trigger), withText("Seleccionar trigger0"),
                        withParent(allOf(withId(R.id.detailScreen),
                                withParent(withId(R.id.content_to_do_manager)))),
                        isDisplayed()));
        editText2.check(matches(withText("Seleccionar trigger0")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.command_action), withText("Accion1"),
                        withParent(allOf(withId(R.id.detailScreen),
                                withParent(withId(R.id.content_to_do_manager)))),
                        isDisplayed()));
        editText3.check(matches(withText("Accion1")));

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.saveView),
                        childAtPosition(
                                allOf(withId(R.id.detailScreen),
                                        childAtPosition(
                                                withId(R.id.content_to_do_manager),
                                                5)),
                                4),
                        isDisplayed()));
        appCompatImageView4.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction appCompatImageView5 = onView(
                allOf(withId(R.id.deleteAllCommands),
                        childAtPosition(
                                allOf(withId(R.id.content_to_do_manager),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageView5.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.page_1), withContentDescription("Power"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

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
