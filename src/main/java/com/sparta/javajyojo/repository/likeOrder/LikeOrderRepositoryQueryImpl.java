package com.sparta.javajyojo.repository.likeOrder;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.javajyojo.entity.Order;
import com.sparta.javajyojo.entity.QLikeOrder;
import com.sparta.javajyojo.entity.QOrder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeOrderRepositoryQueryImpl implements LikeOrderRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> findLikedOrderByUser(Long id, int OrderFerPage) {
        QLikeOrder likeOrder = QLikeOrder.likeOrder;
        QOrder order = QOrder.order;

        return queryFactory
            .selectFrom(order)
            .join(likeOrder).on(order.orderId.eq(likeOrder.order.orderId))
            .where(likeOrder.user.userId.eq(id))
            .limit(OrderFerPage)
            .fetch();
    }

    @Override
    public Integer countOrderLikesByUserId(Long userId) {
        QLikeOrder likeOrder = QLikeOrder.likeOrder;

        return (int) queryFactory
            .selectFrom(likeOrder)
            .where(likeOrder.user.userId.eq(userId))
            .fetchCount();
    }


}
