package noh.clubmservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BonusServiceTest {

    @Autowired
    private BonusService bonusService;

    @Test
    public void 보너스점수_계산_0점() throws Exception {
        //given
        UUID placeId = UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f");
        UUID reviewId = UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772");

        createBonus(placeId, reviewId);

        //when
        int bonusPoint = bonusService.calculate(placeId);

        //then
        assertEquals(0,bonusPoint);
    }

    @Test
    public void 보너스점수_계산_1점() throws Exception {
        //given
        UUID placeId = UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f");

        //when
        int bonusPoint = bonusService.calculate(placeId);

        //then
        assertEquals(1,bonusPoint);
    }

    private void createBonus(UUID placeId, UUID reviewId) {
        bonusService.save(placeId, reviewId);
    }

}