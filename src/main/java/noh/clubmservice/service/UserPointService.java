package noh.clubmservice.service;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.UserPoint;
import noh.clubmservice.repository.UserPointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPointService {

    private final UserPointRepository userPointRepository;

    public UserPoint findByUser(UUID userId) {
        List<UserPoint> userPoints = userPointRepository.findByUser(userId);
        if (userPoints.isEmpty()) {
            return null;
        } else {
            return userPoints.get(0);
        }
    }

    public void save(UUID userId, int totalPoint) {
        UserPoint userPoint = UserPoint.builder()
                .userId(userId)
                .point(totalPoint)
                .build();

        userPointRepository.save(userPoint);
    }

    public void update(UUID userId, int updatePoint) {
        UserPoint userPoint = findByUser(userId);
        int point = userPoint.getPoint() + updatePoint;
        userPoint.setPoint(point);
    }
}
