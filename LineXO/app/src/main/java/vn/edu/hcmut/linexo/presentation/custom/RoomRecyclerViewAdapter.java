package vn.edu.hcmut.linexo.presentation.custom;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import vn.edu.hcmut.linexo.R;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmut.linexo.presentation.model.RoomItem;

/**
 * Created by Anh on 3/13/2019.
 */

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.RecyclerViewHolder> {

    private List<RoomItem> roomItems = new ArrayList<>();

    public RoomRecyclerViewAdapter(List<RoomItem> roomItems) {
        this.roomItems = roomItems;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_room_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.roomId.setText(roomItems.get(position).getId());
//         url gilde
        Glide.with(holder.itemView.getContext())
                .load(roomItems.get(position).getUrl_host())
                .into(holder.cvHost);
        Glide.with(holder.itemView.getContext())
                .load(roomItems.get(position).getUrl_opponent())
                .into(holder.cvOpponent);
        if (!roomItems.get(position).isPrivate())
            holder.lockRoomIcon.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return roomItems.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView roomId;
        CircleImageView cvOpponent;
        CircleImageView cvHost;
        View lockRoomIcon;

        public RecyclerViewHolder(View itemRoomView) {
            super(itemRoomView);
            roomId = (TextView) itemRoomView.findViewById(R.id.txt_room_id);
            cvOpponent = (CircleImageView) itemRoomView.findViewById(R.id.civ_opponent);
            cvHost = (CircleImageView) itemRoomView.findViewById(R.id.civ_host);
            lockRoomIcon = (View) itemRoomView.findViewById(R.id.lock_room_icon);
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
