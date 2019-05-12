package vn.edu.hcmut.linexo.databinding;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityPlayBindingImpl extends ActivityPlayBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.txt_score1, 12);
        sViewsWithIds.put(R.id.score_icon_1, 13);
        sViewsWithIds.put(R.id.txt_score2, 14);
        sViewsWithIds.put(R.id.score_icon_2, 15);
        sViewsWithIds.put(R.id.img_robot, 16);
    }
    // views
    // variables
    // values
    // listeners
    private OnClickListenerImpl mViewModelOnClickBtnMessageAndroidViewViewOnClickListener;
    private OnClickListenerImpl1 mViewModelOnClickSendAndroidViewViewOnClickListener;
    // Inverse Binding Event Handlers
    private android.databinding.InverseBindingListener boardtouchAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.touch
            //         is viewModel.setTouch((int[]) callbackArg_0)
            int[] callbackArg_0 = vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.getTouch(board);
            // localize variables for thread safety
            // viewModel.touch
            int[] viewModelTouch = null;
            // viewModel
            vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {




                viewModel.setTouch(((int[]) (callbackArg_0)));
            }
        }
    };
    private android.databinding.InverseBindingListener btnMessageandroidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.contentMessage
            //         is viewModel.setContentMessage((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(btnMessage);
            // localize variables for thread safety
            // viewModel.contentMessage
            java.lang.String viewModelContentMessage = null;
            // viewModel
            vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {




                viewModel.setContentMessage(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private android.databinding.InverseBindingListener edtMessageandroidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.contentMessage
            //         is viewModel.setContentMessage((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(edtMessage);
            // localize variables for thread safety
            // viewModel.contentMessage
            java.lang.String viewModelContentMessage = null;
            // viewModel
            vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {




                viewModel.setContentMessage(((java.lang.String) (callbackArg_0)));
            }
        }
    };

    public ActivityPlayBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private ActivityPlayBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (vn.edu.hcmut.linexo.presentation.custom.CircleImageView) bindings[2]
            , (vn.edu.hcmut.linexo.presentation.custom.CircleImageView) bindings[5]
            , (vn.edu.hcmut.linexo.presentation.view.play.BoardView) bindings[7]
            , (android.widget.Button) bindings[9]
            , (android.widget.ImageButton) bindings[10]
            , (android.widget.EditText) bindings[11]
            , (android.widget.ImageView) bindings[16]
            , (android.support.v7.widget.RecyclerView) bindings[8]
            , (vn.edu.hcmut.linexo.presentation.view.play.PlayLayout) bindings[0]
            , (android.view.View) bindings[13]
            , (android.view.View) bindings[15]
            , (vn.edu.hcmut.linexo.presentation.custom.ClockView) bindings[3]
            , (vn.edu.hcmut.linexo.presentation.custom.ClockView) bindings[6]
            , (android.widget.TextView) bindings[1]
            , (vn.edu.hcmut.linexo.presentation.custom.RoomNumberView) bindings[4]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[14]
            );
        this.avatar1.setTag(null);
        this.avatar2.setTag(null);
        this.board.setTag(null);
        this.btnMessage.setTag(null);
        this.btnSend.setTag(null);
        this.edtMessage.setTag(null);
        this.lstMessage.setTag(null);
        this.root.setTag(null);
        this.txtClock1.setTag(null);
        this.txtClock2.setTag(null);
        this.txtNetwork.setTag(null);
        this.txtRoom.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8000L;
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
            setViewModel((vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel ViewModel) {
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
                return onChangeViewModel((vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModel(vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel ViewModel, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.arrayKeyboardChanged) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        else if (fieldId == BR.playType) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        else if (fieldId == BR.networkVisibility) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.avatar1) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.enableHost) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.countTimeHost) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        else if (fieldId == BR.roomNumber) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        else if (fieldId == BR.avatar2) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
            }
            return true;
        }
        else if (fieldId == BR.enableGuest) {
            synchronized(this) {
                    mDirtyFlags |= 0x200L;
            }
            return true;
        }
        else if (fieldId == BR.countTimeGuest) {
            synchronized(this) {
                    mDirtyFlags |= 0x400L;
            }
            return true;
        }
        else if (fieldId == BR.board) {
            synchronized(this) {
                    mDirtyFlags |= 0x800L;
            }
            return true;
        }
        else if (fieldId == BR.touch) {
            synchronized(this) {
                    mDirtyFlags |= 0x1000L;
            }
            return true;
        }
        else if (fieldId == BR.adapter) {
            synchronized(this) {
                    mDirtyFlags |= 0x2000L;
            }
            return true;
        }
        else if (fieldId == BR.contentMessage) {
            synchronized(this) {
                    mDirtyFlags |= 0x4000L;
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
        int viewModelRoomNumber = 0;
        vn.edu.hcmut.linexo.presentation.model.Board viewModelBoard = null;
        int[] viewModelTouch = null;
        java.lang.String viewModelContentMessage = null;
        long viewModelCountTimeHost = 0L;
        boolean viewModelEnableHost = false;
        boolean viewModelEnableGuest = false;
        vn.edu.hcmut.linexo.presentation.view.play.ChatRecyclerViewAdapter viewModelAdapter = null;
        java.lang.Object viewModelAvatar1 = null;
        int viewModelNetworkVisibility = 0;
        int[] viewModelArrayKeyboardChanged = null;
        android.view.View.OnClickListener viewModelOnClickBtnMessageAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener viewModelOnClickSendAndroidViewViewOnClickListener = null;
        java.lang.Object viewModelAvatar2 = null;
        boolean viewModelPlayType = false;
        vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel viewModel = mViewModel;
        long viewModelCountTimeGuest = 0L;

        if ((dirtyFlags & 0xffffL) != 0) {


            if ((dirtyFlags & 0x8081L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.roomNumber
                        viewModelRoomNumber = viewModel.getRoomNumber();
                    }
            }
            if ((dirtyFlags & 0x8801L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.board
                        viewModelBoard = viewModel.getBoard();
                    }
            }
            if ((dirtyFlags & 0x9001L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.touch
                        viewModelTouch = viewModel.getTouch();
                    }
            }
            if ((dirtyFlags & 0xc001L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.contentMessage
                        viewModelContentMessage = viewModel.getContentMessage();
                    }
            }
            if ((dirtyFlags & 0x8041L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.countTimeHost
                        viewModelCountTimeHost = viewModel.getCountTimeHost();
                    }
            }
            if ((dirtyFlags & 0x8021L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.enableHost
                        viewModelEnableHost = viewModel.getEnableHost();
                    }
            }
            if ((dirtyFlags & 0x8201L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.enableGuest
                        viewModelEnableGuest = viewModel.getEnableGuest();
                    }
            }
            if ((dirtyFlags & 0xa001L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.adapter
                        viewModelAdapter = viewModel.getAdapter();
                    }
            }
            if ((dirtyFlags & 0x8011L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.avatar1
                        viewModelAvatar1 = viewModel.getAvatar1();
                    }
            }
            if ((dirtyFlags & 0x8009L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.networkVisibility
                        viewModelNetworkVisibility = viewModel.getNetworkVisibility();
                    }
            }
            if ((dirtyFlags & 0x8003L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.arrayKeyboardChanged
                        viewModelArrayKeyboardChanged = viewModel.getArrayKeyboardChanged();
                    }
            }
            if ((dirtyFlags & 0x8001L) != 0) {

                    if (viewModel != null) {
                        // read viewModel::onClickBtnMessage
                        viewModelOnClickBtnMessageAndroidViewViewOnClickListener = (((mViewModelOnClickBtnMessageAndroidViewViewOnClickListener == null) ? (mViewModelOnClickBtnMessageAndroidViewViewOnClickListener = new OnClickListenerImpl()) : mViewModelOnClickBtnMessageAndroidViewViewOnClickListener).setValue(viewModel));
                        // read viewModel::onClickSend
                        viewModelOnClickSendAndroidViewViewOnClickListener = (((mViewModelOnClickSendAndroidViewViewOnClickListener == null) ? (mViewModelOnClickSendAndroidViewViewOnClickListener = new OnClickListenerImpl1()) : mViewModelOnClickSendAndroidViewViewOnClickListener).setValue(viewModel));
                    }
            }
            if ((dirtyFlags & 0x8101L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.avatar2
                        viewModelAvatar2 = viewModel.getAvatar2();
                    }
            }
            if ((dirtyFlags & 0x8005L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.playType
                        viewModelPlayType = viewModel.getPlayType();
                    }
            }
            if ((dirtyFlags & 0x8401L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.countTimeGuest
                        viewModelCountTimeGuest = viewModel.getCountTimeGuest();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x8011L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setImageFromObject(this.avatar1, (java.lang.Object) viewModelAvatar1);
        }
        if ((dirtyFlags & 0x8101L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setImageFromObject(this.avatar2, (java.lang.Object) viewModelAvatar2);
        }
        if ((dirtyFlags & 0x8801L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setBoard(this.board, viewModelBoard);
        }
        if ((dirtyFlags & 0x9001L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setTouch(this.board, viewModelTouch);
        }
        if ((dirtyFlags & 0x8000L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setTouchListener(this.board, boardtouchAttrChanged);
            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.btnMessage, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, btnMessageandroidTextAttrChanged);
            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.edtMessage, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, edtMessageandroidTextAttrChanged);
        }
        if ((dirtyFlags & 0x8001L) != 0) {
            // api target 1

            this.btnMessage.setOnClickListener(viewModelOnClickBtnMessageAndroidViewViewOnClickListener);
            this.btnSend.setOnClickListener(viewModelOnClickSendAndroidViewViewOnClickListener);
        }
        if ((dirtyFlags & 0xc001L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.btnMessage, viewModelContentMessage);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.edtMessage, viewModelContentMessage);
        }
        if ((dirtyFlags & 0xa001L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setAdapter(this.lstMessage, viewModelAdapter);
        }
        if ((dirtyFlags & 0x8003L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setKeyboardChanged(this.root, viewModelArrayKeyboardChanged);
        }
        if ((dirtyFlags & 0x8005L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setPlayType(this.root, viewModelPlayType);
        }
        if ((dirtyFlags & 0x8021L) != 0) {
            // api target 1

            this.txtClock1.setEnabled(viewModelEnableHost);
        }
        if ((dirtyFlags & 0x8041L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setCountTime(this.txtClock1, viewModelCountTimeHost);
        }
        if ((dirtyFlags & 0x8201L) != 0) {
            // api target 1

            this.txtClock2.setEnabled(viewModelEnableGuest);
        }
        if ((dirtyFlags & 0x8401L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setCountTime(this.txtClock2, viewModelCountTimeGuest);
        }
        if ((dirtyFlags & 0x8009L) != 0) {
            // api target 1

            this.txtNetwork.setVisibility(viewModelNetworkVisibility);
        }
        if ((dirtyFlags & 0x8081L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setKeyboardChanged(this.txtRoom, viewModelRoomNumber);
        }
    }
    // Listener Stub Implementations
    public static class OnClickListenerImpl implements android.view.View.OnClickListener{
        private vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel value;
        public OnClickListenerImpl setValue(vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.onClickBtnMessage(arg0); 
        }
    }
    public static class OnClickListenerImpl1 implements android.view.View.OnClickListener{
        private vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel value;
        public OnClickListenerImpl1 setValue(vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.onClickSend(arg0); 
        }
    }
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): viewModel.arrayKeyboardChanged
        flag 2 (0x3L): viewModel.playType
        flag 3 (0x4L): viewModel.networkVisibility
        flag 4 (0x5L): viewModel.avatar1
        flag 5 (0x6L): viewModel.enableHost
        flag 6 (0x7L): viewModel.countTimeHost
        flag 7 (0x8L): viewModel.roomNumber
        flag 8 (0x9L): viewModel.avatar2
        flag 9 (0xaL): viewModel.enableGuest
        flag 10 (0xbL): viewModel.countTimeGuest
        flag 11 (0xcL): viewModel.board
        flag 12 (0xdL): viewModel.touch
        flag 13 (0xeL): viewModel.adapter
        flag 14 (0xfL): viewModel.contentMessage
        flag 15 (0x10L): null
    flag mapping end*/
    //end
}