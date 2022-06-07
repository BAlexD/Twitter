package ru.vsu.twitter.service.likes;

import org.springframework.stereotype.Service;
import ru.vsu.twitter.dto.likes.LikesCreateRequest;
import ru.vsu.twitter.dto.likes.LikesResponse;
import ru.vsu.twitter.dto.likes.LikesUpdateRequest;
import ru.vsu.twitter.entity.Likes;
import ru.vsu.twitter.mapper.LikesMapper;
import ru.vsu.twitter.repository.LikesRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final LikesMapper likesMapper;

    public LikesService(LikesRepository likesRepository, LikesMapper likesMapper) {
        this.likesRepository = likesRepository;
        this.likesMapper = likesMapper;
    }

    public List<LikesResponse> geAllLikes() {
        return likesRepository.findAll().stream().
                map(likesMapper::likesToLikesResponse)
                .collect(Collectors.toList());
    }

    public LikesResponse getLikesById(Long id) {
        Optional<Likes> likesById = likesRepository.findById(id);
        if (likesById.isEmpty()) {
            throw new RuntimeException("there is no user with such id");
        }
        return likesMapper.likesToLikesResponse(likesById.get());
    }

    public LikesResponse saveLikes(LikesCreateRequest likesCreateRequest) {
        Likes likes = likesMapper.likesCreateRequestToLikes(likesCreateRequest);
        return likesMapper.likesToLikesResponse(likesRepository.save(likes));
    }

    public void updateLike(LikesUpdateRequest likesUpdateRequest) {
        Optional<Likes> likeByUserIdAndPostId = likesRepository.findLikesByUserIdAndPostId(likesUpdateRequest.getUserId(), likesUpdateRequest.getPostId());
        if (likeByUserIdAndPostId.isPresent()) {
            this.likesRepository.deleteById((likeByUserIdAndPostId.get()).getId());
        } else {
            this.likesRepository.save(likesMapper.updateRequestToLikes(likesUpdateRequest));
        }
    }

    public void deleteLikesById(Long id) {
        if (!likesRepository.existsById(id)) {
            throw new RuntimeException("there is no user with such id");
        }
        likesRepository.deleteById(id);
    }
    
   public List<LikesResponse> getLikesByPostId(Long postId) {
        List<Likes> allLikesByPotId = this.likesRepository.getAllByPostId(postId);
        return allLikesByPotId.stream()
                .map(likesMapper::likesToLikesResponse);
    }

    public Integer getLikesCountByPostId(Long postId) {
        PostResponse postById = this.postService.getPostById(postId);
        return this.likesRepository.countLikeByPostId(postById.getId());
    }

    public Boolean isPostLiked(Long userId, Long PostId) {
        return this.likesRepository.existsLikeByUserIdAndPostId(userId, PostId);
    }
}
