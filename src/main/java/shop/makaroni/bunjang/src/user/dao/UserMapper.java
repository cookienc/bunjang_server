package shop.makaroni.bunjang.src.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shop.makaroni.bunjang.src.user.model.dto.PatchUserRequest;

@Mapper
public interface UserMapper {
	void update(@Param("userId") Long userId, @Param("request") PatchUserRequest request);
}
