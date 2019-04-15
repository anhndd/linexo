package vn.edu.hcmut.linexo.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import vn.edu.hcmut.linexo.presentation.view.splash.SplashLayout;
import vn.edu.hcmut.linexo.presentation.view_model.splash.SplashViewModel;

public abstract class ActivitySplashBinding extends ViewDataBinding {
  @NonNull
  public final View loading;

  @NonNull
  public final View logo;

  @NonNull
  public final SplashLayout root;

  @Bindable
  protected SplashViewModel mViewModel;

  protected ActivitySplashBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, View loading, View logo, SplashLayout root) {
    super(_bindingComponent, _root, _localFieldCount);
    this.loading = loading;
    this.logo = logo;
    this.root = root;
  }

  public abstract void setViewModel(@Nullable SplashViewModel viewModel);

  @Nullable
  public SplashViewModel getViewModel() {
    return mViewModel;
  }

  @NonNull
  public static ActivitySplashBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivitySplashBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivitySplashBinding>inflate(inflater, vn.edu.hcmut.linexo.R.layout.activity_splash, root, attachToRoot, component);
  }

  @NonNull
  public static ActivitySplashBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivitySplashBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivitySplashBinding>inflate(inflater, vn.edu.hcmut.linexo.R.layout.activity_splash, null, false, component);
  }

  public static ActivitySplashBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivitySplashBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivitySplashBinding)bind(component, view, vn.edu.hcmut.linexo.R.layout.activity_splash);
  }
}
