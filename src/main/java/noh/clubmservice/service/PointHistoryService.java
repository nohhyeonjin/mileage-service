package noh.clubmservice.service;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.PointHistory;
import noh.clubmservice.repository.PointHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public void save(UUID userId, int totalPoint) {
        PointHistory pointHistory = PointHistory.builder()
                .userId(userId)
                .point(totalPoint)
                .build();

        pointHistoryRepository.save(pointHistory);
    }

    public List<PointHistory> getSortedUserHistory(UUID userId) {
        List<PointHistory> pointHistories = pointHistoryRepository.findByUser(userId);
        Collections.sort(pointHistories);

        return pointHistories;
    }
}
