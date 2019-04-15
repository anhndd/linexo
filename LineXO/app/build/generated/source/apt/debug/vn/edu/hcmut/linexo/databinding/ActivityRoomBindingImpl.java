package vn.edu.hcmut.linexo.databinding;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityRoomBindingImpl extends ActivityRoomBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.action_bar, 9);
        sViewsWithIds.put(R.id.advertisement, 10);
    }
    // views
    // variables
    // values
    // listeners
    private OnClickListenerImpl mViewModelOnClickAvatarAndroidViewViewOnClickListener;
    private OnClickListenerImpl1 mViewModelOnClickCreateRoomAndroidViewViewOnClickListener;
    // Inverse Binding Event Handlers
    private android.databinding.InverseBindingListener edtSearchandroidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.strSearch
            //         is viewModel.setStrSearch((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(edtSearch);
            // localize variables for thread safety
            // viewModel
            vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.strSearch
            java.lang.String viewModelStrSearch = null;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {




                viewModel.setStrSearch(((java.lang.String) (callbackArg_0)));
            }
        }
    };

    public ActivityRoomBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private ActivityRoomBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.view.View) bindings[9]
            , (android.view.View) bindings[10]
            , (vn.edu.hcmut.linexo.presentation.custom.CircleImageView) bindings[1]
            , (android.widget.Button) bindings[8]
            , (android.widget.EditText) bindings[6]
            , (android.support.v7.widget.RecyclerView) bindings[7]
            , (vn.edu.hcmut.linexo.presentation.view.room.RoomLayout) bindings[0]
            , (android.view.View) bindings[4]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[3]
            );
        this.avatar.setTag(null);
        this.btnCreateRoom.setTag(null);
        this.edtSearch.setTag(null);
        this.lstRoom.setTag(null);
        this.root.setTag(null);
        this.scoreIcon.setTag(null);
        this.txtName.setTag(null);
        this.txtNetwork.setTag(null);
        this.txtScore.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x100L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.viewModel == variableId) {
            setViewModel((vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel ViewModel) {
        updateRegistration(0, ViewModel);
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModel((vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModel(vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel ViewModel, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.urlAvatar) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        else if (fieldId == BR.userName) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        else if (fieldId == BR.score) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.userVisibility) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.networkVisibility) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.strSearch) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        else if (fieldId == BR.adapter) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        android.view.View.OnClickListener viewModelOnClickAvatarAndroidViewViewOnClickListener = null;
        java.lang.String viewModelScore = null;
        java.lang.String viewModelUserName = null;
        int viewModelUserVisibility = 0;
        vn.edu.hcmut.linexo.presentation.view.room.RoomRecyclerViewAdapter viewModelAdapter = null;
        int viewModelNetworkVisibility = 0;
        java.lang.String viewModelStrSearch = null;
        java.lang.Object viewModelUrlAvatar = null;
        android.view.View.OnClickListener viewModelOnClickCreateRoomAndroidViewViewOnClickListener = null;
        vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x1ffL) != 0) {


            if ((dirtyFlags & 0x101L) != 0) {

                    if (viewModel != null) {
                        // read viewModel::onClickAvatar
                        viewModelOnClickAvatarAndroidViewViewOnClickListener = (((mViewModelOnClickAvatarAndroidViewViewOnClickListener == null) ? (mViewModelOnClickAvatarAndroidViewViewOnClickListener = new OnClickListenerImpl()) : mViewModelOnClickAvatarAndroidViewViewOnClickListener).setValue(viewModel));
                        // read viewModel::onClickCreateRoom
                        viewModelOnClickCreateRoomAndroidViewViewOnClickListener = (((mViewModelOnClickCreateRoomAndroidViewViewOnClickListener == null) ? (mViewModelOnClickCreateRoomAndroidViewViewOnClickListener = new OnClickListenerImpl1()) : mViewModelOnClickCreateRoomAndroidViewViewOnClickListener).setValue(viewModel));
                    }
            }
            if ((dirtyFlags & 0x109L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.score
                        viewModelScore = viewModel.getScore();
                    }
            }
            if ((dirtyFlags & 0x105L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.userName
                        viewModelUserName = viewModel.getUserName();
                    }
            }
            if ((dirtyFlags & 0x111L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.userVisibility
                        viewModelUserVisibility = viewModel.getUserVisibility();
                    }
            }
            if ((dirtyFlags & 0x181L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.adapter
                        viewModelAdapter = viewModel.getAdapter();
                    }
            }
            if ((dirtyFlags & 0x121L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.networkVisibility
                        viewModelNetworkVisibility = viewModel.getNetworkVisibility();
                    }
            }
            if ((dirtyFlags & 0x141L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.strSearch
                        viewModelStrSearch = viewModel.getStrSearch();
                    }
            }
            if ((dirtyFlags & 0x103L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.urlAvatar
                        viewModelUrlAvatar = viewModel.getUrlAvatar();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x101L) != 0) {
            // api target 1

            this.avatar.setOnClickListener(viewModelOnClickAvatarAndroidViewViewOnClickListener);
            this.btnCreateRoom.setOnClickListener(viewModelOnClickCreateRoomAndroidViewViewOnClickListener);
        }
        if ((dirtyFlags & 0x103L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setImageFromObject(this.avatar, (java.lang.Object) viewModelUrlAvatar);
        }
        if ((dirtyFlags & 0x141L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.edtSearch, viewModelStrSearch);
        }
        if ((dirtyFlags & 0x100L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.edtSearch, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, edtSearchandroidTextAttrChanged);
        }
        if ((dirtyFlags & 0x181L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setAdapter(this.lstRoom, viewModelAdapter);
        }
        if ((dirtyFlags & 0x111L) != 0) {
            // api target 1

            this.scoreIcon.setVisibility(viewModelUserVisibility);
            this.txtScore.setVisibility(viewModelUserVisibility);
        }
        if ((dirtyFlags & 0x105L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.txtName, viewModelUserName);
        }
        if ((dirtyFlags & 0x121L) != 0) {
            // api target 1

            this.txtNetwork.setVisibility(viewModelNetworkVisibility);
        }
        if ((dirtyFlags & 0x109L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.txtScore, viewModelScore);
        }
    }
    // Listener Stub Implementations
    public static class OnClickListenerImpl implements android.view.View.OnClickListener{
        private vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel value;
        public OnClickListenerImpl setValue(vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.onClickAvatar(arg0); 
        }
    }
    public static class OnClickListenerImpl1 implements android.view.View.OnClickListener{
        private vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel value;
        public OnClickListenerImpl1 setValue(vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.onClickCreateRoom(arg0); 
        }
    }
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): viewModel.urlAvatar
        flag 2 (0x3L): viewModel.userName
        flag 3 (0x4L): viewModel.score
        flag 4 (0x5L): viewModel.userVisibility
        flag 5 (0x6L): viewModel.networkVisibility
        flag 6 (0x7L): viewModel.strSearch
        flag 7 (0x8L): viewModel.adapter
        flag 8 (0x9L): null
    flag mapping end*/
    //end
}