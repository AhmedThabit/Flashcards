package com.triangleleft.flashcards.ui.common.presenter

import com.triangleleft.flashcards.ui.ViewAction
import com.triangleleft.flashcards.ui.ViewEvent
import com.triangleleft.flashcards.ui.ViewState
import com.triangleleft.flashcards.ui.common.view.IView
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by aleksey.kurnosenko on 05.05.2017.
 */

open class AbstractRxPresenter<View : IView<VS, VE>, VS : ViewState, VE : ViewEvent>(private val scheduler: Scheduler)
    : AbstractPresenter<View, VS, VE>() {

    private var disposable = CompositeDisposable()
    private val events = PublishSubject.create<VE>()
    private val viewStates = BehaviorSubject.create<VS>()
    private var setupCalled = false

    @Synchronized
    override final fun onRebind(view: View) {
        super.onRebind(view)
        check(setupCalled, { "setup() was not called" })

        disposable = CompositeDisposable()
        // Connect view observables to our publish subjects
        // Connect view to our behavior subject
        disposable.addAll(
                viewStates.subscribe { view.render(it) },
                view.events().subscribe { events.onNext(it) }
        )
    }

    override final fun onUnbind() {
        super.onUnbind()

        disposable.dispose()
    }

    override fun getInstanceViewState(): VS {
        return viewStates.value
    }

    protected fun viewEvents(): PublishSubject<VE> = events

    fun setup(transformer: ObservableTransformer<VE, ViewAction<VS>>, initialState: VS) {
        setupCalled = true
        viewEvents().compose(transformer)
                .scan(initialState) { viewState, viewAction -> viewAction.reduce(viewState) }
                .distinctUntilChanged()
                .observeOn(scheduler)
                .doOnNext { logger.debug("onNext() {}", it) }
                .subscribe { state ->
                    viewStates.onNext(state)
                }

        // Set initial state
        viewStates.onNext(initialState)
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(AbstractRxPresenter::class.java)
    }
}
