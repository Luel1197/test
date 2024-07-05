package com.sparta.javajyojo.service;

import com.sparta.javajyojo.dto.LikedOrderResponseDto;
import com.sparta.javajyojo.dto.ReviewResponseDto;
import com.sparta.javajyojo.entity.LikeOrder;
import com.sparta.javajyojo.entity.LikeReview;
import com.sparta.javajyojo.entity.Order;
import com.sparta.javajyojo.entity.Review;
import com.sparta.javajyojo.entity.User;
import com.sparta.javajyojo.repository.OrderRepository;
import com.sparta.javajyojo.repository.ReviewRepository;
import com.sparta.javajyojo.repository.likeOrder.LikeOrderRepository;
import com.sparta.javajyojo.repository.likeReview.LikeReviewRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final OrderRepository orderRepository;

    private final ReviewRepository reviewRepository;

    private final LikeReviewRepository LikeReviewRepository;

    private final LikeOrderRepository likeOrderRepository;
    private final LikeReviewRepository likeReviewRepository;

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
        Optional<LikeReview> likeReviewOptional = likeReviewRepository.findByUserAndReview(user, review);
        if (likeReviewOptional.isPresent()) {
            throw new RuntimeException("이미 좋아요를 누른 리뷰 입니다.");
        }

        // 추가
        LikeReview likeReview = new LikeReview();
        likeReview.setUser(user);
        likeReview.setReview(review);
        likeReview.setCreateAt(LocalDateTime.now());

        review.addLike(likeReview);

        likeReviewRepository.save(likeReview);
    }

    public void unLikeReview(Long id, User user) {
        // 리뷰 있는지 체크
        Review review = findByReviewId(id);

        LikeReview likeReview = likeReviewRepository.findByUserAndReview(user, review).orElseThrow(() -> new RuntimeException("이 리뷰에 좋아요를 한 적이 없습니다."));

        review.unLike(likeReview);

        likeReviewRepository.delete(likeReview);
    }

    public void LikeOrder(Long id, User user) {
        Order order = findByOrderId(id);

        if (order.getUser().getUserId() == user.getUserId()) {
            throw new RuntimeException("자신의 주문에는 좋아요를 남길 수 없습니다.");
        }

        Optional<LikeOrder> likeOrderOptional = likeOrderRepository.findByUserAndOrder(user, order);
        if (likeOrderOptional.isPresent()) {
            throw new RuntimeException("이미 좋아요를 누른 주문 입니다.");
        }

        LikeOrder likeOrder = new LikeOrder();
        likeOrder.setUser(user);
        likeOrder.setOrder(order);
        likeOrder.setCreateAt(LocalDateTime.now());

        order.addLike(likeOrder);

        likeOrderRepository.save(likeOrder);

    }

    public void unLikeOrder(Long id, User user) {
        Order order = findByOrderId(id);

        LikeOrder likeOrder = likeOrderRepository.findByUserAndOrder(user, order).orElseThrow(() -> new RuntimeException("이 주문에 좋아요를 한 적이 없습니다."));

        order.unLike(likeOrder);

        likeOrderRepository.delete(likeOrder);
    }

    public Page<ReviewResponseDto> getLikedReviews(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        int reviewFerPage = pageable.getPageSize();
        List<Review> likeReview = LikeReviewRepository.findLikeReviewUser(user.getUserId(), reviewFerPage);

        return new PageImpl<>(likeReview.stream()
            .map(ReviewResponseDto::new)
            .collect(Collectors.toList()));
    }
    public Page<LikedOrderResponseDto> getLikedOrders(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        int OrderFerPage = pageable.getPageSize();
        List<Order> likeOrder = likeOrderRepository.findLikedOrderByUser(user.getUserId(), OrderFerPage);

        return new PageImpl<>(likeOrder.stream()
            .map(LikedOrderResponseDto::new)
            .collect(Collectors.toList()));
    }

//    public Page<ReviewResponseDto> getLikeReview(int page, int size, User user) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("likes").descending());
//        List<> = likeRepository.findByUser(user);
//        return Review.map(this::convertToDto);
//    }
//
//    public ReviewResponseDto convertToDto(Review review) {
//        return new ReviewResponseDto(review);
//    }
}
