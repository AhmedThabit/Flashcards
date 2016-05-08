package com.triangleleft.flashcards.service;

import com.triangleleft.flashcards.service.cards.rest.FlashcardResponseModel;
import com.triangleleft.flashcards.service.login.rest.model.LoginResponseModel;
import com.triangleleft.flashcards.service.vocabular.rest.VocabularResponseModel;

import retrofit2.Call;
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
    Call<FlashcardResponseModel> getFlashcardData();


}
