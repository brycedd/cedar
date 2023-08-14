package com.dd.demo.demo.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.server.ServerCnxn;
import org.apache.zookeeper.server.auth.AuthenticationProvider;

/**
 * @author Bryce_dd 2023/8/12 15:29
 */
public class ZkApiDemo implements AuthenticationProvider {
    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public KeeperException.Code handleAuthentication(ServerCnxn serverCnxn, byte[] bytes) {
        return null;
    }

    @Override
    public boolean matches(String s, String s1) {
        return false;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public boolean isValid(String s) {
        return false;
    }
}
