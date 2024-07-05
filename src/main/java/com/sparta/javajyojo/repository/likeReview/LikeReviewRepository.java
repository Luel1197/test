package com.sparta.javajyojo.repository.likeReview;

import com.sparta.javajyojo.entity.LikeReview;
import com.sparta.javajyojo.entity.Review;
import com.sparta.javajyojo.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeReviewRepository extends JpaRepository<LikeReview, Long>, LikeReviewRepositoryQuery {
    Optional<LikeReview> findByUserAndReview(User user, Review review);
}
