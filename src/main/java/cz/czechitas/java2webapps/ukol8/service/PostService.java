package cz.czechitas.java2webapps.ukol8.service;

import cz.czechitas.java2webapps.ukol8.entity.Post;
import cz.czechitas.java2webapps.ukol8.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PostService {
    public final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository repository) {
        this.postRepository = repository;
    }

    /**
     * Metoda vracející seznam postů
     */
    public Page<Post> list(Pageable pageable) {
        LocalDate currentDate = LocalDate.now();
        return postRepository.findByPublishedNotNullAndPublishedBeforeOrderByPublishedDesc(currentDate, pageable);
    }

    /**
     * Metoda vracející jeden slug (část url adresy odkazující na jeden post)
     */
    public Post singlePost(String slug) {
        return postRepository.findBySlug(slug);
    }
}
