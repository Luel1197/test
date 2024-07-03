package com.sparta.javajyojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.javajyojo.dto.ReviewRequestDto;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reviews")
@NoArgsConstructor
public class Review extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long userId;

    private String review;

    private Long rating;

    @OneToMany(mappedBy = "review")
    @JsonIgnore
    private List<Like> likeList = new ArrayList<>();

    private Integer likesCount = 0;

    public Review(ReviewRequestDto reviewRequestDto, Order order) {
        this.order = order;
        this.userId = order.getUser().getUserId();
        this.review = reviewRequestDto.getReview();
        this.rating = reviewRequestDto.getRating();
    }

    public Review(Order order, Long userId, String review, Long rating) {
        this.order = order;
        this.userId = userId;
        this.review = review;
        this.rating = rating;
    }

    public void update(ReviewRequestDto reviewRequestDto) {
        this.review = reviewRequestDto.getReview();
        this.rating = reviewRequestDto.getRating();
    }

    public void addLike(Like like) {
        likeList.add(like);
        like.setReview(this);
        likesCount++;
    }

    public void unLike(Like like) {
        likeList.remove(like);
        like.setReview(null);
        likesCount--;
    }
}
