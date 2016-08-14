package com.triangleleft.flashcards.di.main;

import com.triangleleft.flashcards.di.AndroidApplicationComponent;
import com.triangleleft.flashcards.di.scope.ActivityScope;
import com.triangleleft.flashcards.ui.main.MainActivity;
import com.triangleleft.flashcards.ui.main.MainPageModule;
import com.triangleleft.flashcards.ui.main.MainPresenter;
import com.triangleleft.flashcards.ui.vocabular.VocabularyNavigator;

import dagger.Component;

@ActivityScope
@Component(dependencies = AndroidApplicationComponent.class, modules = MainPageModule.class)
public interface MainPageComponent extends AndroidApplicationComponent {

    MainPresenter mainPresenter();

    VocabularyNavigator vocabularNavigator();

    void inject(MainActivity mainView);
}
