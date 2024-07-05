package com.sparta.javajyojo.repository.likeOrder;

import com.sparta.javajyojo.entity.LikeOrder;
import com.sparta.javajyojo.entity.Order;
import com.sparta.javajyojo.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeOrderRepository extends JpaRepository<LikeOrder, Long>, LikeOrderRepositoryQuery {
    Optional<LikeOrder> findByUserAndOrder(User user, Order order);
}
