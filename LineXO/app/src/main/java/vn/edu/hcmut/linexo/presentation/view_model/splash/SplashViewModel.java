package vn.edu.hcmut.linexo.presentation.view_model.splash;

import android.databinding.BaseObservable;

import android.os.Handler;

import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.utils.Event;

public class SplashViewModel extends BaseObservable implements ViewModel {

    /**
     * Publisher will emit event to view. View listen these event via a observer.
     */
    private PublishSubject<Event> publisher = PublishSubject.create();

    public SplashViewModel() {

    }

    @Override
    public void subscribeObserver(Observer<Event> observer) {
        publisher.subscribe(observer);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                publisher.onNext(Event.create(Event.END_SPLASH));
            }
        },2000);
    }

    @Override
    public void endTask() {

    }
}
