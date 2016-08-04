package com.triangleleft.flashcards.ui.login;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static com.triangleleft.flashcards.test.EspressoUtils.checkHasView;
import static com.triangleleft.flashcards.test.EspressoUtils.withError;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.triangleleft.flashcards.MockFlashcardsApplication;
import com.triangleleft.flashcards.MockWebServerRule;
import com.triangleleft.flashcards.R;
import com.triangleleft.flashcards.test.MockJsonResponse;
import com.triangleleft.flashcards.test.MockServerResponse;
import com.triangleleft.flashcards.util.PersistentStorage;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTestCase {

    private static final Logger logger = LoggerFactory.getLogger(LoginActivityTestCase.class);

    private MockWebServerRule webServerRule = new MockWebServerRule();

    private ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class, true, false);

    @Rule
    public RuleChain ruleChain = RuleChain.outerRule(webServerRule)
            .around(activityRule);

    private MockWebServer webServer = webServerRule.getWebServer();


    @Before
    public void setUp() {
        logger.debug("setUp() called");
        MockitoAnnotations.initMocks(this);

        PersistentStorage storage = MockFlashcardsApplication.getInstance().getComponent().persistentStorage();
        storage.put("SimpleAccountModule::rememberUser", false);
        activityRule.launchActivity(new Intent(Intent.ACTION_MAIN));
    }

    @Test
    public void progressIsShownWhileRequest() {
        enterCredentials();

        // Verify progress is shown
        checkHasView(withId(R.id.login_progress));

        webServer.enqueue(MockServerResponse.makeNetworkErrorResponse());
    }

    // TODO: Some timeout problems
    //    @Test
    //    @MockJsonResponse("login/wrong_login_response.json")
    //    public void loginErrorIsShown() {
    //        enterCredentials();
    //
    //        checkHasView(withError(R.string.wrong_login));
    //    }

    @Test
    @MockJsonResponse("login/wrong_password_response.json")
    public void passwordErrorIsShown() throws InterruptedException {
        enterCredentials();

        checkHasView(withError(R.string.wrong_password));
    }

    @Test
    public void toastIsShownForNetworkError() throws InterruptedException {
        webServer.enqueue(MockServerResponse.makeNetworkErrorResponse());

        enterCredentials();

        // Check that toast is shown
        onView(withText(R.string.login_network_error))
                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    @MockJsonResponse(value = "internal_server_error.txt", httpCode = 500)
    public void toastIsShownForGenericError() throws InterruptedException {
        enterCredentials();

        // Check that toast is shown
        onView(withText(R.string.login_generic_error))
                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    private void enterCredentials() {
        onView(withId(R.id.login_email)).perform(typeText("login"));
        onView(withId(R.id.login_password)).perform(typeText("password")).perform(closeSoftKeyboard());
        onView(withText(R.string.action_sign_in)).perform(click());
    }

}
