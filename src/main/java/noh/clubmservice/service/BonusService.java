package noh.clubmservice.service;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.Bonus;
import noh.clubmservice.repository.BonusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BonusService {

    //==보상 점수==//
    private static final int NONE = 0;
    private static final int BONUS_POINT = 1;

    private final BonusRepository bonusRepository;

    public int calculate(UUID placeId) {
        if (bonusRepository.isFirst(placeId)) {
            return BONUS_POINT;
        } else {
            return NONE;
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

    public int getPoint(UUID reviewId) {
        Bonus bonus = findByReview(reviewId);
        if (bonus != null) {
            return BONUS_POINT;
        } else {
            return NONE;
        }
    }

    private Bonus findByReview(UUID reviewId) {
        List<Bonus> bonusList = bonusRepository.findByReview(reviewId);
        if (bonusList.isEmpty()) {
            return null;
        } else {
            return bonusList.get(0);
        }
    }

    public void changeState(UUID reviewId, boolean state) {
        Bonus bonus = findByReview(reviewId);
        bonus.setFirst(state);
    }

}
