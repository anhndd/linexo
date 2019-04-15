package vn.edu.hcmut.linexo.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import vn.edu.hcmut.linexo.presentation.custom.CircleImageView;
import vn.edu.hcmut.linexo.presentation.custom.ClockView;
import vn.edu.hcmut.linexo.presentation.custom.RoomNumberView;
import vn.edu.hcmut.linexo.presentation.view.play.BoardView;
import vn.edu.hcmut.linexo.presentation.view.play.PlayLayout;
import vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel;

public abstract class ActivityPlayBinding extends ViewDataBinding {
  @NonNull
  public final CircleImageView avatar1;

  @NonNull
  public final CircleImageView avatar2;

  @NonNull
  public final BoardView board;

  @NonNull
  public final Button btnMessage;

  @NonNull
  public final ImageButton btnSend;

  @NonNull
  public final RecyclerView lstMessage;

  @NonNull
  public final PlayLayout root;

  @NonNull
  public final View scoreIcon1;

  @NonNull
  public final View scoreIcon2;

  @NonNull
  public final ClockView txtClock1;

  @NonNull
  public final ClockView txtClock2;

  @NonNull
  public final TextView txtNetwork;

  @NonNull
  public final RoomNumberView txtRoom;

  @NonNull
  public final TextView txtScore1;

  @NonNull
  public final TextView txtScore2;

  @Bindable
  protected PlayViewModel mViewModel;

  protected ActivityPlayBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, CircleImageView avatar1, CircleImageView avatar2, BoardView board,
      Button btnMessage, ImageButton btnSend, RecyclerView lstMessage, PlayLayout root,
      View scoreIcon1, View scoreIcon2, ClockView txtClock1, ClockView txtClock2,
      TextView txtNetwork, RoomNumberView txtRoom, TextView txtScore1, TextView txtScore2) {
    super(_bindingComponent, _root, _localFieldCount);
    this.avatar1 = avatar1;
    this.avatar2 = avatar2;
    this.board = board;
    this.btnMessage = btnMessage;
    this.btnSend = btnSend;
    this.lstMessage = lstMessage;
    this.root = root;
    this.scoreIcon1 = scoreIcon1;
    this.scoreIcon2 = scoreIcon2;
    this.txtClock1 = txtClock1;
    this.txtClock2 = txtClock2;
    this.txtNetwork = txtNetwork;
    this.txtRoom = txtRoom;
    this.txtScore1 = txtScore1;
    this.txtScore2 = txtScore2;
  }

  public abstract void setViewModel(@Nullable PlayViewModel viewModel);

  @Nullable
  public PlayViewModel getViewModel() {
    return mViewModel;
  }

  @NonNull
  public static ActivityPlayBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityPlayBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityPlayBinding>inflate(inflater, vn.edu.hcmut.linexo.R.layout.activity_play, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityPlayBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityPlayBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityPlayBinding>inflate(inflater, vn.edu.hcmut.linexo.R.layout.activity_play, null, false, component);
  }

  public static ActivityPlayBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityPlayBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityPlayBinding)bind(component, view, vn.edu.hcmut.linexo.R.layout.activity_play);
  }
}
