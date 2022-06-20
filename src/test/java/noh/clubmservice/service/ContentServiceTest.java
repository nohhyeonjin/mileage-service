package noh.clubmservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ContentServiceTest {

    @Autowired
    private ContentService contentService;

    @Test
    public void 내용점수_계산() throws Exception {
        //given
        String emptyContent = "";
        String content = "내용있음";

        List<UUID> emptyPhotos = new ArrayList<>();
        List<UUID> photos = new ArrayList<>();
        photos.add(UUID.fromString("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"));
        photos.add(UUID.fromString("afb0cef2-851d-4a50-bb07-9cc15cbdc332"));

        //when
        int oo = contentService.calculate(content, photos);
        int ox = contentService.calculate(content, emptyPhotos);
        int xo = contentService.calculate(emptyContent, photos);
        int xx = contentService.calculate(emptyContent, emptyPhotos);

        //then
        assertEquals(2,oo);
        assertEquals(1,ox);
        assertEquals(1,xo);
        assertEquals(0,xx);
    }

}