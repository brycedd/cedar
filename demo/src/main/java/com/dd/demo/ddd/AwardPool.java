package com.dd.demo.ddd;

import java.util.List;

/**
 * @author Bryce_dd 2023/9/2 00:25
 */
public class AwardPool implements Entity<DString>{
    private DString id;
    private Constraint constraint;
    private List<Award> awards;// 奖池中包含的奖品

    public boolean match(PoolStrategy strategy) {

    }
}
