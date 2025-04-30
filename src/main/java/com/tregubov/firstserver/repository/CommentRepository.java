package com.tregubov.firstserver.repository;

import com.tregubov.firstserver.entities.product.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "DELETE FROM comment WHERE id = :commentId",
            nativeQuery = true)
    void deleteCommentById(@Param("commentId") int id);

}
