package com.dd.common.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PmsProductFullReduction implements Serializable {
    private Long id;

    private Long productId;

    private BigDecimal fullPrice;

    private BigDecimal reducePrice;

    private static final long serialVersionUID = 1L;
}