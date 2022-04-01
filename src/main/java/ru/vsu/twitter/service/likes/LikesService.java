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

    public LikesResponse updateLikes(LikesUpdateRequest likesUpdateRequest) {
        if (!likesRepository.existsById(likesUpdateRequest.getId())) {
            throw new RuntimeException("there is no user with such id");
        }
        Likes likes = likesMapper.updateRequestToLikes(likesUpdateRequest);
        return likesMapper.likesToLikesResponse(likesRepository.save(likes));
    }

    public void deleteLikesById(Long id) {
        if (!likesRepository.existsById(id)) {
            throw new RuntimeException("there is no user with such id");
        }
        likesRepository.deleteById(id);
    }
}
