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
import android.widget.EditText;
import android.widget.TextView;
import vn.edu.hcmut.linexo.presentation.custom.CircleImageView;
import vn.edu.hcmut.linexo.presentation.view.room.RoomLayout;
import vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel;

public abstract class ActivityRoomBinding extends ViewDataBinding {
  @NonNull
  public final View actionBar;

  @NonNull
  public final View advertisement;

  @NonNull
  public final CircleImageView avatar;

  @NonNull
  public final Button btnCreateRoom;

  @NonNull
  public final EditText edtSearch;

  @NonNull
  public final RecyclerView lstRoom;

  @NonNull
  public final RoomLayout root;

  @NonNull
  public final View scoreIcon;

  @NonNull
  public final TextView txtName;

  @NonNull
  public final TextView txtNetwork;

  @NonNull
  public final TextView txtScore;

  @Bindable
  protected RoomViewModel mViewModel;

  protected ActivityRoomBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, View actionBar, View advertisement, CircleImageView avatar,
      Button btnCreateRoom, EditText edtSearch, RecyclerView lstRoom, RoomLayout root,
      View scoreIcon, TextView txtName, TextView txtNetwork, TextView txtScore) {
    super(_bindingComponent, _root, _localFieldCount);
    this.actionBar = actionBar;
    this.advertisement = advertisement;
    this.avatar = avatar;
    this.btnCreateRoom = btnCreateRoom;
    this.edtSearch = edtSearch;
    this.lstRoom = lstRoom;
    this.root = root;
    this.scoreIcon = scoreIcon;
    this.txtName = txtName;
    this.txtNetwork = txtNetwork;
    this.txtScore = txtScore;
  }

  public abstract void setViewModel(@Nullable RoomViewModel viewModel);

  @Nullable
  public RoomViewModel getViewModel() {
    return mViewModel;
  }

  @NonNull
  public static ActivityRoomBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityRoomBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityRoomBinding>inflate(inflater, vn.edu.hcmut.linexo.R.layout.activity_room, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityRoomBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityRoomBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityRoomBinding>inflate(inflater, vn.edu.hcmut.linexo.R.layout.activity_room, null, false, component);
  }

  public static ActivityRoomBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityRoomBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityRoomBinding)bind(component, view, vn.edu.hcmut.linexo.R.layout.activity_room);
  }
}
