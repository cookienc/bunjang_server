package shop.makaroni.bunjang.src.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shop.makaroni.bunjang.src.domain.review.UpdateReviewRequest;

@Mapper
public interface ReviewMapper {

	void updateReview(@Param("reviewIdx") Long reviewIdx, @Param("request") UpdateReviewRequest request);
}
