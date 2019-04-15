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
        sViewsWithIds.put(R.id.avatar_1, 5);
        sViewsWithIds.put(R.id.txt_score1, 6);
        sViewsWithIds.put(R.id.score_icon_1, 7);
        sViewsWithIds.put(R.id.txt_clock_1, 8);
        sViewsWithIds.put(R.id.txt_room, 9);
        sViewsWithIds.put(R.id.avatar_2, 10);
        sViewsWithIds.put(R.id.txt_score2, 11);
        sViewsWithIds.put(R.id.score_icon_2, 12);
        sViewsWithIds.put(R.id.txt_clock_2, 13);
        sViewsWithIds.put(R.id.btn_message, 14);
    }
    // views
    // variables
    // values
    // listeners
    private OnClickListenerImpl mViewModelOnClickSendAndroidViewViewOnClickListener;
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

    public ActivityPlayBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private ActivityPlayBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (vn.edu.hcmut.linexo.presentation.custom.CircleImageView) bindings[5]
            , (vn.edu.hcmut.linexo.presentation.custom.CircleImageView) bindings[10]
            , (vn.edu.hcmut.linexo.presentation.view.play.BoardView) bindings[2]
            , (android.widget.Button) bindings[14]
            , (android.widget.ImageButton) bindings[4]
            , (android.support.v7.widget.RecyclerView) bindings[3]
            , (vn.edu.hcmut.linexo.presentation.view.play.PlayLayout) bindings[0]
            , (android.view.View) bindings[7]
            , (android.view.View) bindings[12]
            , (vn.edu.hcmut.linexo.presentation.custom.ClockView) bindings[8]
            , (vn.edu.hcmut.linexo.presentation.custom.ClockView) bindings[13]
            , (android.widget.TextView) bindings[1]
            , (vn.edu.hcmut.linexo.presentation.custom.RoomNumberView) bindings[9]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[11]
            );
        this.board.setTag(null);
        this.btnSend.setTag(null);
        this.lstMessage.setTag(null);
        this.root.setTag(null);
        this.txtNetwork.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x20L;
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
        else if (fieldId == BR.networkVisibility) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        else if (fieldId == BR.board) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        else if (fieldId == BR.touch) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.adapter) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
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
        vn.edu.hcmut.linexo.presentation.model.Board viewModelBoard = null;
        int[] viewModelTouch = null;
        vn.edu.hcmut.linexo.presentation.view.play.ChatRecyclerViewAdapter viewModelAdapter = null;
        int viewModelNetworkVisibility = 0;
        android.view.View.OnClickListener viewModelOnClickSendAndroidViewViewOnClickListener = null;
        vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x3fL) != 0) {


            if ((dirtyFlags & 0x25L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.board
                        viewModelBoard = viewModel.getBoard();
                    }
            }
            if ((dirtyFlags & 0x29L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.touch
                        viewModelTouch = viewModel.getTouch();
                    }
            }
            if ((dirtyFlags & 0x31L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.adapter
                        viewModelAdapter = viewModel.getAdapter();
                    }
            }
            if ((dirtyFlags & 0x23L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.networkVisibility
                        viewModelNetworkVisibility = viewModel.getNetworkVisibility();
                    }
            }
            if ((dirtyFlags & 0x21L) != 0) {

                    if (viewModel != null) {
                        // read viewModel::onClickSend
                        viewModelOnClickSendAndroidViewViewOnClickListener = (((mViewModelOnClickSendAndroidViewViewOnClickListener == null) ? (mViewModelOnClickSendAndroidViewViewOnClickListener = new OnClickListenerImpl()) : mViewModelOnClickSendAndroidViewViewOnClickListener).setValue(viewModel));
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x25L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setBoard(this.board, viewModelBoard);
        }
        if ((dirtyFlags & 0x29L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setTouch(this.board, viewModelTouch);
        }
        if ((dirtyFlags & 0x20L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setTouchListener(this.board, boardtouchAttrChanged);
        }
        if ((dirtyFlags & 0x21L) != 0) {
            // api target 1

            this.btnSend.setOnClickListener(viewModelOnClickSendAndroidViewViewOnClickListener);
        }
        if ((dirtyFlags & 0x31L) != 0) {
            // api target 1

            vn.edu.hcmut.linexo.presentation.custom.CustomBindingAdapter.setAdapter(this.lstMessage, viewModelAdapter);
        }
        if ((dirtyFlags & 0x23L) != 0) {
            // api target 1

            this.txtNetwork.setVisibility(viewModelNetworkVisibility);
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
            this.value.onClickSend(arg0); 
        }
    }
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): viewModel.networkVisibility
        flag 2 (0x3L): viewModel.board
        flag 3 (0x4L): viewModel.touch
        flag 4 (0x5L): viewModel.adapter
        flag 5 (0x6L): null
    flag mapping end*/
    //end
}