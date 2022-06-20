package noh.clubmservice.repository;

import lombok.RequiredArgsConstructor;
import noh.clubmservice.domain.Content;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ContentRepository {

    private final EntityManager em;

    public void save(Content content) {
        em.persist(content);
    }

}
