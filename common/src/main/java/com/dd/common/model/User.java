package com.dd.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Bryce_dd 2023/7/20 15:18
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -2972518481752668123L;

    private Long id;
    private String name;
    private byte[] data;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
    }
}
