package vn.edu.hcmut.linexo.presentation.view.room;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.presentation.custom.CircleImageView;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModelCallback;

/**
 * Created by Anh on 3/13/2019.
 */

public class RankRecyclerViewAdapter extends RecyclerView.Adapter<RankRecyclerViewAdapter.RankRecyclerViewHolder>{

    private List<RankItem> rankItems = new ArrayList<>();
    ViewModelCallback viewModelCallback;

    // TODO: use Callback, not a viewModel
    public RankRecyclerViewAdapter(List<RankItem> rankItems, ViewModelCallback viewModelCallback) {
        this.rankItems = rankItems;
        this.viewModelCallback = viewModelCallback;
    }

    public RankRecyclerViewAdapter(List<RankItem> rankItems) {
        this.rankItems = rankItems;
    }

    @Override
    public RankRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_rank_item, parent, false);
        return new RankRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankRecyclerViewHolder holder, int position) {
        holder.txtUserName.setText(rankItems.get(position).getUserName());
        holder.txtUserScore.setText(rankItems.get(position).getUserScore());
        holder.avatar.setImageFromObject(rankItems.get(position).getAvatarURL());
    }

    @Override
    public int getItemCount() {
        return rankItems.size();
    }

    public class RankRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName;
        TextView txtUserScore;
        CircleImageView avatar;

        public RankRecyclerViewHolder(View itemRankView) {
            super(itemRankView);
            this.txtUserScore = itemRankView.findViewById(R.id.txt_userscore);
            this.txtUserName = itemRankView.findViewById(R.id.txt_username);
            this.avatar = itemRankView.findViewById(R.id.avatar);
        }
    }

    public void updateRankListItems(List<RankItem> rankItems) {
        final RankDiffCallBack diffCallback = new RankDiffCallBack(this.rankItems, rankItems);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.rankItems.clear();
        this.rankItems.addAll(rankItems);
        diffResult.dispatchUpdatesTo(this);
    }
}