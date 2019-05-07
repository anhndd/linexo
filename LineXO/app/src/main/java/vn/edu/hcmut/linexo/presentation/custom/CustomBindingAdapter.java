package vn.edu.hcmut.linexo.presentation.custom;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.v7.widget.RecyclerView;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.view.play.BoardView;
import vn.edu.hcmut.linexo.presentation.view.play.PlayLayout;

/**
 * Class included methods that used in xml file to bind attribute or
 * something else of view.
 */

public class CustomBindingAdapter {

    /**
     * Add setter to bind {@code RecyclerView.Adapter} to a {@code RecyclerView}.
     * @param recyclerView view need to set listener.
     * @param adapter used to set into view.
     */
    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    /**
     * Add setter for {@code src_from_url} in {@code AvatarImageView}.
     * @param circleImageView view need to set image.
     * @param object used to load image to {@code AvatarImageView}.
     */
    @BindingAdapter("src_from_object")
    public static void setImageFromObject(CircleImageView circleImageView, Object object) {
        circleImageView.setImageFromObject(object);
    }

    /**
     * Add setter for {@code board} in {@code BoardView}.
     * @param boardView view need to set board.
     * @param board used to load board to {@code BoardView}.
     */
    @BindingAdapter("board")
    public static void setBoard(BoardView boardView, Board board) {
        boardView.setBoard(board);
    }

    /**
     * Add setter for {@code touch} in {@code BoardView}.
     * @param boardView view need to set touch.
     * @param touch used to load touch to {@code BoardView}.
     */
    @BindingAdapter("touch")
    public static void setTouch(BoardView boardView, int[] touch) {
        boardView.setTouch(touch);
    }

    /**
     * Add getter for {@code touch} in {@code BoardView}.
     * @param boardView view need to get touch.
     */
    @InverseBindingAdapter(attribute = "touch")
    public static int[] getTouch(BoardView boardView) {
        return boardView.getTouch();
    }

    /**
     * Add listener to know when attribute {@code touch} change value.
     * @param boardView View need to listen an attribute changing.
     * @param listener used to notify attribute change.
     */
    @BindingAdapter("touchAttrChanged")
    public static void setTouchListener(BoardView boardView, final InverseBindingListener listener) {
        boardView.setCallback(() -> listener.onChange());
    }

    /**
     * Add listener to know when attribute {@code touch} change value.
     * @param playLayout View need to listen an attribute changing.
     * @param array array 2 elements height and orientation.
     */
    @BindingAdapter("onKeyboardChanged")
    public static void setKeyboardChanged(PlayLayout playLayout, int[] array) {
        playLayout.setKeyboardHeight(array[0],array[1]);
    }

    /**
     * Add listener to know when attribute {@code touch} change value.
     * @param roomNumberView View need to listen an attribute changing.
     * @param roomNumber room ID in integer type.
     */
    @BindingAdapter("roomNumber")
    public static void setKeyboardChanged(RoomNumberView roomNumberView, int roomNumber) {
        roomNumberView.setNumber(roomNumber);
    }

    /**
     * Add listener to know when attribute {@code time} change value.
     * @param clockView View need to listen an attribute changing.
     * @param countTime time countdown.
     */
    @BindingAdapter("time")
    public static void setCountTime(ClockView clockView, long countTime) {
        clockView.setCountTime(countTime);
    }

    /**
     * Add listener to know when attribute {@code play_type} change value.
     * @param playLayout View need to listen an attribute changing.
     * @param isAI true if type is AI, otherwise.
     */
    @BindingAdapter("play_type")
    public static void setPlayType(PlayLayout playLayout, boolean isAI) {
        playLayout.setPlayType(isAI);
    }
}
