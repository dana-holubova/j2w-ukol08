package cz.czechitas.java2webapps.ukol8.repository;

import cz.czechitas.java2webapps.ukol8.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Metoda vracející seznam postů, které mají datum příspěvku a není v budoucnosti
     * Metoda findByPublishedBefore mi nezobrazovala ani příspěvky bez data publikování
     * Metoda by měla udělat asi toto: ...where x.published not null and x.published < ?1 order by x.published desc
     */
    Page<Post> findByPublishedNotNullAndPublishedBeforeOrderByPublishedDesc(LocalDate currentDate, Pageable pageable);

    /**
     * TODO: Zjistit proč nefunguje metoda s anotací @Query. Je červeně zvýrazněné slovo null.
     */
//    @Query("SELECT p FROM Post p WHERE p.published NOT NULL AND p.published < ?1 ORDER BY p.published DESC")
//    Page<Post> findByPublished(LocalDate currentDate, Pageable pageable);

    /**
     * Metoda vracející slug
     */
    Post findBySlug(String slug);

}
