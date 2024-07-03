package com.sparta.javajyojo.repository;

import com.sparta.javajyojo.entity.Like;
import com.sparta.javajyojo.entity.Order;
import com.sparta.javajyojo.entity.Review;
import com.sparta.javajyojo.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndReview(User user, Review review);
    Optional<Like> findByUserAndOrder(User user, Order order);
}
