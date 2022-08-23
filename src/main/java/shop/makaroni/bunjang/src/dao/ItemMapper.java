package shop.makaroni.bunjang.src.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shop.makaroni.bunjang.src.domain.item.Item;
import shop.makaroni.bunjang.utils.resolver.PagingCond;

import java.util.List;

@Mapper
public interface ItemMapper {
	List<Item> getMyStoreItem(@Param("userIdx") Long userIdx,
							  @Param("condition") String condition,
							  @Param("pagingCond") PagingCond pagingCond);

	List<Item> searchStoreItemByName(@Param("userIdx") Long userIdx,
									 @Param("itemName") String itemName,
									 @Param("condition") String condition,
									 @Param("pagingCond") PagingCond pagingCond);
}