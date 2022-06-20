package noh.clubmservice.repository;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.PointHistory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepository {

    private final EntityManager em;

    public void save(PointHistory pointHistory) {
        em.persist(pointHistory);
    }

    public List<PointHistory> findByUser(UUID userId) {
        return em.createQuery("select ph from PointHistory ph where ph.userId=:userId")
                .setParameter("userId",userId)
                .getResultList();
    }
}
