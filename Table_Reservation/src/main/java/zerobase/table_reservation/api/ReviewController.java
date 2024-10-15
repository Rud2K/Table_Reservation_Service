package zerobase.table_reservation.api;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.model.Review;
import zerobase.table_reservation.service.ReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/reviews")
public class ReviewController {
  
  private final ReviewService reviewService;
  
  /**
   * 새로운 리뷰를 생성하는 메소드
   * POST /reviews
   * 
   * @param review 생성할 리뷰 정보
   * @return 생성된 리뷰를 포함한 ResponseEntity
   */
  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Review> createReview(@RequestBody Review review) {
    Review newReview = this.reviewService.createReview(review);
    return ResponseEntity.status(HttpStatus.CREATED).body(newReview);
  }
  
  /**
   * 기존 리뷰를 수정하는 메소드
   * PUT /reviews/{reviewId}
   * 
   * @param reviewId 수정할 리뷰의 ID
   * @param review 수정할 리뷰 정보
   * @return 수정된 리뷰를 포함한 ResponseEntity
   */
  @PutMapping("/{reviewId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Review> updateReview(
      @PathVariable("reviewId") Long reviewId,
      @RequestBody Review.UpdateRequest review) {
    Review updatedReview = this.reviewService.updateReview(reviewId, review);
    return ResponseEntity.ok(updatedReview);
  }
  
  /**
   * 특정 리뷰를 삭제하는 메소드
   * DELETE /reviews/{reviewId}
   * 
   * @param reviewId 삭제할 리뷰의 ID
   * @return ResponseEntity<Void>
   */
  @DeleteMapping("/{reviewId}")
  @PreAuthorize("hasRole('PARTNER') or hasRole('USER')")
  public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long reviewId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    this.reviewService.deleteReview(reviewId, authentication.getName());
    return ResponseEntity.noContent().build();
  }
  
  /**
   * 특정 매장에 대한 모든 리뷰를 조회하는 메소드
   * 
   * @param storeId 리뷰를 조회할 매장의 ID
   * @return 해당 매장에 대한 리뷰 리스트를 포함한 ResponseEntity
   */
  @GetMapping("/store/{storeId}")
  @PreAuthorize("hasRole('PARTNER') or hasRole('USER')")
  public ResponseEntity<List<Review>> getReviewsByStore(@PathVariable("storeId") Long storeId) {
    List<Review> reveiws = this.reviewService.getReviewsByStore(storeId);
    return ResponseEntity.ok(reveiws);
  }
  
}
