package noh.clubmservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import noh.clubmservice.controller.Action;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class EventResDTO {

    private Action action;
    private UUID reviewId;
    private int point;

}
