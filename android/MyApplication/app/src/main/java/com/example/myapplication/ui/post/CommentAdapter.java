package com.example.myapplication.ui.post;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Comment;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList = new ArrayList<>();

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comment, parent, false);
        return new CommentAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.CommentViewHolder holder, int position) {
        holder.bind(commentList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public void setItems(Collection<Comment> comments) {
        commentList.addAll(comments);
        notifyDataSetChanged();
    }

    public void setItem(Comment comment) {
        commentList.add(comment);
        notifyDataSetChanged();
    }

    public void clearItems() {
        commentList.clear();
        notifyDataSetChanged();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView nickTextView;
        private TextView contentTextView;

        public CommentViewHolder(View itemView) {
            super(itemView);
            nickTextView = itemView.findViewById(R.id.author_nick_text_view);
            contentTextView = itemView.findViewById(R.id.tweet_content_text_view);
        }

        public void bind(Comment tweet) {
            nickTextView.setText(tweet.getUser().getNick());
            contentTextView.setText(tweet.getText());

        }
    }
}