package vn.edu.hcmut.linexo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;

/**
 * The keyboard height provider, this class uses a PopupWindow
 * to calculate the window height when the floating keyboard is opened and closed.
 */
public class KeyboardHeightProvider extends PopupWindow {

    /**
     * The root context that uses this KeyboardHeightProvider
     **/
    private Context context;

    /**
     * The view that is used to calculate the keyboard height
     **/
    private View popupView;

    /**
     * The parent view
     **/
    private View parentView;

    /**
     * Popup rectangle before and after keyboard display
     */
    private Rect oldRect;
    private Rect newRect;

    /**
     * The keyboard height observer
     **/
    private KeyboardHeightObserver observer;

    /**
     * Construct a new KeyboardHeightProvider
     * @param context The parent context
     */
    public KeyboardHeightProvider(Context context) {
        super(context);
        this.context = context;

        popupView = new View(context);
        popupView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        setContentView(popupView);
        setWidth(0);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new ColorDrawable(0));
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_RESIZE | LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        parentView = ((Activity)context).findViewById(android.R.id.content);

        popupView.getViewTreeObserver().addOnGlobalLayoutListener(this::handleOnGlobalLayout);
    }

    /**
     * Set the keyboard height observer to this provider. The
     * observer will be notified when the keyboard height has changed.
     * For example when the keyboard is opened or closed.
     * @param observer The observer to be added to this provider.
     */
    public void setKeyboardHeightObserver(KeyboardHeightObserver observer) {
        this.observer = observer;
    }

    /**
     * Start the KeyboardHeightProvider, this must be called after the onResume of the Activity.
     * PopupWindows are not allowed to be registered before the onResume has finished
     * of the Activity.
     */
    public void start() {
        showAtLocation(parentView, Gravity.NO_GRAVITY, 0, 0);
    }

    /**
     * Close the keyboard height provider,
     * this provider will not be used anymore.
     */
    public void stop() {
        dismiss();
    }

    /**
     * Reset state of the provider
     */
    public void reset() {
        oldRect = null;
    }


    /**
     * Popup window itself is as big as the window of the Activity.
     * The keyboard can then be calculated by extracting the popup view bottom
     * from the context window height.
     */
    private void handleOnGlobalLayout() {

        if (oldRect == null) {
            popupView.getWindowVisibleDisplayFrame(oldRect = new Rect());
        }

        popupView.getWindowVisibleDisplayFrame(newRect = new Rect());

        if (observer != null) {
            observer.onKeyboardHeightChanged(
                    oldRect.bottom - newRect.bottom,
                    context.getResources().getConfiguration().orientation
            );
        }
    }
}
