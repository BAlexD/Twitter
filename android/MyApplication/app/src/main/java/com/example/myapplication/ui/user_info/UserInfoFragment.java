package com.example.myapplication.ui.user_info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.ui.post.AddPostActivity;
import com.example.myapplication.MyApplication;
import com.example.myapplication.databinding.FragmentUserInfoBinding;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;
import com.example.myapplication.network.TestData;
import com.example.myapplication.requestSender.LikeRequestSender;
import com.example.myapplication.requestSender.PostRequestSender;
import com.example.myapplication.requestSender.UserRequestSender;
import com.example.myapplication.ui.login.LoginActivity;
import com.example.myapplication.ui.post.CommentListFragment;
import com.example.myapplication.ui.post.PostPrivateAdapter;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutionException;


public class UserInfoFragment extends Fragment {

    private TextView nickTextView;
    private TextView followingCountTextView;
    private TextView followersCountTextView;
    private TextView followersLabel;
    private TextView followingLabel;
    private Button addPostButton;

    private TextView autorizationText;
    private Button autorizationButon;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView postsRecyclerView;
    private PostPrivateAdapter postPrivateAdapter;
    private User currentUser = TestData.getUser1();

    private UserInfoViewModel userViewModel;
    private FragmentUserInfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userViewModel =
                new ViewModelProvider(this).get(UserInfoViewModel.class);

        final MyApplication app = (MyApplication) UserInfoFragment.this.getActivity().getApplicationContext();
        currentUser = app.getCurrentUser();

        //updateCurrentUser();

        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        nickTextView = binding.userNickTextView;
        followersCountTextView = binding.followersCountTextView;
        followingCountTextView = binding.followingCountTextView;
        postsRecyclerView = binding.fragmentPost;
        addPostButton = binding.addPostButton;
        followersLabel = binding.followersTextView;
        followingLabel = binding.followingTextView;
        autorizationText = binding.text;
        autorizationButon = binding.authorization;

        swipeRefreshLayout = binding.swipeRefreshLayout;

