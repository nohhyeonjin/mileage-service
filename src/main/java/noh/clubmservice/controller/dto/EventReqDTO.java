package noh.clubmservice.controller.dto;

import lombok.Getter;
import noh.clubmservice.controller.Action;

import java.util.List;
import java.util.UUID;

@Getter
public class EventReqDTO {

    private String type;
    private Action action;
    private UUID reviewId;
    private String content;
    private List<UUID> attachedPhotoIds;
    private UUID userId;
    private UUID placeId;

}
