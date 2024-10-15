package zerobase.table_reservation.service;

import java.util.List;
import zerobase.table_reservation.model.Review;

public interface ReviewService {
  
  Review createReview(Review review);
  
  Review updateReview(Long reviewId, Review.UpdateRequest review);
  
  void deleteReview(Long reviewId, String username);
  
  List<Review> getReviewsByStore(Long storeId);
  
}
