package vn.edu.hcmut.linexo.presentation.view_model;

import io.reactivex.Observer;
import vn.edu.hcmut.linexo.utils.Event;

public interface ViewModelCallback {
    /**
     * View subscribe an observer to ViewModel to listen event emitted from it.
     * @param e listen event from ViewModel
     */
    void onHelp(Event e);
}
