package vn.edu.hcmut.linexo.presentation.custom;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.view.play.BoardView;

/**
 * Class included methods that used in xml file to bind attribute or
 * something else of view.
 */

public class CustomBindingAdapter {

    /**
     * Add setter to bind {@code View.OnTouchListener} to a {@code View}.
     * @param view view need to set listener.
     * @param listener used to set into view.
     */
    @BindingAdapter("onTouch")
    public static void setOnTouchListener(View view, View.OnTouchListener listener) {
        view.setOnTouchListener(listener);
    }

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
     * @param avatarImageView view need to set image.
     * @param object used to load image to {@code AvatarImageView}.
     */
    @BindingAdapter("src_from_object")
    public static void setImageFromObject(CircleImageView avatarImageView, Object object) {
        avatarImageView.setImageFromObject(object);
    }

    /**
     * Add setter for {@code board} in {@code BoardView}.
     * @param boardView view need to set board.
     * @param board used to load board to {@code BoardView}.
     */
    @BindingAdapter("board")
    public static void setBoard(BoardView boardView, Board board) {
        boardView.setBoard(board);
    };

    /**
     * Add getter for {@code user_move} in {@code BoardView}.
     * @param boardView view need to set board.
     */
    @BindingAdapter("move")
    public static void setMove(BoardView boardView, int[] move) {
        boardView.setMove(move);
    }

    /**
     * Add getter for {@code user_move} in {@code BoardView}.
     * @param boardView view need to set board.
     */
    @InverseBindingAdapter(attribute = "move")
    public static int[] getMove(BoardView boardView) {
        return boardView.getMove();
    }


    @BindingAdapter("moveAttrChanged")
    public static void setMoveListener(BoardView boardView, final InverseBindingListener listener) {
        boardView.setCallback(() -> {
            listener.onChange();
        });
    };
}
