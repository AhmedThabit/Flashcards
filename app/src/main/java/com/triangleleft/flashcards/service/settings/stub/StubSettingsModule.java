package com.triangleleft.flashcards.service.settings.stub;

import com.annimon.stream.Optional;
import com.triangleleft.flashcards.service.settings.Language;
import com.triangleleft.flashcards.service.settings.SettingsModule;
import com.triangleleft.flashcards.service.settings.UserData;
import com.triangleleft.flashcards.util.FunctionsAreNonnullByDefault;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

@FunctionsAreNonnullByDefault
public class StubSettingsModule implements SettingsModule {

    private final static int DELAY = 0;
    private List<Language> languages = Arrays.asList(
            new Language("en", "English", 4, false, false),
            new Language("es", "Spanish", 3, true, false),
            new Language("de", "German", 2, true, true),
            new Language("fr", "French", 5, true, false),
            new Language("du", "Dutch", 1, true, false)
    );
    private String avatarUrl =
            "http://i2.wp.com/bato.to/forums/public/style_images/subway/profile/default_large.png";
    private String userName = "userName";
    private String uiLanguage = "en";
    private String learningLanguage = " de";

    @Inject
    public StubSettingsModule() {

    }

    private Optional<UserData> getCurrentUserData() {
        return Optional.of(new UserData(
                Collections.unmodifiableList(languages),
                avatarUrl,
                userName,
                uiLanguage,
                learningLanguage));
    }

    @Override
    public Observable<UserData> userData() {
        return Observable.just(getCurrentUserData().get())
                .delay(DELAY, TimeUnit.MILLISECONDS);
    }

    @Override
    public Observable<Object> switchLanguage(Language language) {
//        languages = Stream.of(languages)
//                .map(stub -> stub.withCurrentLearning(stub.getId().equals(language.getId())))
//                .sortBy(Language::getId)
//                .collect(Collectors.toList());
        return Observable.just(new Object());
    }
}