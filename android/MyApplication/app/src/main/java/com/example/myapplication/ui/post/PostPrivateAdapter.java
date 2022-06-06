package com.example.myapplication.ui.post;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;

import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Post;
import com.example.myapplication.requestSender.PostRequestSender;
import com.yandex.metrica.YandexMetrica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class PostPrivateAdapter extends RecyclerView.Adapter<PostPrivateAdapter.PostViewHolder> {

    private List<Post> tweetList = new ArrayList<>();

    private OnCommentClickListener onCommentClickListener;
    private OnLikeClickListener onLikeClickListener;
    private OnDeleteClickListener onDeleteClickListener;
    private SetButtonImage setButtonImage;

    public PostPrivateAdapter(OnCommentClickListener onCommentClickListener,
                              OnLikeClickListener onLikeClickListener,
                              OnDeleteClickListener onDeleteClickListener, SetButtonImage setButtonImage) {
        this.onCommentClickListener = onCommentClickListener;
        this.onLikeClickListener = onLikeClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
        this.setButtonImage = setButtonImage;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post_private, parent, false);
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
    public interface OnDeleteClickListener {
        void onDeleteClick(Post post);
    }

    public interface SetButtonImage{
        boolean isLike(Post post) throws ExecutionException, InterruptedException;
    }
    class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView nickTextView;
        private TextView contentTextView;
        private TextView commentsTextView;
        private TextView likesTextView;
        private ImageButton commentButton;
        private ImageButton likeButton;
        private ImageButton deleteButton;

        private Boolean imageFlag;

        public PostViewHolder(View itemView) {
            super(itemView);
            nickTextView = itemView.findViewById(R.id.author_nick_text_view);
            contentTextView = itemView.findViewById(R.id.tweet_content_text_view);
            commentsTextView = itemView.findViewById(R.id.comments_text_view);
            likesTextView = itemView.findViewById(R.id.likes_text_view);
            commentButton = itemView.findViewById(R.id.commentButton);
            likeButton = itemView.findViewById(R.id.likeButton);
            deleteButton =  itemView.findViewById(R.id.delete_post_Button);



            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Post post = tweetList.get(getLayoutPosition());
                    onCommentClickListener.onCommentClick(post);

                }
            });
            likeButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    Post post = tweetList.get(getLayoutPosition());
                    onLikeClickListener.onLikeClick(post);
                    Post changedPost;
                    if (imageFlag){
                      changedPost = new Post(post.getId(), post.getUser(), post.getText(), post.getСommentCount(), post.getFavouriteCount()-1);
                    }else{
                        changedPost = new Post(post.getId(), post.getUser(), post.getText(), post.getСommentCount(), post.getFavouriteCount()+1);
                    }
                    tweetList.set(getLayoutPosition(), changedPost);
                    String eventParameters = "{\"postID\":\""+post.getId()+"\"}";
                    YandexMetrica.reportEvent("New like", eventParameters);
                    //textLikeFlag = true;
                    notifyItemChanged(getLayoutPosition());
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    Post post = tweetList.get(getLayoutPosition());
                    onDeleteClickListener.onDeleteClick(post);
                    tweetList.remove(getLayoutPosition());
                    notifyDataSetChanged();
                }
            });

        }

        public void bind(Post tweet) {
            nickTextView.setText(tweet.getUser().getNick());
            contentTextView.setText(tweet.getText());
            commentsTextView.setText(String.valueOf(tweet.getСommentCount()));
            try {
                changeButtonLike(setButtonImage.isLike(tweet));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            likesTextView.setText(String.valueOf(tweet.getFavouriteCount()));
        }

        public void changeButtonLike(Boolean like){
            if(like){
                likeButton.setImageResource(R.drawable.like21);
                imageFlag = true;
            } else{
                likeButton.setImageResource(R.drawable.like01);
                imageFlag = false;
            }
        }
    }

}
