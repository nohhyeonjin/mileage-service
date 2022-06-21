package noh.clubmservice.service;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.Content;
import noh.clubmservice.repository.ContentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentService {

    //==보상 점수 조건==//
    private static final int MINIMUM_TEXT = 1;
    private static final int MINIMUM_PHOTO = 1;

    //==보상 점수==//
    private static final int NONE = 0;
    private static final int TEXT_POINT = 1;
    private static final int PHOTO_POINT = 1;

    private final ContentRepository contentRepository;

    public int calculate(String content, List<UUID> attachedPhotoIds) {
        return calculateTextPoint(content) + calculatePhotoPoint(attachedPhotoIds);
    }

    public int calculateTextPoint(String text) {
        if (text.length() >= MINIMUM_TEXT) {
            return TEXT_POINT;
        } else {
            return NONE;
        }
    }

    public int calculatePhotoPoint(List<UUID> attachedPhotoIds) {
        if (attachedPhotoIds.size() >= MINIMUM_PHOTO) {
            return PHOTO_POINT;
        } else {
            return NONE;
        }
    }

    public int calculateDiff(UUID reviewId, String text, List<UUID> attachedPhotoIds) {
        Content content = findByReview(reviewId);
        int originTextPoint = content.getText();
        int originPhotoPoint = content.getPhoto();

        int modifiedTextPoint = calculateTextPoint(text);
        int modifiedPhotoPoint = calculatePhotoPoint(attachedPhotoIds);

        return (modifiedTextPoint - originTextPoint) + (modifiedPhotoPoint - originPhotoPoint);
    }

    public Content findByReview(UUID reviewId) {
        List<Content> contents = contentRepository.findByReview(reviewId);
        if (contents.isEmpty()) {
            return null;
        } else {
            return contents.get(0);
        }
    }

    @Transactional
    public void save(UUID reviewId, String text, List<UUID> attachedPhotoIds) {
        Content content = Content.builder()
                .reviewId(reviewId)
                .text(calculateTextPoint(text))
                .photo(calculatePhotoPoint(attachedPhotoIds))
                .build();

        contentRepository.save(content);
    }

    @Transactional
    public void update(UUID reviewId, int modifiedTextPoint, int modifiedPhotoPoint) {
        Content content = findByReview(reviewId);
        content.setText(modifiedTextPoint);
        content.setPhoto(modifiedPhotoPoint);
    }

}

