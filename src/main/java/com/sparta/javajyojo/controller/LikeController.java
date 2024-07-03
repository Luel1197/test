package com.sparta.javajyojo.controller;

import com.sparta.javajyojo.security.UserDetailsImpl;
import com.sparta.javajyojo.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 리뷰 좋아요
    @PostMapping("/review/{id}")
    public ResponseEntity<String> addLikeReview(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.LikeReview(id, userDetails.getUser());
        return ResponseEntity.ok(id + "번 리뷰에 좋아요 성공 ");
    }

    // 리뷰 좋아요 최소
    @DeleteMapping("/review/{id}")
    public ResponseEntity<String> unLikeReview(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.unLikeReview(id, userDetails.getUser());
        return ResponseEntity.ok(id + "번 리뷰에 좋아요 취소 ");
    }

    // 오더 디테일 좋아요
    @PostMapping("/order/{id}")
    public ResponseEntity<String> addLikeOrder(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.LikeOrder(id, userDetails.getUser());
        return ResponseEntity.ok(id + "번 주문에 좋아요 성공 ");
    }

    // 오더 디테일 좋아요 취소
    @DeleteMapping("/order/{id}")
    public ResponseEntity<String> unLikeOrder(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.unLikeOrder(id, userDetails.getUser());
        return ResponseEntity.ok(id + "번 주문에 좋아요 취소 ");
    }
}