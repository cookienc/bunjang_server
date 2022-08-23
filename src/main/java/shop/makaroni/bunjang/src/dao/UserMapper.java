package shop.makaroni.bunjang.src.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shop.makaroni.bunjang.src.domain.user.dto.PatchUserRequest;

@Mapper
public interface UserMapper {
	void update(@Param("userIdx") Long userIdx, @Param("request") PatchUserRequest request);
}
