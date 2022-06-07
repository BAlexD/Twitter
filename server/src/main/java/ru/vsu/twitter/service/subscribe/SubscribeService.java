package ru.vsu.twitter.service.subscribe;

import org.springframework.stereotype.Service;
import ru.vsu.twitter.dto.subscribe.SubscribeCreateRequest;
import ru.vsu.twitter.dto.subscribe.SubscribeResponse;
import ru.vsu.twitter.dto.subscribe.SubscribeUpdateRequest;
import ru.vsu.twitter.entity.Subscribe;
import ru.vsu.twitter.mapper.SubscribeMapper;
import ru.vsu.twitter.repository.SubscribeRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final SubscribeMapper subscribeMapper;

    public SubscribeService(SubscribeRepository subscribeRepository, SubscribeMapper subscribeMapper) {
        this.subscribeRepository = subscribeRepository;
        this.subscribeMapper = subscribeMapper;
    }

    public List<SubscribeResponse> geAllSubscribes() {
        return subscribeRepository.findAll().stream().
                map(subscribeMapper::subscribeToSubscribeResponse)
                .collect(Collectors.toList());
    }

    public SubscribeResponse getSubscribeById(Long id) {
        Optional<Subscribe> subscribeById = subscribeRepository.findById(id);
        if (subscribeById.isEmpty()) {
            throw new RuntimeException("there is no subscribe with such id");
        }
        return subscribeMapper.subscribeToSubscribeResponse(subscribeById.get());
    }

    public SubscribeResponse saveSubscribe(SubscribeCreateRequest subscribeCreateRequest) {
        Subscribe subscribe = subscribeMapper.SubscribeCreateRequestToSubscribe(subscribeCreateRequest);
        return subscribeMapper.subscribeToSubscribeResponse(subscribeRepository.save(subscribe));
    }

     public void updateSubscribe(SubscribeUpdateRequest subscribeUpdateRequest) {
        Optional<Subscribe> subscribe = subscribeRepository.findByProfileIdAndSubscriberId(subscribeUpdateRequest.getProfileId(), subscribeUpdateRequest.getSubscriberId());
        if (subscribe.isPresent()) {
            this.subscribeRepository.deleteById(((Subscribe)subscribe.get()).getId());
        } else {
            subscribeRepository.save(subscribeMapper.updateRequestToSubscribe(subscribeUpdateRequest));
        }

    }

    public Integer getSubscribersCountByProfileId(Long profileId) {
        UserResponse userById = userService.getUserById(profileId);
        return subscribeRepository.countSubscribeByProfileId(userById.getId());
    }

    public Integer getSubscribesCountByProfileId(Long profileId) {
        UserResponse userById = userService.getUserById(profileId);
        return subscribeRepository.countSubscribeBySubscriberId(userById.getId());
    }

    public List<UserResponse> getSubscribesByProfileId(Long profileId) {
        List<Subscribe> allByProfileId = subscribeRepository.getAllBySubscriberId(profileId);
        return userService.findAllByIds(allByProfileId.stream().map(Subscribe::getSubscriberId).collect(Collectors.toList()));
    }

    public List<UserResponse> getSubscribersByProfileId(Long profileId) {
        List<Subscribe> allByProfileId = subscribeRepository.getAllByProfileId(profileId);
        return userService.findAllByIds(allByProfileId.stream().map(Subscribe::getSubscriberId).collect(Collectors.toList()));
    }

    public Boolean isUserSubscribe(Long profileId, Long subscriberId) {
        return subscribeRepository.existsByProfileIdAndSubscriberId(profileId, subscriberId);
    }
}
