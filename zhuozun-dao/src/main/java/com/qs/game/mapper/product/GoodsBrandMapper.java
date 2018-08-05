package com.qs.game.mapper.product;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.product.GoodsBrand;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsBrandMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsBrand record);

    int insertSelective(GoodsBrand record);

    GoodsBrand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsBrand record);

    int updateByPrimaryKey(GoodsBrand record);
}