package com.dd.common.product.domain;

import com.dd.common.model.PmsProduct;
import com.dd.common.model.PmsProductAttribute;
import com.dd.common.model.PmsSkuStock;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.List;

/**
 * 购物车中选择规格的商品信息
 * Created by macro on 2018/8/2.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CartProduct extends PmsProduct {
    private List<PmsProductAttribute> productAttributeList;
    private List<PmsSkuStock> skuStockList;
}
