package vn.edu.hcmut.linexo.presentation.view_model;

import vn.edu.hcmut.linexo.utils.Event;

public interface ViewModelCallback {
    /**
     * Execute an action given by {@code e}.
     * @param e an event.
     */
    void onHelp(Event e);
}
