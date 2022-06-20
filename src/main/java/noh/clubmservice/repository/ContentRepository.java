package noh.clubmservice.repository;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.Content;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ContentRepository {

    private final EntityManager em;

    public void save(Content content) {
        em.persist(content);
    }

    public List<Content> findByReview(UUID reviewId) {
        return em.createQuery("select c from Content c where c.reviewId=:reviewId")
                .setParameter("reviewId",reviewId)
                .getResultList();
    }
}
