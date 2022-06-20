package noh.clubmservice.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Bonus {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID placeId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID reviewId;

    @NotNull
    private boolean first;

    @Builder
    Bonus(UUID placeId, UUID reviewId, boolean first) {
        this.placeId = placeId;
        this.reviewId = reviewId;
        this.first = first;
    }

}