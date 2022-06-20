package noh.clubmservice.domain;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
public class Content {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID reviewId;

    // 텍스트점수 획득여부
    @NotNull
    private boolean text;

    // 사진점수 획득여부
    @NotNull
    private boolean photo;

}
