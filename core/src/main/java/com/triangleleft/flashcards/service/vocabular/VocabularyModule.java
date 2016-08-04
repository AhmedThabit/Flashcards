package com.triangleleft.flashcards.service.vocabular;

import com.triangleleft.flashcards.util.FunctionsAreNonnullByDefault;

import java.util.List;

import rx.Observable;

@FunctionsAreNonnullByDefault
public interface VocabularyModule {

    Observable<List<VocabularyWord>> loadVocabularyWords();

    Observable<List<VocabularyWord>> refreshVocabularyWords();
}
