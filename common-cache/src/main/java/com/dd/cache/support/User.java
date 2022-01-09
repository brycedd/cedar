package com.dd.cache.support;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Bryce_dd 2022/1/9 19:12
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 2470548122281778567L;

    private String name;
    private Integer age;
}
