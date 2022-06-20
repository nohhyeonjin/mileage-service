package noh.clubmservice.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PointHistory {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;

    @NotNull
    private int point;

    @CreatedDate
    private LocalDateTime date;

    @Builder
    PointHistory(UUID userId, int point) {
        this.userId = userId;
        this.point = point;
    }

}

