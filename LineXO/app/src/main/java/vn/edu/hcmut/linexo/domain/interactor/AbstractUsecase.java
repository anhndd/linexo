package vn.edu.hcmut.linexo.domain.interactor;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.disposables.CompositeDisposable;
import vn.edu.hcmut.linexo.domain.executor.TaskExecutor;

public abstract class AbstractUsecase implements Usecase {

    private Scheduler           subscribeScheduler;
    private Scheduler           observeScheduler;
    private CompositeDisposable compositeDisposable;

    public AbstractUsecase() {
        this.subscribeScheduler  = Schedulers.from(TaskExecutor.getInstance());
        this.observeScheduler    = AndroidSchedulers.mainThread();
        this.compositeDisposable = new CompositeDisposable();
    }

    public void addTask(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public Scheduler getSubscribeScheduler() {
        return subscribeScheduler;
    }

    public Scheduler getObserveScheduler() {
        return observeScheduler;
    }

    @Override
    public void endTask() {
        if (compositeDisposable.size() > 0) {
            compositeDisposable.clear();
        }
    }
}
