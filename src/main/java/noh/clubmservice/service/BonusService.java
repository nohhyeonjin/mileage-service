package noh.clubmservice.service;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.Bonus;
import noh.clubmservice.repository.BonusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BonusService {

    private final BonusRepository bonusRepository;

    public int calculate(UUID placeId) {
        int point = 0;

        if (isFirstReview(placeId)) {
            point++;
        }

        return point;
    }

    private boolean isFirstReview(UUID placeId) {
        if (bonusRepository.firstReviewAtPlace(placeId)) {
            return true;
        } else {
            return false;
        }
    }

    public void save(UUID placeId, UUID reviewId) {
        Bonus bonus = Bonus.builder()
                .placeId(placeId)
                .reviewId(reviewId)
                .first(true)
                .build();

        bonusRepository.save(bonus);
    }

}
