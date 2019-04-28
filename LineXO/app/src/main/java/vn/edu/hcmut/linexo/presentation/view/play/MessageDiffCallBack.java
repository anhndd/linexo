package vn.edu.hcmut.linexo.presentation.view.play;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import vn.edu.hcmut.linexo.presentation.model.Message;

/**
 * Created by Anh on 3/16/2019.
 */

public class MessageDiffCallBack extends DiffUtil.Callback {
    List<Message> oldMessageList;
    List<Message> newMessageList;

    public MessageDiffCallBack(List<Message> oldMessageList, List<Message> newMessageList) {
        this.oldMessageList = oldMessageList;
        this.newMessageList = newMessageList;
    }

    @Override
    public int getOldListSize() {
        return oldMessageList.size();
    }

    @Override
    public int getNewListSize() {
        return newMessageList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMessageList.get(oldItemPosition).getTime().equals(newMessageList.get(newItemPosition).getTime());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Message oldMessage = oldMessageList.get(oldItemPosition);
        Message newMessage = newMessageList.get(newItemPosition);

        return (equalString(oldMessage.getId(),newMessage.getId())) && equalString(oldMessage.getAvatarURL(),newMessage.getAvatarURL())
                && (equalString(oldMessage.getName(), newMessage.getName())) && (equalString(oldMessage.getMessage(),newMessage.getMessage()));
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
