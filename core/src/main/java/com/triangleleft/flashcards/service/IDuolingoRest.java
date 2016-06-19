package com.triangleleft.flashcards.service;

import com.triangleleft.flashcards.service.cards.rest.FlashcardResponseModel;
import com.triangleleft.flashcards.service.cards.rest.FlashcardResultsController;
import com.triangleleft.flashcards.service.cards.rest.PostFlashcardsResponseModel;
import com.triangleleft.flashcards.service.login.rest.model.LoginResponseModel;
import com.triangleleft.flashcards.service.settings.rest.model.LanguageDataModel;
import com.triangleleft.flashcards.service.settings.rest.model.UserDataModel;
import com.triangleleft.flashcards.service.vocabular.rest.VocabularResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


public interface IDuolingoRest {
    @FormUrlEncoded
    @POST("/login")
    Call<LoginResponseModel> login(@Field("login") String login, @Field("password") String password);

    @GET("/vocabulary/overview")
    Observable<VocabularResponseModel> getVocabularList(@Query("_") long timestamp);

    @GET("/api/1/flashcards")
    Observable<FlashcardResponseModel> getFlashcardData(@Query("n") int count,
                                                        @Query("allow_partial_deck") boolean allowPartialDeck,
                                                        @Query("_") long timestamp);

    @POST("/api/1/flashcards")
    Observable<PostFlashcardsResponseModel> postFlashcardResults(@Body FlashcardResultsController model);

    @FormUrlEncoded
    @POST("/switch_language")
    Observable<LanguageDataModel> switchLanguage(@Field("learning_language") String languageId);

    @GET("/api/1/users/show")
    Observable<UserDataModel> getUserData(@Query("id") String userId);

}
