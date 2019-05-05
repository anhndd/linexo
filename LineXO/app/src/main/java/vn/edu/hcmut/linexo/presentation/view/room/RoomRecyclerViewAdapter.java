package vn.edu.hcmut.linexo.presentation.view.room;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModelCallback;
import vn.edu.hcmut.linexo.utils.Event;

/**
 * Created by Anh on 3/13/2019.
 */

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.RoomRecyclerViewHolder>{

    private List<RoomItem> roomItems = new ArrayList<>();
    ViewModelCallback viewModelCallback;

    // TODO: use Callback, not a viewModel
    public RoomRecyclerViewAdapter(List<RoomItem> roomItems, ViewModelCallback viewModelCallback) {
        this.roomItems = roomItems;
        this.viewModelCallback = viewModelCallback;
    }

    @Override
    public RoomRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_room_item, parent, false);
        return new RoomRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomRecyclerViewHolder holder, int position) {
        holder.itemView.setRoomView(roomItems.get(position).getRoomNumber(), roomItems.get(position).getUrl_host(),
                roomItems.get(position).getUrl_opponent(), roomItems.get(position).isPrivate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    viewModelCallback.onHelp(Event.create(Event.CLICK_ROOM, roomItems.get(position).getId()));
                }
            }
        );
    }

    @Override
    public int getItemCount() {
        return roomItems.size();
    }

    public class RoomRecyclerViewHolder extends RecyclerView.ViewHolder {
        RoomViewLayout itemView;

        public RoomRecyclerViewHolder(View itemRoomView) {
            super(itemRoomView);
            this.itemView = (RoomViewLayout) itemRoomView;
        }
    }

    public void updateRoomListItems(List<RoomItem> roomItems) {
        final RoomDiffCallBack diffCallback = new RoomDiffCallBack(this.roomItems, roomItems);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.roomItems.clear();
        this.roomItems.addAll(roomItems);
        diffResult.dispatchUpdatesTo(this);
    }
}