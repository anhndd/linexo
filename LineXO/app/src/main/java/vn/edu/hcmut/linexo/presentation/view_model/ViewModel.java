package vn.edu.hcmut.linexo.presentation.view_model;

import io.reactivex.Observer;
import vn.edu.hcmut.linexo.utils.Event;

public interface ViewModel {

    /**
     * View subscribe an observer to ViewModel to listen event emitted from it.
     * @param observer listen event from ViewModel
     */
    void subscribeObserver(Observer<Event> observer);

    /**
     * View subscribe an observer to ViewModel to listen event emitted from it.
     * @param e listen event from ViewModel
     */
    void onHelp(Event e);

    /**
     *  View called this method to inform ViewModel stop usecase that in progress
     *  when it finish.
     */
    void endTask();
}
