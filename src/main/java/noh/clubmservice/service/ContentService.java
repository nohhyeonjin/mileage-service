package noh.clubmservice.service;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.Content;
import noh.clubmservice.repository.ContentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    public int calculate(String content, List<UUID> attachedPhotoIds) {
        int point = 0;

        if (getTextPoint(content)) {
            point++;
        }

        if (getPhotoPoint(attachedPhotoIds)) {
            point++;
        }

        return point;
    }

    private boolean getTextPoint(String text) {
        if (text.length() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean getPhotoPoint(List<UUID> attachedPhotoIds) {
        if (attachedPhotoIds.size() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public void save(UUID reviewId, String text, List<UUID> attachedPhotoIds) {
        Content content = Content.builder()
                .reviewId(reviewId)
                .text(getTextPoint(text))
                .photo(getPhotoPoint(attachedPhotoIds))
                .build();

        contentRepository.save(content);
    }

}

