package com.example.myapplication.ui.post;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.entities.Comment;
import com.example.myapplication.network.TestData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class CommentListViewModel extends ViewModel {
    private static final String TWITTER_RESPONSE_FORMAT="EEE MMM dd HH:mm:ss ZZZZZ yyyy"; // Thu Oct 26 07:31:08 +0000 2017
    private static final String MONTH_DAY_FORMAT = "MMM d"; // Oct 26

    //private ImageView userImageView;
    private final MutableLiveData<String> nameText;
    private final MutableLiveData<String> nickText;
    private final MutableLiveData<String> contentText;
    private final MutableLiveData<String> creationDateTextView;
    private final MutableLiveData<CommentAdapter> commentAdapterData;
    private CommentAdapter сommentAdapter;
    private Collection<Comment> comments;

    public CommentListViewModel() {
        nameText = new MutableLiveData<>();
        nickText = new MutableLiveData<>();
        contentText = new MutableLiveData<>();
        creationDateTextView = new MutableLiveData<>();
        commentAdapterData = new MutableLiveData<>();
    }

    public void setData(final Long post_id){
        loadPostInfo(post_id);
        loadComments(post_id);
    }
    public MutableLiveData<String> getNameText() {
        return nameText;
    }

    public MutableLiveData<String> getNickText() {
        return nickText;
    }

    public MutableLiveData<String> getContentText() {
        return contentText;
    }

    public MutableLiveData<String> getCreationDateTextView() {
        return creationDateTextView;
    }

    public MutableLiveData<CommentAdapter> _getCommentAdapterData() {
        сommentAdapter = new CommentAdapter();
        сommentAdapter.setItems(comments);
        commentAdapterData.setValue(сommentAdapter);
        return commentAdapterData;
    }
    public CommentAdapter getCommentAdapter() {
        сommentAdapter = new CommentAdapter();
        сommentAdapter.setItems(comments);
        return сommentAdapter;
    }
    private void loadPostInfo(final Long post_id){
        nameText.setValue(TestData.getUser().getName());
        nickText.setValue(TestData.getUser().getNick());
        contentText.setValue("content");
        String creationDateFormatted = getFormattedDate("Thu Dec 13 07:31:08 +0000 2017");
        creationDateTextView.setValue(creationDateFormatted);
        creationDateTextView.setValue(creationDateFormatted);
    }
    private void loadComments(final Long post_id){
        comments = TestData.getComments();
    }


    private String getFormattedDate(String rawDate) {
        SimpleDateFormat utcFormat = new SimpleDateFormat(TWITTER_RESPONSE_FORMAT, Locale.ROOT);
        SimpleDateFormat displayedFormat = new SimpleDateFormat(MONTH_DAY_FORMAT, Locale.getDefault());
        try {
            Date date = utcFormat.parse(rawDate);
            return displayedFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
