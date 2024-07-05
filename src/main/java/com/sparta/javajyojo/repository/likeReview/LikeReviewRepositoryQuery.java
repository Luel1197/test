package com.sparta.javajyojo.repository.likeReview;

import com.sparta.javajyojo.entity.Review;
import java.util.List;

public interface LikeReviewRepositoryQuery  {
    List<Review> findLikeReviewUser(Long id, int reviewFerPage);
    Integer countReviewLikesByUserId(Long userId);
}
