package com.example.myapplication.ui.post;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Post;
import com.yandex.metrica.YandexMetrica;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private static final String TWITTER_RESPONSE_FORMAT="EEE MMM dd HH:mm:ss ZZZZZ yyyy"; // Thu Oct 26 07:31:08 +0000 2017
    private static final String MONTH_DAY_FORMAT = "MMM dd"; // Oct 26

    private List<Post> tweetList = new ArrayList<>();

    private OnCommentClickListener onCommentClickListener;
    private OnLikeClickListener onLikeClickListener;

    public PostAdapter(OnCommentClickListener onCommentClickListener, OnLikeClickListener onLikeClickListener) {
        this.onCommentClickListener = onCommentClickListener;
        this.onLikeClickListener = onLikeClickListener;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
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

    public interface OnCommentClickListener {
        void onCommentClick(Post post);
    }
    public interface OnLikeClickListener {
        void onLikeClick(Post post);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView nickTextView;
        private TextView creationDateTextView;
        private TextView contentTextView;
        private TextView commentsTextView;
        private TextView likesTextView;
        private ImageButton commentButton;
        private ImageButton likeButton;

        public PostViewHolder(View itemView) {
            super(itemView);
            nickTextView = itemView.findViewById(R.id.author_nick_text_view);
            creationDateTextView = itemView.findViewById(R.id.creation_date_text_view);
            contentTextView = itemView.findViewById(R.id.tweet_content_text_view);
            commentsTextView = itemView.findViewById(R.id.comments_text_view);
            likesTextView = itemView.findViewById(R.id.likes_text_view);
            commentButton = itemView.findViewById(R.id.commentButton);
            likeButton = itemView.findViewById(R.id.likeButton);

            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Post post = tweetList.get(getLayoutPosition());
                    onCommentClickListener.onCommentClick(post);

                }
            });
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Post post = tweetList.get(getLayoutPosition());
                    onLikeClickListener.onLikeClick(post);
                    String eventParameters = "{\"postID\":\""+Long.toString(post.getId())+"\"}";
                    YandexMetrica.reportEvent("New like", eventParameters);
                }
            });
        }

        public void bind(Post tweet) {

            nickTextView.setText(tweet.getUser().getNick());
            contentTextView.setText(tweet.getText());
            commentsTextView.setText(String.valueOf(tweet.getСommentCount()));
            likesTextView.setText(String.valueOf(tweet.getFavouriteCount()));

            String creationDateFormatted = getFormattedDate(tweet.getCreationDate());
            creationDateTextView.setText(creationDateFormatted);
        }

        private String getFormattedDate(String rawDate) {
            SimpleDateFormat utcFormat = new SimpleDateFormat(TWITTER_RESPONSE_FORMAT, Locale.ENGLISH);
            SimpleDateFormat displayedFormat = new SimpleDateFormat(MONTH_DAY_FORMAT, Locale.getDefault());
            try {
                Date date = utcFormat.parse(rawDate);
                return displayedFormat.format(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
