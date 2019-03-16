package vn.edu.hcmut.linexo.presentation.custom;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import vn.edu.hcmut.linexo.presentation.model.RoomItem;

/**
 * Created by Anh on 3/16/2019.
 */

public class RoomDiffCallBack extends DiffUtil.Callback {
    List<RoomItem> oldRoomList;
    List<RoomItem> newRoomList;

    public RoomDiffCallBack(List<RoomItem> oldRoomList, List<RoomItem> newRoomList) {
        this.oldRoomList = oldRoomList;
        this.newRoomList = newRoomList;
    }

    @Override
    public int getOldListSize() {
        return oldRoomList.size();
    }

    @Override
    public int getNewListSize() {
        return newRoomList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRoomList.get(oldItemPosition).getId() == newRoomList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        RoomItem oldRoom = oldRoomList.get(oldItemPosition);
        RoomItem newRoom = newRoomList.get(newItemPosition);
        return (oldRoom.getId() == newRoom.getId()) && (oldRoom.getUrl_host().equals(newRoom.getUrl_host()))
                && (oldRoom.getUrl_opponent()==newRoom.getUrl_opponent()) && (oldRoom.isPrivate() == newRoom.isPrivate());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
