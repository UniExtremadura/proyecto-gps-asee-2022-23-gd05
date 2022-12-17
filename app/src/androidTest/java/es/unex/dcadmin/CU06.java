package es.unex.dcadmin;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static kotlin.jvm.internal.Intrinsics.checkNotNull;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CU06 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void cU06() {
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

        onView(
                allOf(withId(R.id.deleteAllCommands))
        ).perform(click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.page_2), withContentDescription("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatEditTextName = onView(
                allOf(withId(R.id.command_name),
                        isDisplayed()));
        Random rand = new Random();
        int name = rand. nextInt(1000);
        appCompatEditTextName.perform(replaceText(Integer.toString(name)));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.command_trigger), withText("Seleccionar trigger"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content_to_do_manager),
                                        5),
                                3),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Modificar"));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_command),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content_to_do_manager),
                                        5),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.commandRecyclerView),
                        childAtPosition(
                                withId(R.id.content_to_do_manager),
                                0)));
        recyclerView.perform(
                RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(Integer.toString(name))), click()));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.command_trigger), withText("Modificar"),
                        childAtPosition(
                                allOf(withId(R.id.detailScreen),
                                        childAtPosition(
                                                withId(R.id.content_to_do_manager),
                                                5)),
                                3),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Cambio"));

        ViewInteraction editText = onView(
                allOf(withId(R.id.command_trigger), withText("Cambio"),
                        withParent(allOf(withId(R.id.detailScreen),
                                withParent(withId(R.id.content_to_do_manager)))),
                        isDisplayed()));
        editText.check(matches(withText("Cambio")));

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.command_action), withText("Accion"),
                        childAtPosition(
                                allOf(withId(R.id.detailScreen),
                                        childAtPosition(
                                                withId(R.id.content_to_do_manager),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("Accion2"));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.command_action), withText("Accion2"),
                        withParent(allOf(withId(R.id.detailScreen),
                                withParent(withId(R.id.content_to_do_manager)))),
                        isDisplayed()));
        editText2.check(matches(withText("Accion2")));

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.command_name),
                        childAtPosition(
                                allOf(withId(R.id.detailScreen),
                                        childAtPosition(
                                                withId(R.id.content_to_do_manager),
                                                5)),
                                2),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("Nombre"+ name));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.command_name),
                        withParent(allOf(withId(R.id.detailScreen),
                                withParent(withId(R.id.content_to_do_manager)))),
                        isDisplayed()));
        editText3.check(matches(withText("Nombre"+name)));

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.saveView),
                        childAtPosition(
                                allOf(withId(R.id.detailScreen),
                                        childAtPosition(
                                                withId(R.id.content_to_do_manager),
                                                5)),
                                4),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        recyclerView
                .check(matches(atPosition(0, hasDescendant(withText("Nombre"+name)))));

        recyclerView.perform(
                RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Nombre"+name)), click()));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.command_trigger), withText("Cambio"),
                        withParent(allOf(withId(R.id.detailScreen),
                                withParent(withId(R.id.content_to_do_manager)))),
                        isDisplayed()));
        editText4.check(matches(withText("Cambio")));

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.command_name),
                        withParent(allOf(withId(R.id.detailScreen),
                                withParent(withId(R.id.content_to_do_manager)))),
                        isDisplayed()));
        editText5.check(matches(withText("Nombre"+name)));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.command_action), withText("Accion2"),
                        withParent(allOf(withId(R.id.detailScreen),
                                withParent(withId(R.id.content_to_do_manager)))),
                        isDisplayed()));
        editText6.check(matches(withText("Accion2")));

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

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}