        if (currentUser != null) {

            nickTextView.setVisibility(View.VISIBLE);
            followersCountTextView.setVisibility(View.VISIBLE);
            followingCountTextView.setVisibility(View.VISIBLE);
            followersLabel.setVisibility(View.VISIBLE);
            followingLabel.setVisibility(View.VISIBLE);
            addPostButton.setVisibility(View.VISIBLE);
            autorizationText.setVisibility(View.GONE);
            autorizationButon.setVisibility(View.GONE);

            userViewModel.setUser(currentUser);

            userViewModel.getUserNickText().observe(getViewLifecycleOwner(), nickTextView::setText);
            userViewModel.getUserFollowersText().observe(getViewLifecycleOwner(), followersCountTextView::setText);
            userViewModel.getUserFollowingText().observe(getViewLifecycleOwner(), followingCountTextView::setText);

            postsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            try {
                postsRecyclerView.setAdapter(getPostAdapter());
            } catch (IOException e) {
                e.printStackTrace();
            }

            addPostButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UserInfoFragment.this.getActivity(), "new post " + nickTextView.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserInfoFragment.this.getActivity(), AddPostActivity.class);
                    intent.putExtra(AddPostActivity.USER_ID, currentUser.getId());
                    startActivity(intent);
                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    try {
                        postsRecyclerView.setAdapter(getPostAdapter());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    updateCurrentUser();
                }
            });

        } else {
            nickTextView.setVisibility(View.GONE);
            followersCountTextView.setVisibility(View.GONE);
            followingCountTextView.setVisibility(View.GONE);
            followersLabel.setVisibility(View.GONE);
            followingLabel.setVisibility(View.GONE);
            addPostButton.setVisibility(View.GONE);
            autorizationText.setVisibility(View.VISIBLE);
            autorizationButon.setVisibility(View.VISIBLE);

            autorizationButon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserInfoFragment.this.getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        return root;
    }

    @SuppressLint("SetTextI18n")
    private void updateCurrentUser(){
        final MyApplication app = (MyApplication) UserInfoFragment.this.getActivity().getApplicationContext();
        if(currentUser != null){
            GetUser getUser = new GetUser();
            getUser.execute(Long.toString(currentUser.getId()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final MyApplication app = (MyApplication) UserInfoFragment.this.getActivity().getApplicationContext();
        if (app.isRecreateFlag())
        {
            app.setRecreateFlag(false);
            this.getActivity().recreate();
        }
        if(app.isUpdateFlag()){
            app.setUpdateFlag(false);
            updateCurrentUser();
            try {
                postsRecyclerView.setAdapter(getPostAdapter());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public PostPrivateAdapter getPostAdapter() throws IOException {
        PostPrivateAdapter.OnCommentClickListener onCommentClickListener = new PostPrivateAdapter.OnCommentClickListener() {
            @Override
            public void onCommentClick(Post post) {
                Toast.makeText(UserInfoFragment.this.getActivity(), "user " + post.getUser().getNick(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserInfoFragment.this.getActivity(), CommentListFragment.class);
                intent.putExtra(CommentListFragment.POST_ID, post.getId());
                startActivity(intent);
            }
        };
        PostPrivateAdapter.OnLikeClickListener onLikeClickListener = new PostPrivateAdapter.OnLikeClickListener() {
            @Override
            public void onLikeClick(Post post) {
                Toast.makeText(UserInfoFragment.this.getActivity(), "like " + post.getUser().getNick(), Toast.LENGTH_SHORT).show();
                UpdateLike updateLike = new UpdateLike();
                updateLike.execute(Long.toString(post.getId()), Long.toString(currentUser.getId()));
            }
        };
        PostPrivateAdapter.OnDeleteClickListener onDeleteClickListener = new PostPrivateAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Post post) {
                Toast.makeText(UserInfoFragment.this.getActivity(), "delete " + post.getUser().getNick(), Toast.LENGTH_SHORT).show();
                DeletePost deletePost = new DeletePost();
                deletePost.execute(Long.toString(post.getId()));
            }
        };
        PostPrivateAdapter.SetButtonImage setButtonLike = new PostPrivateAdapter.SetButtonImage() {
            @Override
            public boolean isLike(Post post) throws ExecutionException, InterruptedException {
                IsLike isLike = new IsLike();
                isLike.execute(Long.toString(post.getId()), Long.toString(currentUser.getId()));
                return isLike.get();
            }
        };
        postPrivateAdapter = new PostPrivateAdapter(onCommentClickListener, onLikeClickListener, onDeleteClickListener, setButtonLike);

        GetUserPosts getUserPosts = new GetUserPosts();
        getUserPosts.execute(Long.toString(currentUser.getId()));
       //postPrivateAdapter.setItems(posts);
        return postPrivateAdapter;
    }

    class GetUserPosts extends AsyncTask<String, Void, Void> {
        Collection<Post> posts;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(String... body) {
            try{
                PostRequestSender postRequestSender = new PostRequestSender();
                posts = postRequestSender.getUsersPosts(body[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (posts.size() == 0){
                final TextView textView = binding.textDashboardUserInfo;
                textView.setText("Добавьте первый пост!");
            }
            postPrivateAdapter.clearItems();
            postPrivateAdapter.setItems(posts);
        }
    }

    class GetUser extends AsyncTask<String, Void, Void> {
        User user = null;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(String... id) {
            try{
                UserRequestSender userRequestSender = new UserRequestSender();
                user = userRequestSender.getUser(id[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (user == null){
                Toast.makeText(UserInfoFragment.this.getActivity(), "Пользователь не найден", Toast.LENGTH_SHORT).show();
            }
            final MyApplication app = (MyApplication) UserInfoFragment.this.getActivity().getApplicationContext();
            app.setCurrentUser(user);
            currentUser = app.getCurrentUser();
            userViewModel.setUser(currentUser);
            followingCountTextView.setText(Long.toString(currentUser.getFollowingCount()));
            followersCountTextView.setText(Long.toString(currentUser.getFollowersCount()));

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

    class DeletePost extends AsyncTask<String, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(String... body) {
            try{
                PostRequestSender postRequestSender = new PostRequestSender();
                postRequestSender.deletePost(body[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //postPrivateAdapter.notifyDataSetChanged();
        }
    }

}