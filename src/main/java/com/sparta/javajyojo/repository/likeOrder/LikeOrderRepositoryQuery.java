package com.sparta.javajyojo.repository.likeOrder;

import com.sparta.javajyojo.entity.Order;
import java.util.List;

public interface LikeOrderRepositoryQuery {
    List<Order> findLikedOrderByUser(Long id, int OrderFerPage);
    Integer countOrderLikesByUserId(Long userId);
}
