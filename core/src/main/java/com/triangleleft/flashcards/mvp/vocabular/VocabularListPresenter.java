package com.triangleleft.flashcards.mvp.vocabular;

import com.triangleleft.flashcards.mvp.common.di.scope.FragmentScope;
import com.triangleleft.flashcards.mvp.common.presenter.AbstractPresenter;
import com.triangleleft.flashcards.service.vocabular.IVocabularModule;
import com.triangleleft.flashcards.service.vocabular.IVocabularWord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Scheduler;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

@FragmentScope
public class VocabularListPresenter extends AbstractPresenter<IVocabularListView> {

    private static final Logger logger = LoggerFactory.getLogger(VocabularListPresenter.class);

    private final IVocabularModule vocabularModule;
    private final IVocabularNavigator navigator;
    private final Scheduler mainThreadScheduler;
    private List<IVocabularWord> wordList;
    private Subscription subscription = Subscriptions.empty();

    @Inject
    public VocabularListPresenter(@NonNull IVocabularModule vocabularModule, @NonNull IVocabularNavigator navigator,
                                  @NonNull Scheduler mainThreadScheduler) {
        super(IVocabularListView.class);
        this.vocabularModule = vocabularModule;
        this.navigator = navigator;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    public void onBind(IVocabularListView view) {
        super.onBind(view);
        if (wordList != null) {
            getView().showWords(wordList);
        } else {
            onLoadList();
        }
    }

    public void onWordSelected(@NonNull IVocabularWord word) {
        logger.debug("onWordSelected() called with: word = [{}]", word);
        navigator.onWordSelected(word);
    }

    public void onLoadList() {
        logger.debug("onLoadList() called");
        getView().showProgress();
        loadList(false);
    }

    @Override
    public void onDestroy() {
        logger.debug("onDestroy() called");
        subscription.unsubscribe();
    }

    public void onRefreshList() {
        loadList(true);
    }

    private void loadList(boolean refresh) {
        subscription.unsubscribe();
        subscription = vocabularModule.getVocabularList(refresh)
                .observeOn(mainThreadScheduler)
                .subscribe(
                        list -> getView().showWords(wordList = list),
                        error -> {
                            if (refresh) {
                                getView().showError();
                            } else {
                                getView().showErrorNoContent();
                            }
                        });
    }
}