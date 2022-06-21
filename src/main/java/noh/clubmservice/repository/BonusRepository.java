package noh.clubmservice.repository;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.Bonus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BonusRepository {

    private final EntityManager em;

    public boolean isFirst(UUID placeId) {
        String query = "select " +
                "case when (count(*) = 0) " +
                "then true " +
                "else false end " +
                "from Bonus b where b.placeId=:placeId and b.first=true";

        return em.createQuery(query, Boolean.class)
                .setParameter("placeId", placeId)
                .getSingleResult();
    }

    public void save(Bonus bonus) {
        em.persist(bonus);
    }

}
