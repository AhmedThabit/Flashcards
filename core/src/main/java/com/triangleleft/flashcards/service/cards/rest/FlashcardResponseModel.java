package com.triangleleft.flashcards.service.cards.rest;

import com.google.gson.annotations.SerializedName;

import com.triangleleft.flashcards.service.cards.IFlashcardWord;

import java.util.Collections;
import java.util.List;

public class FlashcardResponseModel {

    @SerializedName("ui_language")
    public String uiLanguage;
    @SerializedName("learning_language")
    public String learningLanguage;
    @SerializedName("flashcard_data")
    public List<FlashcardModel> flashcardData;

    public List<IFlashcardWord> getWords() {
        return Collections.unmodifiableList(flashcardData);
    }

    public static class FlashcardModel implements IFlashcardWord {
        @SerializedName("ui_word")
        public String translation;
        @SerializedName("id")
        public String id;
        @SerializedName("learning_word")
        public String word;

        @Override
        public String getWord() {
            return word;
        }

        @Override
        public String getTranslation() {
            return translation;
        }

        @Override
        public String getId() {
            return id;
        }
    }

}