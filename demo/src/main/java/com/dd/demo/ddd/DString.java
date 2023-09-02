package com.dd.demo.ddd;

/**
 * @author Bryce_dd 2023/9/2 00:35
 */
public class DString implements Identifier{
    private static final long serialVersionUID = -3785699423924758497L;

    private final String dStr;

    public DString(String dStr) {
        this.dStr = dStr;
    }
}
