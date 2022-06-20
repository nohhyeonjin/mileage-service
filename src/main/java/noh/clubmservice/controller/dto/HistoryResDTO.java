package noh.clubmservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import noh.clubmservice.domain.PointHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class HistoryResDTO {

    private UUID userId;
    private List<PointInfo> points;

    public HistoryResDTO(UUID userId, List<PointHistory> pointHistories) {
        this.userId = userId;

        points = pointHistories.stream()
                .map(pointHistory -> new PointInfo(pointHistory.getPoint(), pointHistory.getDate()))
                .collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    private class PointInfo {
        private int point;
        private LocalDateTime time;
    }
}
