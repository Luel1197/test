package com.sparta.javajyojo.repository.likeReview;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.javajyojo.entity.QLikeOrder;
import com.sparta.javajyojo.entity.QLikeReview;
import com.sparta.javajyojo.entity.Review;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeReviewRepositoryQueryImpl implements LikeReviewRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> findLikeReviewUser(Long id, int reviewFerPage) {
        return List.of();
    }

    @Override
    public Integer countReviewLikesByUserId(Long id) {
        QLikeReview likeReview = QLikeReview.likeReview;

        return (int) queryFactory
            .selectFrom(likeReview)
            .where(likeReview.user.userId.eq(id))
            .fetchCount();
    }
}
