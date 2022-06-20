package noh.clubmservice.repository;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.PointHistory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepository {

    private final EntityManager em;

    public void save(PointHistory pointHistory) {
        em.persist(pointHistory);
        em.flush();
        em.refresh(pointHistory);
    }

}
