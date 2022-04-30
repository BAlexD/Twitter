package com.example.myapplication.ui.user_info;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AddPostActivity;
import com.example.myapplication.databinding.FragmentUserInfoBinding;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;
import com.example.myapplication.network.TestData;
import com.example.myapplication.ui.login.NewUserLoginFragment;
import com.example.myapplication.ui.post.CommentListFragment;
import com.example.myapplication.ui.post.PostAdapter;
import com.example.myapplication.ui.post.PostPrivateAdapter;
import com.yandex.metrica.YandexMetrica;

import java.util.Collection;


public class UserInfoFragment extends Fragment {

    private TextView nickTextView;
    private TextView descriptionTextView;
    private TextView followingCountTextView;
    private TextView followersCountTextView;
    private Button addPostButton;

    private RecyclerView postsRecyclerView;
    private PostPrivateAdapter postPrivateAdapter;
    private User user = TestData.getUser1();


    private FragmentUserInfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserInfoViewModel userViewModel =
                new ViewModelProvider(this).get(UserInfoViewModel.class);

        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nickTextView = binding.userNickTextView;
        userViewModel.getUserNickText().observe(getViewLifecycleOwner(), nickTextView::setText);

        descriptionTextView = binding.userDescriptionTextView;
        userViewModel.getUserDescriptionText().observe(getViewLifecycleOwner(), descriptionTextView::setText);

        followersCountTextView = binding.followersCountTextView;
        userViewModel.getUserFollowersText().observe(getViewLifecycleOwner(), followersCountTextView::setText);

        followingCountTextView = binding.followingCountTextView;
        userViewModel.getUserFollowingText().observe(getViewLifecycleOwner(), followingCountTextView::setText);

        postsRecyclerView = binding.fragmentPost;
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        postsRecyclerView.setAdapter(getPostAdapter());

        addPostButton = binding.addPostButton;
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserInfoFragment.this.getActivity(), "new post " + nickTextView.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserInfoFragment.this.getActivity(), AddPostActivity.class);
                intent.putExtra(AddPostActivity.USER_ID, user.getId());
                startActivity(intent);
            }
        });
        return root;
    }

    public PostPrivateAdapter getPostAdapter() {
        PostPrivateAdapter.OnCommentClickListener onCommentClickListener = new PostPrivateAdapter.OnCommentClickListener() {
            @Override
            public void onCommentClick(Post post) {
                Toast.makeText(UserInfoFragment.this.getActivity(), "user " + post.getUser().getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserInfoFragment.this.getActivity(), CommentListFragment.class);
                intent.putExtra(CommentListFragment.POST_ID, post.getId());
                startActivity(intent);
            }
        };
        PostPrivateAdapter.OnLikeClickListener onLikeClickListener = new PostPrivateAdapter.OnLikeClickListener() {
            @Override
            public void onLikeClick(Post post) {
                Toast.makeText(UserInfoFragment.this.getActivity(), "like " + post.getUser().getName(), Toast.LENGTH_SHORT).show();
            }
        };
        PostPrivateAdapter.OnDeleteClickListener onDeleteClickListener = new PostPrivateAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Post post) {
                Toast.makeText(UserInfoFragment.this.getActivity(), "delete " + post.getUser().getName(), Toast.LENGTH_SHORT).show();
            }
        };
        postPrivateAdapter = new PostPrivateAdapter(onCommentClickListener, onLikeClickListener, onDeleteClickListener);
        Collection<Post> posts = TestData.getTweets();
        postPrivateAdapter.setItems(posts);
        return postPrivateAdapter;
    }
}