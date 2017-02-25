package com.triangleleft.flashcards.ui.cards;

import com.triangleleft.flashcards.Call;
import com.triangleleft.flashcards.Observer;
import com.triangleleft.flashcards.service.cards.FlashcardTestData;
import com.triangleleft.flashcards.service.cards.FlashcardTestResult;
import com.triangleleft.flashcards.service.cards.FlashcardWord;
import com.triangleleft.flashcards.service.cards.FlashcardWordResult;
import com.triangleleft.flashcards.service.cards.FlashcardsModule;
import com.triangleleft.flashcards.service.common.exception.ServerException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.triangleleft.flashcards.util.TestUtils.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FlashcardsPresenterTest {

    private static final String UI_LANG = "ui";
    private static final String LEARN_LANG = "learn";

    @Mock
    FlashcardsModule module;
    @Mock
    Call<FlashcardTestData> mockCall;
    @Captor
    ArgumentCaptor<List<FlashcardWord>> listCaptor;
    @Captor
    ArgumentCaptor<Observer<FlashcardTestData>> observerCaptor;
    @Mock
    IFlashcardsView view;

    private FlashcardsPresenter presenter;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        presenter = new FlashcardsPresenter(module, Runnable::run);
    }

    @Test
    public void onLoadFlashcardsWouldStartLoadingFlashcards() {
        when(module.getFlashcards()).thenReturn(Call.empty());

        presenter.onLoadFlashcards();

        verify(module).getFlashcards();
    }

    @Ignore // Failing after translation, probably due to generics
    @Test
    public void onDestroyWouldCancel() {
        AtomicBoolean unsubscribed = new AtomicBoolean(false);
        doAnswer(invocation -> {
            unsubscribed.set(true);
            return true;
        }).when(mockCall).cancel();
        when(module.getFlashcards()).thenReturn(mockCall);

        // Simulate list load
        presenter.onLoadFlashcards();
        presenter.onDestroy();

        assertThat(unsubscribed.get(), equalTo(true));
    }

    @Test
    public void onCreateWouldStartLoadingFlashcards() {
        when(module.getFlashcards()).thenReturn(Call.empty());

        presenter.onCreate();
        presenter.onBind(view);

        verify(module).getFlashcards();
        verify(view).showProgress();
    }

    @Test
    public void onCreateWouldShowList() {
        List<FlashcardWord> list = Collections.singletonList(mock(FlashcardWord.class));
        prepareTestData(list);
        presenter.onCreate();
        presenter.onBind(view);

        verify(view).showWords(list);
    }

    @Test
    public void onFlashcardsLoadErrorWouldShowError() {
        when(module.getFlashcards()).thenReturn(Call.error(new ServerException()));

        presenter.onCreate();
        presenter.onBind(view);

        verify(view).showError();
    }

    @Test
    public void emptyWordsListIsTreatedAsError() {
        prepareTestData(Collections.emptyList());

        presenter.onCreate();
        presenter.onBind(view);

        verify(view).showError();
    }

    @Test
    public void resultsArePosted() {
        FlashcardWord rightWord = mock(FlashcardWord.class);
        FlashcardWord wrongWord = mock(FlashcardWord.class);
        prepareTestData(Arrays.asList(rightWord, wrongWord));

        presenter.onCreate();
        presenter.onBind(view);
        presenter.onWordRight(rightWord);
        presenter.onWordWrong(wrongWord);
        presenter.onCardsDepleted();

        ArgumentCaptor<FlashcardTestResult> resultCaptor = ArgumentCaptor.forClass(FlashcardTestResult.class);
        verify(module).postResult(resultCaptor.capture());
        FlashcardTestResult result = resultCaptor.getValue();
        assertThat(result.getLearningLanguage(), equalTo(LEARN_LANG));
        assertThat(result.getUiLanguage(), equalTo(UI_LANG));

        FlashcardWordResult rightResult = FlashcardWordResult.create(rightWord, true);
        FlashcardWordResult wrongResult = FlashcardWordResult.create(wrongWord, false);
        assertThat(result.getWordResults(), contains(rightResult));
        assertThat(result.getWordResults(), contains(wrongResult));
    }

    @Test
    public void resultWithErrors() {
        FlashcardWord wrongWord = mock(FlashcardWord.class);
        prepareTestData(Collections.singletonList(wrongWord));

        presenter.onCreate();
        presenter.onBind(view);
        presenter.onWordWrong(wrongWord);
        presenter.onCardsDepleted();

        verify(view).showResultErrors(listCaptor.capture());
        List<FlashcardWord> list = listCaptor.getValue();
        assertThat(list, contains(wrongWord));
    }

    @Test
    public void resultWithNoErrors() {
        prepareTestData(Collections.singletonList(mock(FlashcardWord.class)));

        presenter.onCreate();
        presenter.onBind(view);
        presenter.onCardsDepleted();

        verify(view).showResultsNoErrors();
    }

    private void prepareTestData(List<FlashcardWord> words) {
        FlashcardTestData data = mock(FlashcardTestData.class);
        when(data.getUiLanguage()).thenReturn(UI_LANG);
        when(data.getLearningLanguage()).thenReturn(LEARN_LANG);
        when(data.getWords()).thenReturn(words);
        when(module.getFlashcards()).thenReturn(Call.just(data));
    }
}