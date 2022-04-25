package com.example.myapplication.ui.user_info;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentUserInfoBinding;
import com.example.myapplication.entities.Post;
import com.example.myapplication.network.TestData;
import com.example.myapplication.ui.post.CommentListFragment;
import com.example.myapplication.ui.post.PostAdapter;

import java.util.Collection;


public class UserInfoFragment extends Fragment {

    private ImageView userImageView;
    private TextView nameTextView;
    private TextView nickTextView;
    private TextView descriptionTextView;
    private TextView locationTextView;
    private TextView followingCountTextView;
    private TextView followersCountTextView;

    private RecyclerView postsRecyclerView;
    private PostAdapter postAdapter;

    private FragmentUserInfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserInfoViewModel userViewModel =
                new ViewModelProvider(this).get(UserInfoViewModel.class);

        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nameTextView = binding.userNameTextView;
        userViewModel.getUserNameText().observe(getViewLifecycleOwner(), nameTextView::setText);
        nickTextView = binding.userNickTextView;
        userViewModel.getUserNickText().observe(getViewLifecycleOwner(), nickTextView::setText);
        descriptionTextView = binding.userDescriptionTextView;
        userViewModel.getUserDescriptionText().observe(getViewLifecycleOwner(), descriptionTextView::setText);
        locationTextView = binding.userLocationTextView;
        userViewModel.getUserLocationText().observe(getViewLifecycleOwner(), locationTextView::setText);
        followersCountTextView = binding.followersCountTextView;
        userViewModel.getUserFollowersText().observe(getViewLifecycleOwner(), followersCountTextView::setText);
        followingCountTextView = binding.followingCountTextView;
        userViewModel.getUserFollowingText().observe(getViewLifecycleOwner(), followingCountTextView::setText);
        postsRecyclerView = binding.fragmentPost;
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        postsRecyclerView.setAdapter(getPostAdapter());
        //userViewModel.getPostAdapter().observe(getViewLifecycleOwner(), postsRecyclerView::setAdapter);
        return root;
    }

    public PostAdapter getPostAdapter() {
        PostAdapter.OnCommentClickListener onCommentClickListener = new PostAdapter.OnCommentClickListener() {
            @Override
            public void onCommentClick(Post post) {
                Toast.makeText(UserInfoFragment.this.getActivity(), "user " + post.getUser().getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserInfoFragment.this.getActivity(), CommentListFragment.class);
                intent.putExtra(CommentListFragment.POST_ID, post.getId());
                startActivity(intent);
            }
        };
        PostAdapter.OnLikeClickListener onLikeClickListener = new PostAdapter.OnLikeClickListener() {
            @Override
            public void onLikeClick(Post post) {
                Toast.makeText(UserInfoFragment.this.getActivity(), "like " + post.getUser().getName(), Toast.LENGTH_SHORT).show();
            }
        };
        postAdapter = new PostAdapter(onCommentClickListener, onLikeClickListener);
        Collection<Post> posts = TestData.getTweets();
        postAdapter.setItems(posts);
        return postAdapter;
    }
}