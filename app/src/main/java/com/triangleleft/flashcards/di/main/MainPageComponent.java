package com.triangleleft.flashcards.di.main;

import com.triangleleft.flashcards.di.ApplicationComponent;
import com.triangleleft.flashcards.di.scope.ActivityScope;
import com.triangleleft.flashcards.ui.main.DrawerPresenter;
import com.triangleleft.flashcards.ui.main.MainActivity;
import com.triangleleft.flashcards.ui.main.MainPageModule;
import com.triangleleft.flashcards.ui.main.MainPresenter;
import com.triangleleft.flashcards.ui.main.drawer.NavigationView;
import com.triangleleft.flashcards.ui.vocabular.VocabularyNavigator;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = MainPageModule.class)
public interface MainPageComponent extends ApplicationComponent {

    MainPresenter mainPresenter();

    DrawerPresenter drawerPresenter();

    VocabularyNavigator vocabularNavigator();

    void inject(MainActivity mainView);

    void inject(NavigationView navigationView);
}
