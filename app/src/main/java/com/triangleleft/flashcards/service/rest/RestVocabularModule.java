package com.triangleleft.flashcards.service.rest;

import com.triangleleft.flashcards.service.ICommonListener;
import com.triangleleft.flashcards.service.IVocabularModule;
import com.triangleleft.flashcards.service.IVocabularWord;

import java.util.List;

import javax.inject.Inject;

public class RestVocabularModule implements IVocabularModule {
    private final IDuolingoRest service;
    private ICommonListener listener;

    @Inject
    public RestVocabularModule(IDuolingoRest service) {
        this.service = service;
    }


    @Override
    public List<IVocabularWord> getData() {
        return null;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void registerListener(ICommonListener listener) {

    }

    @Override
    public void unregisterListener(ICommonListener listener) {

    }
}