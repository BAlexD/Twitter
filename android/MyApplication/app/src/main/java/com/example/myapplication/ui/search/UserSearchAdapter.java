package com.example.myapplication.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.UserSearchViewHolder> {
    private List<User> userList = new ArrayList<>();

    private OnSubscribeClickListener onSubscribeClickListener;
    private SetButtonText setButtonText;

    public UserSearchAdapter(OnSubscribeClickListener onSubscribeClickListener, SetButtonText setButtonText) {
        this.onSubscribeClickListener = onSubscribeClickListener;
        this.setButtonText = setButtonText;
    }

    @Override
    public UserSearchAdapter.UserSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_view, parent, false);
        return new UserSearchAdapter.UserSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserSearchAdapter.UserSearchViewHolder holder, int position) {
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

    public interface OnSubscribeClickListener {
        void onSubscribeClick(User user);
    }
    public interface SetButtonText{
        boolean isSubscribe(User user) throws ExecutionException, InterruptedException;
    }

    class UserSearchViewHolder extends RecyclerView.ViewHolder {
        private TextView nickTextView;
        private TextView subscribesTextView;
        private TextView subscribersTextView;
        private Button subscribeButton;

        private Boolean subscribeFlag;


        public UserSearchViewHolder(View itemView) {
            super(itemView);
            nickTextView = itemView.findViewById(R.id.user_nick_text_view_search);
            subscribesTextView = itemView.findViewById(R.id.following_count_text_view_search);
            subscribersTextView = itemView.findViewById(R.id.followers_count_text_view_search);
            subscribeButton = itemView.findViewById(R.id.subscribe_button);


            subscribeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = userList.get(getLayoutPosition());
                    onSubscribeClickListener.onSubscribeClick(user);
                    notifyItemChanged(getLayoutPosition());
                    User changedUser;
                    if (subscribeFlag){
                        changedUser = new User(user.getId(), user.getNick(), user.getFollowingCount(), user.getFollowersCount()-1);
                    }else{
                        changedUser = new User(user.getId(), user.getNick(), user.getFollowingCount(), user.getFollowersCount()+1);
                    }
                    userList.set(getLayoutPosition(), changedUser);

                    String eventParameters = "{\"userID\":\""+user.getId()+"\"}";
                    YandexMetrica.reportEvent("New subscribe", eventParameters);
                    //textLikeFlag = true;
                    notifyItemChanged(getLayoutPosition());
                }
            });

        }

        //TODO проверить кнопку подписки
        public void bind(User user) {
            nickTextView.setText(user.getNick());
            subscribesTextView.setText(String.valueOf(user.getFollowingCount()));
            subscribersTextView.setText(String.valueOf(user.getFollowersCount()));
            try {
                changeButtonSubscribe(setButtonText.isSubscribe(user));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void changeButtonSubscribe(Boolean subscribe){
            if(subscribe){
                subscribeButton.setText("Отписаться");
                subscribeFlag = true;
            } else{
                subscribeButton.setText("Подписаться");
                subscribeFlag = false;
            }
        }
    }


}
