package com.example.myapplication.ui.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PostNoButtonsAdapter extends RecyclerView.Adapter<PostNoButtonsAdapter.PostNoButtonsViewHolder> {
    private List<Post> tweetList = new ArrayList<>();

    @Override
    public PostNoButtonsAdapter.PostNoButtonsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post_no_buttons, parent, false);
        return new PostNoButtonsAdapter.PostNoButtonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostNoButtonsAdapter.PostNoButtonsViewHolder holder, int position) {
        holder.bind(tweetList.get(position));
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    public void setItems(Collection<Post> posts) {
        tweetList.addAll(posts);
        notifyDataSetChanged();
    }

    public void clearItems() {
        tweetList.clear();
        notifyDataSetChanged();
    }
    class PostNoButtonsViewHolder extends RecyclerView.ViewHolder {
        private TextView nickTextView;
        private TextView contentTextView;

        public PostNoButtonsViewHolder(View itemView) {
            super(itemView);
            nickTextView = itemView.findViewById(R.id.author_nick_text_view_no_buttons);
            contentTextView = itemView.findViewById(R.id.tweet_content_text_view_no_buttons);
        }

        public void bind(Post tweet) {
            nickTextView.setText(tweet.getUser().getNick());
            contentTextView.setText(tweet.getText());
        }

    }
}
