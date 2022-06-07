package com.example.myapplication.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UserSearchNotSubscribedAdapter extends RecyclerView.Adapter<UserSearchNotSubscribedAdapter.UserSearchNotSubscribedViewHolder> {
    private List<User> userList = new ArrayList<>();

    public UserSearchNotSubscribedAdapter() { }

    @Override
    public UserSearchNotSubscribedAdapter.UserSearchNotSubscribedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_view_not_subscribed, parent, false);
        return new UserSearchNotSubscribedAdapter.UserSearchNotSubscribedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserSearchNotSubscribedAdapter.UserSearchNotSubscribedViewHolder holder, int position) {
        holder.bind(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setItems(Collection<User> users) {
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public void clearItems() {
        userList.clear();
        notifyDataSetChanged();
    }

    class UserSearchNotSubscribedViewHolder extends RecyclerView.ViewHolder {
        private TextView nickTextView;
        private TextView subscribesTextView;
        private TextView subscribersTextView;


        public UserSearchNotSubscribedViewHolder(View itemView) {
            super(itemView);
            nickTextView = itemView.findViewById(R.id.user_nick_text_view_search);
            subscribesTextView = itemView.findViewById(R.id.following_count_text_view_search);
            subscribersTextView = itemView.findViewById(R.id.followers_count_text_view_search);
        }

        public void bind(User user) {
            nickTextView.setText(user.getNick());
            subscribesTextView.setText(String.valueOf(user.getFollowingCount()));
            subscribersTextView.setText(String.valueOf(user.getFollowersCount()));
        }
    }
}
