package noh.clubmservice.controller;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.controller.dto.EventReqDTO;
import noh.clubmservice.controller.dto.HistoryResDTO;
import noh.clubmservice.controller.dto.PointResDTO;
import noh.clubmservice.domain.Bonus;
import noh.clubmservice.domain.Content;
import noh.clubmservice.service.BonusService;
import noh.clubmservice.service.ContentService;
import noh.clubmservice.service.PointHistoryService;
import noh.clubmservice.service.UserPointService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final ContentService contentService;
    private final BonusService bonusService;
    private final PointHistoryService pointHistoryService;
    private final UserPointService userPointService;

    @PostMapping("/events")
    public void events(@RequestBody EventReqDTO eventReqDTO) {
        Action action = eventReqDTO.getAction();

        if (action.equals(Action.ADD)) {
            // 리뷰 작성 보상 점수 계산
            int contentPoint = contentService.calculate(eventReqDTO.getContent(), eventReqDTO.getAttachedPhotoIds());
            int bonusPoint = bonusService.calculate(eventReqDTO.getPlaceId());
            int totalPoint = contentPoint + bonusPoint;

            // 계산 포인트 저장
            contentService.save(eventReqDTO.getReviewId(), eventReqDTO.getContent(), eventReqDTO.getAttachedPhotoIds());
            if (bonusPoint != 0) {
                bonusService.save(eventReqDTO.getPlaceId(), eventReqDTO.getReviewId());
            }

            // 포인트 이력 저장
            pointHistoryService.save(eventReqDTO.getUserId(), totalPoint);
            userPointService.save(eventReqDTO.getUserId(), totalPoint);
        } else if (action.equals(Action.MOD)) {
            // 기존 내용 점수 로드
            Content content = contentService.findByReview(eventReqDTO.getReviewId());
            int originTextPoint = content.getText();
            int originPhotoPoint = content.getPhoto();

            // 수정 내용 점수 계산
            int modifiedTextPoint = contentService.calculateTextPoint(eventReqDTO.getContent());
            int modifiedPhotoPoint = contentService.calculatePhotoPoint(eventReqDTO.getAttachedPhotoIds());

            int updatePoint = (modifiedTextPoint - originTextPoint) + (modifiedPhotoPoint - originPhotoPoint);

            // 수정 내용 점수 저장
            contentService.update(eventReqDTO.getReviewId(), modifiedTextPoint, modifiedPhotoPoint);

            // 포인트 이력 저장
            pointHistoryService.save(eventReqDTO.getUserId(), updatePoint);
            userPointService.update(eventReqDTO.getUserId(), updatePoint);
        } else if (action.equals(Action.DELETE)) {
            // 기존 내용 점수 로드
            Content content = contentService.findByReview(eventReqDTO.getReviewId());
            int textPoint = content.getText();
            int photoPoint = content.getPhoto();

            // 보너스 점수 로드
            int bonusPoint = bonusService.getPoint(eventReqDTO.getReviewId());
            if (bonusPoint != 0) {
                bonusService.changeState(eventReqDTO.getReviewId());
            }

            int updatePoint = -1 * (textPoint + photoPoint + bonusPoint);

            // 포인트 이력 저장
            pointHistoryService.save(eventReqDTO.getUserId(), updatePoint);
            userPointService.update(eventReqDTO.getUserId(), updatePoint);
        }
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