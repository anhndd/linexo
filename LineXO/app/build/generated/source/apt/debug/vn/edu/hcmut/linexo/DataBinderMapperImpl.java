package vn.edu.hcmut.linexo;

import android.databinding.DataBinderMapper;
import android.databinding.DataBindingComponent;
import android.databinding.ViewDataBinding;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import vn.edu.hcmut.linexo.databinding.ActivityPlayBindingImpl;
import vn.edu.hcmut.linexo.databinding.ActivityRoomBindingImpl;
import vn.edu.hcmut.linexo.databinding.ActivitySplashBindingImpl;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYPLAY = 1;

  private static final int LAYOUT_ACTIVITYROOM = 2;

  private static final int LAYOUT_ACTIVITYSPLASH = 3;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(3);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.edu.hcmut.linexo.R.layout.activity_play, LAYOUT_ACTIVITYPLAY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.edu.hcmut.linexo.R.layout.activity_room, LAYOUT_ACTIVITYROOM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.edu.hcmut.linexo.R.layout.activity_splash, LAYOUT_ACTIVITYSPLASH);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYPLAY: {
          if ("layout/activity_play_0".equals(tag)) {
            return new ActivityPlayBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_play is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYROOM: {
          if ("layout/activity_room_0".equals(tag)) {
            return new ActivityRoomBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_room is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSPLASH: {
          if ("layout/activity_splash_0".equals(tag)) {
            return new ActivitySplashBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_splash is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new com.android.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(22);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "adapter");
      sKeys.put(2, "roomNumber");
      sKeys.put(3, "touch");
      sKeys.put(4, "arrayKeyboardChanged");
      sKeys.put(5, "userName");
      sKeys.put(6, "urlAvatar");
      sKeys.put(7, "countTimeHost");
      sKeys.put(8, "score");
      sKeys.put(9, "enableGuest");
      sKeys.put(10, "strSearch");
      sKeys.put(11, "userVisibility");
      sKeys.put(12, "playType");
      sKeys.put(13, "contentMessage");
      sKeys.put(14, "enableHost");
      sKeys.put(15, "networkVisibility");
      sKeys.put(16, "viewModel");
      sKeys.put(17, "avatar2");
      sKeys.put(18, "avatar1");
      sKeys.put(19, "countTimeGuest");
      sKeys.put(20, "board");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(3);

    static {
      sKeys.put("layout/activity_play_0", vn.edu.hcmut.linexo.R.layout.activity_play);
      sKeys.put("layout/activity_room_0", vn.edu.hcmut.linexo.R.layout.activity_room);
      sKeys.put("layout/activity_splash_0", vn.edu.hcmut.linexo.R.layout.activity_splash);
    }
  }
}
