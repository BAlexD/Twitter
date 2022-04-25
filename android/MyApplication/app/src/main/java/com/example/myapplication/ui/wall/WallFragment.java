package com.example.myapplication.ui.wall;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentWallBinding;
import com.example.myapplication.entities.Post;
import com.example.myapplication.network.TestData;
import com.example.myapplication.ui.post.CommentListFragment;
import com.example.myapplication.ui.post.PostAdapter;
import com.example.myapplication.ui.user_info.UserInfoFragment;

import java.util.Collection;

public class WallFragment extends Fragment {

    private FragmentWallBinding binding;
    private PostAdapter postAdapter;
    private RecyclerView postsRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WallViewModel dashboardViewModel =
                new ViewModelProvider(this).get(WallViewModel.class);

        binding = FragmentWallBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        postsRecyclerView = binding.wallFragmentPost;
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        postsRecyclerView.setAdapter(getPostAdapter());

        if (postAdapter.getItemCount() == 0){
            final TextView textView = binding.textDashboard;
            dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        }
        return root;
    }


    public PostAdapter getPostAdapter() {
        PostAdapter.OnCommentClickListener onCommentClickListener = new PostAdapter.OnCommentClickListener() {
            @Override
            public void onCommentClick(Post post) {
                Toast.makeText(WallFragment.this.getActivity(), "user " + post.getUser().getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WallFragment.this.getActivity(), CommentListFragment.class);
                intent.putExtra(CommentListFragment.POST_ID, post.getId());
                startActivity(intent);
            }
        };
        PostAdapter.OnLikeClickListener onLikeClickListener = new PostAdapter.OnLikeClickListener() {
            @Override
            public void onLikeClick(Post post) {
                Toast.makeText(WallFragment.this.getActivity(), "like " + post.getUser().getName(), Toast.LENGTH_SHORT).show();
            }
        };
        postAdapter = new PostAdapter(onCommentClickListener, onLikeClickListener);
        Collection<Post> posts = TestData.getTweets();
        postAdapter.setItems(posts);
        return postAdapter;
    }
}