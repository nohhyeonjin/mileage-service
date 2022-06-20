package noh.clubmservice.controller.dto;

import lombok.Getter;
import noh.clubmservice.domain.UserPoint;

import java.util.UUID;

@Getter
public class PointResDTO {

    private UUID userId;
    private int userTotalPoint;

    public PointResDTO(UserPoint userPoint) {
        this.userId = userPoint.getUserId();
        this.userTotalPoint = userPoint.getPoint();
    }

}
