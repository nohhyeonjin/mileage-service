package noh.clubmservice.service;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.Content;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PointService {

    private final ContentService contentService;
    private final BonusService bonusService;
    private final PointHistoryService pointHistoryService;
    private final UserPointService userPointService;

    public int add(String content, List<UUID> attachedPhotoIds, UUID placeId, UUID reviewId, UUID userId) {
        int totalPoint = calculateSavePoint(content, attachedPhotoIds, placeId);
        update(userId, totalPoint);

        contentService.save(reviewId, content, attachedPhotoIds);
        int bonusPoint = bonusService.calculate(placeId);
        if (bonusPoint != 0) {
            bonusService.save(placeId, reviewId);
        }

        return totalPoint;
    }

    public int modify(String content, List<UUID> attachedPhotoIds, UUID reviewId, UUID userId) {
        int updatePoint = contentService.calculateDiff(reviewId, content, attachedPhotoIds);
        update(userId, updatePoint);

        int modifiedTextPoint = contentService.calculateTextPoint(content);
        int modifiedPhotoPoint = contentService.calculatePhotoPoint(attachedPhotoIds);
        contentService.update(reviewId, modifiedTextPoint, modifiedPhotoPoint);

        return updatePoint;
    }

    public int delete(UUID reviewId, UUID userId) {
        int updatePoint = calculateDeletePoint(reviewId);
        update(userId, updatePoint);

        int bonusPoint = bonusService.getPoint(reviewId);
        if (bonusPoint != 0) {
            bonusService.changeState(reviewId, false);
        }

        return updatePoint;
    }

    private int calculateSavePoint(String content, List<UUID> attachedPhotoIds, UUID placeId) {
        int contentPoint = contentService.calculate(content, attachedPhotoIds);
        int bonusPoint = bonusService.calculate(placeId);

        return contentPoint + bonusPoint;
    }

    private int calculateDeletePoint(UUID reviewId) {
        Content content = contentService.findByReview(reviewId);
        int textPoint = content.getText();
        int photoPoint = content.getPhoto();
        int bonusPoint = bonusService.getPoint(reviewId);

        return -1 * (textPoint + photoPoint + bonusPoint);
    }

    private void update(UUID userId, int point) {
        if (point != 0) {
            pointHistoryService.save(userId, point);
        }
        userPointService.update(userId, point);
    }

}
