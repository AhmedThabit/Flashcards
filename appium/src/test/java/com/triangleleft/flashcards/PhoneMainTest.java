/*
 *
 * =======================================================================
 *
 * Copyright (c) 2014-2015 Domlex Limited. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Domlex Limited.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with
 * Domlex Limited.
 *
 * =======================================================================
 *
 */

package com.triangleleft.flashcards;

import static com.triangleleft.flashcards.TestUtils.hasText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.triangleleft.flashcards.page.MainPage;
import com.triangleleft.flashcards.page.VocabularyListPage;
import com.triangleleft.flashcards.page.VocabularyWordPage;
import com.triangleleft.flashcards.rule.AppiumAndroidRule;
import com.triangleleft.flashcards.rule.AppiumRule;
import com.triangleleft.flashcards.service.vocabular.VocabularyWord;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.Collections;

@RunWith(JUnit4.class)
public class PhoneMainTest {

    private final static VocabularyWord WORD_MUJER = VocabularyWord.create(
        "mujer",
        "mujer",
        Optional.of("Noun"),
        Optional.of("Feminine"),
        4,
        Collections.singletonList("woman"),
        "en",
        "es"
    );

    private final static VocabularyWord WORD_TU = VocabularyWord.create(
        "t�",
        "t�",
        Optional.of("Pronoun"),
        Optional.empty(),
        4,
        Collections.singletonList("you"),
        "en",
        "es"
    );

    @Rule
    public AppiumRule appiumRule = new AppiumAndroidRule();

    @Test
    public void words() throws InterruptedException {
        clickOn(WORD_MUJER);
        checkWordInfo(WORD_MUJER);

        MainPage mainPage = appiumRule.getApp().mainPage();
        mainPage.drawerButton.click();

        clickOn(WORD_TU);
        checkWordInfo(WORD_TU);
    }

    private void clickOn(VocabularyWord word) {
        VocabularyListPage listPage = appiumRule.getApp().vocabularyListPage();
        assertThat(listPage.list.isDisplayed(), is(true));

        // Get our word
        WebElement webElement = Stream.of(listPage.words)
            .filter(element -> word.getWord().equals(element.getText()))
            .findFirst().get();
        // Click it
        webElement.click();
    }

    private void checkWordInfo(VocabularyWord word) {
        VocabularyWordPage wordPage = appiumRule.getApp().vocabularyWordPage();
        // Check that all fields are shown
        assertThat(wordPage.title, hasText(word.getWord()));
        if (!word.getTranslations().isEmpty()) {
            assertThat(wordPage.translation, hasText(word.getTranslations().get(0)));
        } else {
            checkNotVisible(wordPage.translation);
        }
        if (word.getGender().isPresent()) {
            assertThat(wordPage.gender, hasText(word.getGender().get()));
        } else {
            checkNotVisible(wordPage.gender);
        }
        if (word.getPos().isPresent()) {
            assertThat(wordPage.pos, hasText(word.getPos().get()));
        } else {
            checkNotVisible(wordPage.pos);
        }
    }

    private void checkNotVisible(WebElement element) {
        appiumRule.getTimeOutDuration().setTime(0);
        boolean displayed = false;
        try {
            displayed = element.isDisplayed();
        } catch (NoSuchElementException e) {
            // Ignored;
        }
        assertThat(displayed, is(false));
        appiumRule.getTimeOutDuration().setTime(60);
    }
}
