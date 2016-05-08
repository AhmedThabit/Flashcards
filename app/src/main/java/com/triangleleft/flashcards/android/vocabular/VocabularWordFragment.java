package com.triangleleft.flashcards.android.vocabular;

import com.triangleleft.flashcards.R;
import com.triangleleft.flashcards.android.BaseFragment;
import com.triangleleft.flashcards.android.main.MainActivity;
import com.triangleleft.flashcards.mvp.main.MainPageComponent;
import com.triangleleft.flashcards.mvp.vocabular.DaggerVocabularWordComponent;
import com.triangleleft.flashcards.mvp.vocabular.VocabularWordComponent;
import com.triangleleft.flashcards.mvp.vocabular.VocabularWordPresenter;
import com.triangleleft.flashcards.mvp.vocabular.IVocabularWordView;
import com.triangleleft.flashcards.service.vocabular.IVocabularWord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VocabularWordFragment
        extends BaseFragment<VocabularWordComponent, IVocabularWordView, VocabularWordPresenter>
        implements IVocabularWordView {

    private static final Logger logger = LoggerFactory.getLogger(VocabularWordFragment.class);
    public static final String KEY_WORD = "keyWord";
    public static final String TAG = VocabularWordFragment.class.getSimpleName();

    @Bind(R.id.fragment_vocabular_word_title)
    TextView titleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_vocabular_word, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tryShowWord();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            tryShowWord();
        }
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
    }

    @NonNull
    @Override
    protected VocabularWordComponent buildComponent() {
        logger.debug("buildComponent() called");
        return DaggerVocabularWordComponent.builder().mainPageComponent(getMainPageComponent()).build();
    }

    private MainPageComponent getMainPageComponent() {
        return ((MainActivity) getActivity()).getComponent();
    }

    @NonNull
    @Override
    protected IVocabularWordView getMvpView() {
        logger.debug("getMvpView() called");
        return this;
    }

    private void tryShowWord() {
        IVocabularWord word = getArguments().getParcelable(KEY_WORD);
        if (word != null) {
            titleView.setText(word.getWord());
        }
    }
}
