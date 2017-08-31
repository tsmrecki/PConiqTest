package com.smrecki.payconiqtest.repositories;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smrecki.payconiqtest.R;
import com.smrecki.payconiqtest.model.GitRepo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tomislav on 30/08/2017.
 */

class GitRepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_REPO = 1;
    private static final int VIEW_TYPE_LOADER = 2;

    private List<GitRepo> mGitRepoList = new ArrayList<>();
    private boolean showLoader = true;
    private OnGitRepoInteractionListener mOnGitRepoInteractionListener;

    void setGitRepoList(List<GitRepo> gitRepoList) {
        mGitRepoList.clear();
        mGitRepoList.addAll(gitRepoList);
        notifyDataSetChanged();
    }

    void addGitRepoList(List<GitRepo> gitRepoList) {
        int index = mGitRepoList.size();
        mGitRepoList.addAll(gitRepoList);
        notifyItemRangeInserted(index, gitRepoList.size());
    }

    void showLoader(boolean show) {
        boolean oldShowLoader = showLoader;
        showLoader = show;

        if (!oldShowLoader && showLoader) {
            notifyItemInserted(getItemCount());
        } else if (oldShowLoader && !showLoader) {
            notifyItemRemoved(getItemCount());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_REPO) {
            final RepoViewHolder repoHolder = new RepoViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.repository, parent, false));
            repoHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnGitRepoInteractionListener != null) {
                        mOnGitRepoInteractionListener.onGitRepoClicked(mGitRepoList.get(repoHolder.getAdapterPosition()), v);
                    }
                }
            });
            return repoHolder;
        } else {
            return new LoaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loader, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RepoViewHolder) {
            GitRepo gitRepo = mGitRepoList.get(position);

            RepoViewHolder holder = (RepoViewHolder) viewHolder;
            holder.title.setText(gitRepo.getName());
            holder.description.setText(gitRepo.getDescription());

        }
    }

    @Override
    public int getItemCount() {
        return mGitRepoList.size() + (showLoader ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (showLoader && position == mGitRepoList.size()) {
            return VIEW_TYPE_LOADER;
        } else {
            return VIEW_TYPE_REPO;
        }
    }

    public void setOnGitRepoInteractionListener(
            OnGitRepoInteractionListener onGitRepoInteractionListener) {
        mOnGitRepoInteractionListener = onGitRepoInteractionListener;
    }


    public interface OnGitRepoInteractionListener {
        void onGitRepoClicked(GitRepo gitRepo, View view);
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;

        private RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class LoaderViewHolder extends RecyclerView.ViewHolder {

        private LoaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
