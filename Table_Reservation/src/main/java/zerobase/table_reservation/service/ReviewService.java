package zerobase.table_reservation.service;

import java.util.List;
import zerobase.table_reservation.model.Review;

public interface ReviewService {
  
  /**
   * 새로운 리뷰를 생성하는 메소드
   * 
   * @param review 생성할 리뷰 정보
   * @return 생성된 리뷰 객체
   */
  Review createReview(Review review);
  
  /**
   * 기존 리뷰를 업데이트하는 메소드
   * 
   * @param reviewId 업데이트할 리뷰의 ID
   * @param review 업데이트할 리뷰 정보
   * @return 업데이트된 리뷰 객체
   */
  Review updateReview(Long reviewId, Review.UpdateRequest review);
  
  /**
   * 특정 리뷰를 삭제하는 메소드
   * 
   * @param reviewId 업데이트할 리뷰 ID
   * @param username 리뷰를 삭제하는 사용자 이름
   */
  void deleteReview(Long reviewId, String username);
  
  /**
   * 특정 매장에 대한 모든 리뷰를 조회하는 메소드
   * 
   * @param storeId 리뷰를 조회할 매장의 ID
   * @return 매장에 대한 리뷰 리스트
   */
  List<Review> getReviewsByStore(Long storeId);
  
}
