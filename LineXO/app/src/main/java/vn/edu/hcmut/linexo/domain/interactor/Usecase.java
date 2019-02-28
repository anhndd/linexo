package vn.edu.hcmut.linexo.domain.interactor;

import android.support.annotation.Nullable;

public interface Usecase {

    /**
     * Execute an usecase with an observer and a list of data.
     * A flag can be given in case the usecase have many actions.
     * @param observer listen event from usecase.
     * @param flag specify specific actions.
     * @param params data to execute usecase.
     */
    void execute(Object observer, @Nullable int flag, @Nullable Object... params);

    /**
     * Stop executing usecase when don't want to listen event from it
     * in future.
     */
    void endTask();
}
