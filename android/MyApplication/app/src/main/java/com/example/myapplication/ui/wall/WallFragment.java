package com.example.myapplication.ui.wall;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.MyApplication;
import com.example.myapplication.databinding.FragmentWallBinding;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;
import com.example.myapplication.requestSender.LikeRequestSender;
import com.example.myapplication.requestSender.PostRequestSender;
import com.example.myapplication.ui.post.CommentListFragment;
import com.example.myapplication.ui.post.PostAdapter;
import com.example.myapplication.ui.post.PostNoButtonsAdapter;
import com.example.myapplication.ui.post.PostPrivateAdapter;
import com.example.myapplication.ui.user_info.UserInfoFragment;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class WallFragment extends Fragment {

    private FragmentWallBinding binding;
    private PostAdapter postAdapter;
    private PostNoButtonsAdapter postNoButtonsAdapter;
    private RecyclerView postsRecyclerView;
    private  WallViewModel dashboardViewModel;
    private User currentUser = null;

    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(WallViewModel.class);

        binding = FragmentWallBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final MyApplication app = (MyApplication) WallFragment.this.getActivity().getApplicationContext();
        currentUser = app.getCurrentUser();

        swipeRefreshLayout = binding.swipeRefreshLayout;

        postsRecyclerView = binding.wallFragmentPost;
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        if (currentUser != null){
            postsRecyclerView.setAdapter(getPostAdapter());
        }else{
            postsRecyclerView.setAdapter(getPostNoButtonsAdapter());
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                if (currentUser != null){
                    postsRecyclerView.setAdapter(getPostAdapter());
                }else{
                    postsRecyclerView.setAdapter(getPostNoButtonsAdapter());
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        final MyApplication app = (MyApplication) WallFragment.this.getActivity().getApplicationContext();

        if(app.isUpdateWallFlag()){
            app.setUpdateFlag(false);
            postsRecyclerView.setAdapter(getPostAdapter());
        }
    }

    public PostNoButtonsAdapter getPostNoButtonsAdapter() {
        postNoButtonsAdapter = new PostNoButtonsAdapter();
        GetAllPosts getAllPosts = new GetAllPosts();
        getAllPosts.execute();
        return postNoButtonsAdapter;
    }

    public PostAdapter getPostAdapter() {
        PostAdapter.OnCommentClickListener onCommentClickListener = new PostAdapter.OnCommentClickListener() {
            @Override
            public void onCommentClick(Post post) {
                Toast.makeText(WallFragment.this.getActivity(), "user " + post.getUser().getNick(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WallFragment.this.getActivity(), CommentListFragment.class);
                intent.putExtra(CommentListFragment.POST_ID, post.getId());
                startActivity(intent);
            }
        };
        PostAdapter.OnLikeClickListener onLikeClickListener = new PostAdapter.OnLikeClickListener() {
            @Override
            public void onLikeClick(Post post) {
                Toast.makeText(WallFragment.this.getActivity(), "like " + post.getUser().getNick(), Toast.LENGTH_SHORT).show();
                UpdateLike updateLike = new UpdateLike();
                updateLike.execute(Long.toString(post.getId()), Long.toString(currentUser.getId()));
            }
        };
        PostAdapter.SetButtonImage setButtonLike = new PostAdapter.SetButtonImage() {
            @Override
            public boolean isLike(Post post) throws ExecutionException, InterruptedException {
                IsLike isLike = new IsLike();
                isLike.execute(Long.toString(post.getId()), Long.toString(currentUser.getId()));
                return isLike.get();
            }
        };
        postAdapter = new PostAdapter(onCommentClickListener, onLikeClickListener, setButtonLike);

        GetPostsSubscribers getPostsSubscribers = new GetPostsSubscribers();
        getPostsSubscribers.execute(Long.toString(currentUser.getId()));

        return postAdapter;
    }

    class GetAllPosts extends AsyncTask<Void, Void, Void> {
        Collection<Post> posts;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                PostRequestSender postRequestSender = new PostRequestSender();
                posts = postRequestSender.getAllPost();
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (posts.size() == 0){
                final TextView textView = binding.textDashboard;
                dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            }
            postNoButtonsAdapter.setItems(posts);
        }
    }

    class GetPostsSubscribers extends AsyncTask<String, Void, Void> {
        Collection<Post> posts;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(String... body) {
            try{
                PostRequestSender postRequestSender = new PostRequestSender();
                posts = postRequestSender.getSubscribersPosts(body[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (posts.size() == 0){
                final TextView textView = binding.textDashboard;
                dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            }
            postAdapter.clearItems();
            postAdapter.setItems(posts);
        }
    }

    class UpdateLike extends AsyncTask<String, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(String... body) {
            try{
                LikeRequestSender likeRequestSender = new LikeRequestSender();
                likeRequestSender.updateLike(body[0], body[1]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    class IsLike extends AsyncTask<String, Void, Boolean> {
        Boolean like;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Boolean doInBackground(String... body) {
            try{
                LikeRequestSender likeRequestSender = new LikeRequestSender();
                like = likeRequestSender.getLikesBuUser(body[0], body[1]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return like;
        }

        @Override
        protected void onPostExecute(Boolean isLike) {
            super.onPostExecute(isLike);
            //postPrivateAdapter.notifyDataSetChanged();
        }
    }
}