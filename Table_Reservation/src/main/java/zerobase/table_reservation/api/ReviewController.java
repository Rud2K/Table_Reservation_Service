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
  
  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Review> createReview(@RequestBody Review review) {
    Review newReview = this.reviewService.createReview(review);
    return ResponseEntity.status(HttpStatus.CREATED).body(newReview);
  }
  
  @PutMapping("/{reviewId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Review> updateReview(
      @PathVariable("reviewId") Long reviewId,
      @RequestBody Review.UpdateRequest review) {
    Review updatedReview = this.reviewService.updateReview(reviewId, review);
    return ResponseEntity.ok(updatedReview);
  }
  
  @DeleteMapping("/{reviewId}")
  @PreAuthorize("hasRole('PARTNER') or hasRole('USER')")
  public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long reviewId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    this.reviewService.deleteReview(reviewId, authentication.getName());
    return ResponseEntity.noContent().build();
  }
  
  @GetMapping("/store/{storeId}")
  @PreAuthorize("hasRole('PARTNER') or hasRole('USER')")
  public ResponseEntity<List<Review>> getReviewsByStore(@PathVariable("storeId") Long storeId) {
    List<Review> reveiws = this.reviewService.getReviewsByStore(storeId);
    return ResponseEntity.ok(reveiws);
  }
  
}
