package ru.vsu.twitter.mapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.vsu.twitter.dto.subscribe.SubscribeCreateRequest;
import ru.vsu.twitter.dto.subscribe.SubscribeResponse;
import ru.vsu.twitter.dto.subscribe.SubscribeUpdateRequest;
import ru.vsu.twitter.entity.Subscribe;

@Mapper
@Component
public interface SubscribeMapper {
    Subscribe SubscribeCreateRequestToSubscribe(SubscribeCreateRequest subscribeCreateRequest);

    SubscribeResponse subscribeToSubscribeResponse(Subscribe subscribe);

    Subscribe updateRequestToSubscribe(SubscribeUpdateRequest subscribeUpdateRequest);
}
