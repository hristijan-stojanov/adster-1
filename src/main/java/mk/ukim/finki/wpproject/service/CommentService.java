package mk.ukim.finki.wpproject.service;


import mk.ukim.finki.wpproject.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findAll();

    Optional<Comment> findById(Long id);

    Optional<Comment> save(Comment comment);

    void deleteById(Long id);

}
