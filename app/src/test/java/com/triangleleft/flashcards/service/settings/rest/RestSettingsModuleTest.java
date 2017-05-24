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

package com.triangleleft.flashcards.service.settings.rest;

import com.triangleleft.flashcards.service.RestService;
import com.triangleleft.flashcards.service.account.AccountModule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class RestSettingsModuleTest {

    private RestSettingsModule module;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    RestService service;
    @Mock
    AccountModule accountModule;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        module = new RestSettingsModule(service, accountModule);
    }

//    @Test
//    public void userData() {
//        when(accountModule.getUserId()).thenReturn(Optional.of("id"));
//        UserDataModel model = mock(UserDataModel.class);
//        UserData userData = UserData.create(Collections.emptyList(), "", "", "", "");
//        when(model.toUserData()).thenReturn(userData);
//        when(service.getUserData("id")).thenReturn(Call.just(model));
//
//        TestObserver<UserData> observer = new TestObserver<>();
//        module.userData().enqueue(observer);
//
//        observer.assertValue(userData);
//    }
//
//    @Test
//    public void switchLanguage() {
//        Language language = Language.create("id", "lang", 0, true, true);
//        when(service.switchLanguage(any(SwitchLanguageController.class))).thenReturn(Call.just(null));
//
//        TestObserver<Object> observer = new TestObserver<>();
//        module.switchLanguage(language).enqueue(observer);
//
//        observer.assertOnNextCalled();
//    }
}