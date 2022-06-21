package noh.clubmservice.service;

import noh.clubmservice.domain.Content;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PointServiceTest {

    @Autowired
    private PointService pointService;

    @Autowired
    private BonusService bonusService;

    @Autowired
    private ContentService contentService;

    @Test
    public void 첫_리뷰_작성() throws Exception {
        //given
        UUID reviewId = UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772");
        String text = "좋아요!";
        List<UUID> attachedPhotoIds = new ArrayList<>();
        attachedPhotoIds.add(UUID.fromString("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"));
        attachedPhotoIds.add(UUID.fromString("afb0cef2-851d-4a50-bb07-9cc15cbdc332"));
        UUID userId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745");
        UUID placeId = UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f");

        //when
        pointService.add(text, attachedPhotoIds, placeId, reviewId, userId);

        //then
        Content content = contentService.findByReview(reviewId);
        int bonusPoint = bonusService.getPoint(reviewId);
        int contentPoint = content.getText() + content.getPhoto();

        assertEquals(3, bonusPoint + contentPoint);
        assertEquals(1, bonusPoint);
        assertEquals(2, contentPoint);
    }

    @Test
    public void 두번째_리뷰_작성() throws Exception {
        //given
        UUID reviewId = UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772");
        String text = "좋아요!";
        List<UUID> attachedPhotoIds = new ArrayList<>();
        attachedPhotoIds.add(UUID.fromString("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"));
        attachedPhotoIds.add(UUID.fromString("afb0cef2-851d-4a50-bb07-9cc15cbdc332"));
        UUID userId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745");
        UUID placeId = UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f");

        UUID secondReviewId = UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667799");
        UUID secondUserId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f799");

        //when
        pointService.add(text, attachedPhotoIds, placeId, reviewId, userId);
        pointService.add(text, attachedPhotoIds, placeId, secondReviewId, secondUserId);

        //then
        Content content = contentService.findByReview(secondReviewId);
        int bonusPoint = bonusService.getPoint(secondReviewId);
        int contentPoint = content.getText() + content.getPhoto();

        assertEquals(2, bonusPoint + contentPoint);
        assertEquals(0, bonusPoint);
        assertEquals(2, contentPoint);
    }

    @Test
    public void 리뷰_수정() throws Exception {
        //given
        UUID userId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745");
        UUID reviewId = createReview(userId);

        //when
        String modifiedContent = "수정텍스트";
        List<UUID> modifiedAttachedPhotoIds = new ArrayList<>();
        int diff = pointService.modify(modifiedContent, modifiedAttachedPhotoIds, reviewId, userId);

        //then
        Content content = contentService.findByReview(reviewId);

        assertEquals(-1, diff);
        assertEquals(1, content.getText());
        assertEquals(0, content.getPhoto());
    }

    @Test
    public void 리뷰_삭제() throws Exception {
        //given
        UUID userId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745");
        UUID reviewId = createReview(userId);

        //when
        int deletePoint = pointService.delete(reviewId, userId);

        //then
        assertEquals(-3, deletePoint);
    }

    @Test
    public void A삭제_후_B작성() throws Exception {
        //given
        UUID A = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745");
        UUID AReviewId = createReview(A);
        pointService.delete(AReviewId, A);

        //when
        UUID B = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f799");
        UUID BReviewId = createReview(B);

        //then
        int bonusPoint = bonusService.getPoint(BReviewId);

        assertEquals(1, bonusPoint);
    }

    @Test
    public void A삭제_전_B작성() throws Exception {
        //given
        UUID A = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745");
        UUID AReviewId = createReview(A);

        //when
        UUID B = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f799");
        UUID BReviewId = createReview(B);
        pointService.delete(AReviewId, A);

        //then
        int bonusPoint = bonusService.getPoint(BReviewId);

        assertEquals(0, bonusPoint);
    }

    private UUID createReview(UUID userId) {
        UUID reviewId = UUID.randomUUID();
        String text = "좋아요!";
        List<UUID> attachedPhotoIds = new ArrayList<>();
        attachedPhotoIds.add(UUID.fromString("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"));
        attachedPhotoIds.add(UUID.fromString("afb0cef2-851d-4a50-bb07-9cc15cbdc332"));
        UUID placeId = UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f");

        pointService.add(text, attachedPhotoIds, placeId, reviewId, userId);

        return reviewId;
    }

}