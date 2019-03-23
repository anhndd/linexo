package vn.edu.hcmut.linexo.presentation.view.room;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import vn.edu.hcmut.linexo.presentation.model.Room;

/**
 * Created by Anh on 3/16/2019.
 */

public class RoomDiffCallBack extends DiffUtil.Callback {
    List<Room> oldRoomList;
    List<Room> newRoomList;

    public RoomDiffCallBack(List<Room> oldRoomList, List<Room> newRoomList) {
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
        return oldRoomList.get(oldItemPosition).getId().equals(newRoomList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Room oldRoom = oldRoomList.get(oldItemPosition);
        Room newRoom = newRoomList.get(newItemPosition);

        return (oldRoom.getId().equals(newRoom.getId())) && (oldRoom.getUrl_host().equals(newRoom.getUrl_host()))
                && (equalString(oldRoom.getUrl_opponent(),newRoom.getUrl_opponent())) && (oldRoom.isPrivate() == newRoom.isPrivate());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return "LineXO";
    }

    private boolean equalString(String str1, String str2) {
        if (str2 == str1) {
            return true;
        } else if (str1 == null || str2 == null) {
            return false;
        } else {
            return str2.equals(str1);
        }
    }
}
