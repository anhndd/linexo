package vn.edu.hcmut.linexo.presentation.view.room;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import vn.edu.hcmut.linexo.presentation.model.User;

/**
 * Created by Anh on 3/16/2019.
 */

public class RankDiffCallBack extends DiffUtil.Callback {
    private List<RankItem> oldRankList;
    private List<RankItem> newRankList;

    public RankDiffCallBack(List<RankItem> oldRankList, List<RankItem> newRankList) {
        this.oldRankList = oldRankList;
        this.newRankList = newRankList;
    }

    @Override
    public int getOldListSize() {
        return oldRankList.size();
    }

    @Override
    public int getNewListSize() {
        return newRankList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRankList.get(oldItemPosition).getUid().equals(newRankList.get(newItemPosition).getUid());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        RankItem oldRankList = this.oldRankList.get(oldItemPosition);
        RankItem newRankList = this.newRankList.get(newItemPosition);

        return (equalString(oldRankList.getUserScore(),newRankList.getUserScore()))
                && equalString(oldRankList.getUserName(),newRankList.getUserName())
                && equalString(oldRankList.getAvatarURL(),newRankList.getAvatarURL());
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
