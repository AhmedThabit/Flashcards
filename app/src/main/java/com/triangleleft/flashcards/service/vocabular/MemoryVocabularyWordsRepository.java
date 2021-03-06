package com.triangleleft.flashcards.service.vocabular;

import static com.annimon.stream.Collectors.toList;

import com.annimon.stream.Stream;
import com.triangleleft.flashcards.util.FunctionsAreNonnullByDefault;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@FunctionsAreNonnullByDefault
public class MemoryVocabularyWordsRepository implements VocabularyWordsRepository {

    Set<VocabularyWord> cache = new HashSet<>();

    @Override
    public List<VocabularyWord> getWords(String uiLanguageId, String learningLanguageId) {
        return Stream.of(cache)
                .filter(word -> word.getUiLanguage().equals(uiLanguageId))
                .filter(word -> word.getLearningLanguage().equals(learningLanguageId))
                .collect(toList());
    }

    @Override
    public void putWords(List<VocabularyWord> words) {
        cache.addAll(words);
    }
}
