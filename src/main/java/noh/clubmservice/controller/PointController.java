package noh.clubmservice.controller;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.controller.dto.EventReqDTO;
import noh.clubmservice.service.BonusService;
import noh.clubmservice.service.ContentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final ContentService contentService;
    private final BonusService bonusService;

    @PostMapping("/events")
    public void events(@RequestBody EventReqDTO eventReqDTO) {
        Action action = eventReqDTO.getAction();

        if (action.equals(Action.ADD)) {
            int contentPoint = contentService.calculate(eventReqDTO.getContent(), eventReqDTO.getAttachedPhotoIds());
            int bonusPoint = bonusService.calculate(eventReqDTO.getPlaceId());

            contentService.save(eventReqDTO.getReviewId(), eventReqDTO.getContent(), eventReqDTO.getAttachedPhotoIds());
            if (bonusPoint != 0) {
                bonusService.save(eventReqDTO.getPlaceId(), eventReqDTO.getReviewId());
            }
        }
    }

}