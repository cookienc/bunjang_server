package shop.makaroni.bunjang.src.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shop.makaroni.bunjang.src.domain.item.Item;

import java.util.List;

@Mapper
public interface ItemMapper {
	List<Item> getMyStoreItem(@Param("userId") Long userId,
							  @Param("condition") String condition,
							  @Param("start") Integer start,
							  @Param("offset") Integer offset,
							  @Param("sortCond") String sortCond);
}