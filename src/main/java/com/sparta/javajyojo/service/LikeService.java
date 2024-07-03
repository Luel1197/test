package com.sparta.javajyojo.service;

import com.sparta.javajyojo.entity.Like;
import com.sparta.javajyojo.entity.Order;
import com.sparta.javajyojo.entity.OrderDetail;
import com.sparta.javajyojo.entity.Review;
import com.sparta.javajyojo.entity.User;
import com.sparta.javajyojo.repository.LikeRepository;
import com.sparta.javajyojo.repository.OrderDetailRepository;
import com.sparta.javajyojo.repository.OrderRepository;
import com.sparta.javajyojo.repository.ReviewRepository;
import com.sparta.javajyojo.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;

    private final LikeRepository likeRepository;

    private final OrderRepository orderRepository;

    private final ReviewRepository reviewRepository;

    // 리뷰 체크
    public Review findByReviewId(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 리뷰 입니다."));
        return review;
    }

    // 오더 체크
    public Order findByOrderId(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 오더 입니다."));
        return order;
    }

    public void LikeReview(Long id, User user) {
        // 리뷰 있는지 체크
        Review review = findByReviewId(id);

        // 자신의 리뷰인지 체크
        if (review.getUserId() == user.getUserId()) {
            throw new RuntimeException("자신의 리뷰에는 좋아요를 남길 수 없습니다.");
        }

        // 중복 좋아요 체크
        Optional<Like> likeOptional = likeRepository.findByUserAndReview(user, review);
        if (likeOptional.isPresent()) {
            throw new RuntimeException("이미 좋아요를 누른 리뷰 입니다.");
        }

        // 추가
        Like like = new Like();
        like.setUser(user);
        like.setReview(review);
        like.setCreatedAt(LocalDateTime.now());

        review.addLike(like);

        likeRepository.save(like);
    }

    public void unLikeReview(Long id, User user) {
        // 리뷰 있는지 체크
        Review review = findByReviewId(id);

        Like like = likeRepository.findByUserAndReview(user, review).orElseThrow(() -> new RuntimeException("이 리뷰에 좋아요를 한 적이 없습니다."));

        review.unLike(like);

        likeRepository.delete(like);
    }

    public void LikeOrder(Long id, User user) {
        Order order = findByOrderId(id);

        if (order.getUser().getUserId() == user.getUserId()) {
            throw new RuntimeException("자신의 주문에는 좋아요를 남길 수 없습니다.");
        }

        Optional<Like> likeOptional = likeRepository.findByUserAndOrder(user, order);
        if (likeOptional.isPresent()) {
            throw new RuntimeException("이미 좋아요를 누른 주문 입니다.");
        }

        Like like = new Like();
        like.setUser(user);
        like.setOrder(order);
        like.setCreatedAt(LocalDateTime.now());

        order.addLike(like);

        likeRepository.save(like);

    }

    public void unLikeOrder(Long id, User user) {
        Order order = findByOrderId(id);

        Like like = likeRepository.findByUserAndOrder(user, order).orElseThrow(() -> new RuntimeException("이 주문에 좋아요를 한 적이 없습니다."));

        order.unLike(like);

        likeRepository.delete(like);
    }
}
