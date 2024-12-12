package com.example.as1;

import com.example.as1.MainActivity;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.ext.junit.rules.ActivityScenarioRule;;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.content.Context;
import android.view.View;
import android.widget.Button;



import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;




import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.core.StringEndsWith.endsWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class ExampleInstrumentedTest {
    private static final int SIMULATED_DELAY_MS = 2000;

    @Rule   // needed to launch the activity
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * start server, run test
     */
    @Test
    public void A_testEverythingAtOnce() {
        String loginUser = "yes";
        String character = "tester";
        onView(withId(R.id.toMenuBtn)).perform(click()); //go to main menu
        onView(withId(R.id.loginBtn)).perform(click()); //go to login

        onView(withId(R.id.registerBtn)).perform(click()); //go to registration
        onView(withId(R.id.registerCancelBtn)).perform(click()); //skip registering (would be weird with server)

        onView(withId(R.id.usernameText)).perform(typeText(loginUser), closeSoftKeyboard()); //sign in as yes, yes
        onView(withId(R.id.passwordText)).perform(typeText(loginUser), closeSoftKeyboard());
        onView(withId(R.id.loginGoBtn)).perform(click()); //login
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.loginCancelBtn)).perform(click()); //go to main menu

        onView(withId(R.id.playBtn)).perform(click()); //play game
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.charSelNewCharBtn)).perform(click()); //create new character

        onView(withId(R.id.charNext)).perform(click()); //test charNext
        onView(withId(R.id.charPrev)).perform(click()); //test charPrev
        onView(withId(R.id.easyRdBtn)).perform(click()); //flashy select radio buttons
        onView(withId(R.id.normalRdBtn)).perform(click());
        onView(withId(R.id.hardRdBtn)).perform(click());
        onView(withId(R.id.charNameTxt)).perform(typeText(character), closeSoftKeyboard()); //type char name
        onView(withId(R.id.charCreationApply)).perform(click()); //apply the char name
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.charCreationCancel)).perform(click()); //Go back
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.charSel2)).perform(click()); //select character slot 2
        onView(withId(R.id.charSelDeleteBtn)).perform(click()); //delete created character
        onView(withId(R.id.charSel1)).perform(click()); //select character slot 1
        onView(withId(R.id.charSelBtn)).perform(click()); //go to next screen
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.yworldSel1)).perform(click()); //select your world slot 1
        onView(withId(R.id.worldSelBackBtn)).perform(click()); //go back to character creation screen

        onView(withId(R.id. charSelGoBackBtn)).perform(click()); //go back to main menu


    }
    @Test
    public void B_loginTest() {
        String loginUser = "yes";
        String failedPass ="no";
        onView(withId(R.id.toMenuBtn)).perform(click()); //go to main menu
        onView(withId(R.id.loginBtn)).perform(click()); //go to login

        onView(withId(R.id.registerBtn)).perform(click()); //go to registration
        onView(withId(R.id.regUsernameText)).perform(typeText(loginUser), closeSoftKeyboard()); //fail a registration
        onView(withId(R.id.passwordText1)).perform(typeText(loginUser), closeSoftKeyboard());
        onView(withId(R.id.passwordText2)).perform(typeText(failedPass), closeSoftKeyboard());
        onView(withId(R.id.createAccBtn)).perform(click());
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box

        onView(withId(R.id.registerCancelBtn)).perform(click()); //skip registering (would be weird with server)

        onView(withId(R.id.usernameText)).perform(typeText(loginUser), closeSoftKeyboard()); //sign in as yes, yes
        onView(withId(R.id.passwordText)).perform(typeText(loginUser), closeSoftKeyboard());
        onView(withId(R.id.loginGoBtn)).perform(click()); //login
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.loginCancelBtn)).perform(click()); //go to main menu
    }

    @Test
    public void C_selectCharacterTest() {
        String loginUser = "yes";
        onView(withId(R.id.toMenuBtn)).perform(click()); //go to main menu
        onView(withId(R.id.loginBtn)).perform(click()); //go to login
        onView(withId(R.id.usernameText)).perform(typeText(loginUser), closeSoftKeyboard()); //sign in as yes, yes
        onView(withId(R.id.passwordText)).perform(typeText(loginUser), closeSoftKeyboard());
        onView(withId(R.id.loginGoBtn)).perform(click()); //login
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.loginCancelBtn)).perform(click()); //go to main menu
        onView(withId(R.id.playBtn)).perform(click()); //play game
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.charSel1)).perform(click()); //select character slot 1
        onView(withId(R.id.charSelBtn)).perform(click()); //go to next screen
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void D_createCharacterTest() {
        String loginUser = "yes";
        String character = "tester";
        onView(withId(R.id.toMenuBtn)).perform(click()); //go to main menu
        onView(withId(R.id.loginBtn)).perform(click()); //go to login
        onView(withId(R.id.usernameText)).perform(typeText(loginUser), closeSoftKeyboard()); //sign in as yes, yes
        onView(withId(R.id.passwordText)).perform(typeText(loginUser), closeSoftKeyboard());
        onView(withId(R.id.loginGoBtn)).perform(click()); //login
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.loginCancelBtn)).perform(click()); //go to main menu
        onView(withId(R.id.playBtn)).perform(click()); //play game
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.charSelNewCharBtn)).perform(click()); //create new character

        onView(withId(R.id.charNext)).perform(click()); //test charNext
        onView(withId(R.id.charPrev)).perform(click()); //test charPrev
        onView(withId(R.id.easyRdBtn)).perform(click()); //flashy select radio buttons
        onView(withId(R.id.normalRdBtn)).perform(click());
        onView(withId(R.id.hardRdBtn)).perform(click());
        onView(withId(R.id.charNameTxt)).perform(typeText(character), closeSoftKeyboard()); //type char name
        onView(withId(R.id.charCreationApply)).perform(click()); //apply the char name
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.charCreationCancel)).perform(click()); //Go back
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void E_deleteCharacterTest() {
        String loginUser = "yes";
        onView(withId(R.id.toMenuBtn)).perform(click()); //go to main menu
        onView(withId(R.id.loginBtn)).perform(click()); //go to login
        onView(withId(R.id.usernameText)).perform(typeText(loginUser), closeSoftKeyboard()); //sign in as yes, yes
        onView(withId(R.id.passwordText)).perform(typeText(loginUser), closeSoftKeyboard());
        onView(withId(R.id.loginGoBtn)).perform(click()); //login
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.loginCancelBtn)).perform(click()); //go to main menu
        onView(withId(R.id.playBtn)).perform(click()); //play game
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.charSel2)).perform(click()); //select character slot 2
        onView(withId(R.id.charSelDeleteBtn)).perform(click()); //delete created character
        onView(withId(R.id.charSel1)).perform(click()); //select character slot 1
        onView(withId(R.id.charSelBtn)).perform(click()); //go to next screen
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.worldSelBackBtn)).perform(click()); //go back to double check
    }

    //Everything after F is a test by Gabe
    @Test
    public void F_testNewGame() {
        String loginUser = "yes";
        String character = "tester";
        String worldname = "Test";
        onView(withId(R.id.toMenuBtn)).perform(click()); //go to main menu
        onView(withId(R.id.loginBtn)).perform(click()); //go to login

        onView(withId(R.id.usernameText)).perform(typeText(loginUser), closeSoftKeyboard()); //sign in as yes, yes
        onView(withId(R.id.passwordText)).perform(typeText(loginUser), closeSoftKeyboard());
        onView(withId(R.id.loginGoBtn)).perform(click()); //login
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.loginCancelBtn)).perform(click()); //go to main menu

        onView(withId(R.id.playBtn)).perform(click()); //play game
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.charSelNewCharBtn)).perform(click()); //create new character

        onView(withId(R.id.charNext)).perform(click()); //test charNext
        onView(withId(R.id.charPrev)).perform(click()); //test charPrev
        onView(withId(R.id.normalRdBtn)).perform(click());
        onView(withId(R.id.charNameTxt)).perform(typeText(character), closeSoftKeyboard()); //type char name
        onView(withId(R.id.charCreationApply)).perform(click()); //apply the char name
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.charCreationCancel)).perform(click()); //Go back
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.charSel2)).perform(click()); //select character slot 2
        onView(withId(R.id.charSelDeleteBtn)).perform(click()); //delete created character
        onView(withId(R.id.charSel1)).perform(click()); //select character slot 1
        onView(withId(R.id.charSelBtn)).perform(click()); //go to next screen
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.yworldSel2)).perform(click()); //select your world slot 2
        onView(withId(R.id.worldSelDelete)).perform(click()); //Delete that world
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.worldSelCreateNew)).perform(click()); //Create new world
        onView(withId(R.id.worldNameTxt)).perform(typeText(character), closeSoftKeyboard()); //type in world name
        onView(withId(R.id.normalWorldBtn)).perform(click());
        onView(withId(R.id.worldTypeSuperflat)).perform(click());
        onView(withId(R.id.worldCreationCreateWorld)).perform(click()); //create the world
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }


    }

    @Test
    public void G_testGameSelect() {
        String loginUser = "yes";
        String character = "tester";
        String worldname = "Test";
        onView(withId(R.id.toMenuBtn)).perform(click()); //go to main menu
        onView(withId(R.id.loginBtn)).perform(click()); //go to login

        onView(withId(R.id.usernameText)).perform(typeText(loginUser), closeSoftKeyboard()); //sign in as yes, yes
        onView(withId(R.id.passwordText)).perform(typeText(loginUser), closeSoftKeyboard());
        onView(withId(R.id.loginGoBtn)).perform(click()); //login
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.loginCancelBtn)).perform(click()); //go to main menu

        onView(withId(R.id.playBtn)).perform(click()); //play game
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.charSelNewCharBtn)).perform(click()); //create new character

        onView(withId(R.id.charNext)).perform(click()); //test charNext
        onView(withId(R.id.charPrev)).perform(click()); //test charPrev
        onView(withId(R.id.normalRdBtn)).perform(click());
        onView(withId(R.id.charNameTxt)).perform(typeText(character), closeSoftKeyboard()); //type char name
        onView(withId(R.id.charCreationApply)).perform(click()); //apply the char name
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.charCreationCancel)).perform(click()); //Go back
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.charSel2)).perform(click()); //select character slot 2
        onView(withId(R.id.charSelDeleteBtn)).perform(click()); //delete created character
        onView(withId(R.id.charSel1)).perform(click()); //select character slot 1
        onView(withId(R.id.charSelBtn)).perform(click()); //go to next screen
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.yworldSel1)).perform(click()); //select your world slot 1
        onView(withId(R.id.worldSelYourSel)).perform(click()); //select your world
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        //CAN'T PRESS THE LEAVE BUTTON AS I DON'T KNOW THE ID.
    }

    @Test
    public void H_testFreinds(){
        String loginUser = "yes";
        onView(withId(R.id.toMenuBtn)).perform(click()); //go to main menu
        onView(withId(R.id.loginBtn)).perform(click()); //go to login

        onView(withId(R.id.usernameText)).perform(typeText(loginUser), closeSoftKeyboard()); //sign in as yes, yes
        onView(withId(R.id.passwordText)).perform(typeText(loginUser), closeSoftKeyboard());
        onView(withId(R.id.loginGoBtn)).perform(click()); //login
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.loginCancelBtn)).perform(click()); //go to main menu

        onView(withId(R.id.friendsBtn)).perform(click());
        onView(withId(R.id.fListBtn)).perform(click());
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.bkBtn)).perform(click());

        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void H_testSettings(){
        String loginUser = "yes";
        onView(withId(R.id.toMenuBtn)).perform(click()); //go to main menu
        onView(withId(R.id.loginBtn)).perform(click()); //go to login

        onView(withId(R.id.usernameText)).perform(typeText(loginUser), closeSoftKeyboard()); //sign in as yes, yes
        onView(withId(R.id.passwordText)).perform(typeText(loginUser), closeSoftKeyboard());
        onView(withId(R.id.loginGoBtn)).perform(click()); //login
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Okay")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click()); //close dialog box
        onView(withId(R.id.loginCancelBtn)).perform(click()); //go to main menu

        onView(withId(R.id.setBtn)).perform(click());
        try { //wait for server
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.prevBtn)).perform(click());

    }
}