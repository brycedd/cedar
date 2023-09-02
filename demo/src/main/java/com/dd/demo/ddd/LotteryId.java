package com.dd.demo.ddd;

/**
 * @author Bryce_dd 2023/9/2 00:16
 */
public class LotteryId implements Identifier {
    private static final long serialVersionUID = 6158104721124522711L;

    private final int id;

    public LotteryId(int id) {
        this.id = id;
    }
}
