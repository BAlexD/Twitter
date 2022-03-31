package ru.vsu.twitter.service.post;

import org.springframework.stereotype.Service;
import ru.vsu.twitter.dto.post.PostCreateRequest;
import ru.vsu.twitter.dto.post.PostResponse;
import ru.vsu.twitter.dto.post.PostUpdateRequest;
import ru.vsu.twitter.entity.Post;
import ru.vsu.twitter.mapper.PostMapper;
import ru.vsu.twitter.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public List<PostResponse> geAllPosts() {
        return postRepository.findAll().stream().
                map(postMapper::postToPostResponse)
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long id) {
        Optional<Post> postById = postRepository.findById(id);
        if (postById.isEmpty()) {
            throw new RuntimeException("there is no post with such id");
        }
        return postMapper.postToPostResponse(postById.get());
    }

    public PostResponse savePost(PostCreateRequest postCreateRequest) {
        Post post = postMapper.PostCreateRequestToPost(postCreateRequest);
        return postMapper.postToPostResponse(postRepository.save(post));
    }

    public PostResponse updatePost(PostUpdateRequest postUpdateRequest) {
        if (!postRepository.existsById(postUpdateRequest.getId())) {
            throw new RuntimeException("there is no post with such id");
        }
        Post post = postMapper.updateRequestToPost(postUpdateRequest);
        return postMapper.postToPostResponse(postRepository.save(post));
    }

    public void deletePostById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("there is no post with such id");
        }
        postRepository.deleteById(id);
    }
}
