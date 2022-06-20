package noh.clubmservice.repository;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.UserPoint;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserPointRepository {

    private final EntityManager em;

    public List<UserPoint> findByUser(UUID userId) {
        return em.createQuery("select up from UserPoint up where up.userId=:userId")
                .setParameter("userId",userId)
                .getResultList();
    }

    public void save(UserPoint userPoint) {
        em.persist(userPoint);
    }

}
