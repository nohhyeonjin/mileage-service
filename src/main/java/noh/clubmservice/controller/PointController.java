package noh.clubmservice.controller;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.controller.dto.EventReqDTO;
import noh.clubmservice.controller.dto.EventResDTO;
import noh.clubmservice.controller.dto.HistoryResDTO;
import noh.clubmservice.controller.dto.PointResDTO;
import noh.clubmservice.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointHistoryService pointHistoryService;
    private final UserPointService userPointService;
    private final PointService pointService;

    @PostMapping("/events")
    public EventResDTO events(@RequestBody EventReqDTO eventReqDTO) {
        Action action = eventReqDTO.getAction();

        int point = 0;
        if (action.equals(Action.ADD)) {
            point = pointService.add(eventReqDTO.getContent(), eventReqDTO.getAttachedPhotoIds(), eventReqDTO.getPlaceId(), eventReqDTO.getReviewId(), eventReqDTO.getUserId());
        } else if (action.equals(Action.MOD)) {
            point = pointService.modify(eventReqDTO.getContent(), eventReqDTO.getAttachedPhotoIds(), eventReqDTO.getReviewId(), eventReqDTO.getUserId());
        } else if (action.equals(Action.DELETE)) {
            point = pointService.delete(eventReqDTO.getReviewId(), eventReqDTO.getUserId());
        }

        return new EventResDTO(action, eventReqDTO.getReviewId(), point);
    }

    @GetMapping("/point/{id}")
    public PointResDTO point(@PathVariable("id") UUID userId) {
        return new PointResDTO(userPointService.findByUser(userId));
    }

    @GetMapping("/history/{id}")
    public HistoryResDTO history(@PathVariable("id") UUID userId) {
        return new HistoryResDTO(userId, pointHistoryService.getSortedUserHistory(userId));
    }

}