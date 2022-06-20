package noh.clubmservice.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Content {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID reviewId;

    // 텍스트점수 획득
    @NotNull
    private int text;

    // 사진점수 획득
    @NotNull
    private int photo;

    @Builder
    Content(UUID reviewId, int text, int photo) {
        this.reviewId = reviewId;
        this.text = text;
        this.photo = photo;
    }

}
