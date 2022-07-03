package com.dd.common.util.lock;

/**
 * @author Bryce_dd 2022/3/20 15:19
 */
public class ScriptCreator {

    public ScriptCreator() {
    }

    /**
     * 设置锁标志
     * 如果存在锁，比较值是否和当前要设置的锁标志一致。一致则更新过期时间，返回：1
     * 如果没有锁标志，则设置锁标志，成功则返回：1
     * 否则返回：0
     * -- return 1：成功；0：失败
     * -- 说明： 1、这段lua 脚本并不实现分布式锁。
     * --      2、要求redis 版本不能低于2.6.12
     * <p>
     * -- 参数： key, value, expire
     * local currValue = redis.call('GET', KEYS[1])
     * if nil ~= currValue and ARGV[1] == currValue then
     * redis.call('PEXPIRE', KEYS[1], ARGV[2])
     * return '1'
     * end
     * <p>
     * local result = redis.call('SET', KEYS[1], ARGV[1], 'PX', ARGV[2], 'NX')
     * if nil ~= result  then
     * if type(result) == 'table' then
     * local rvalue = nil
     * for _,v in pairs(result) do rvalue = v end
     * if rvalue == 'OK' then
     * return 1
     * end
     * elseif type(result) == 'boolean' and result == true then
     * return  1
     * end
     * end
     * return 0
     */
    public static String lockScript() {

        return " local currValue = redis.call('GET', KEYS[1]) \n" +
                " if nil ~= currValue and ARGV[1] == currValue then \n" +
                " 	  redis.call('PEXPIRE', KEYS[1], ARGV[2]) \n" +
                " return '1' \n" +
                " end \n" +
                " local result = redis.call('SET', KEYS[1], ARGV[1], 'PX', ARGV[2], 'NX') \n" +
                " if nil ~= result  then \n" +
                " 	if type(result) == 'table' then \n" +
                " 		local rvalue = nil \n" +
                " 		for _,v in pairs(result) do rvalue = v end \n" +
                " 		if rvalue == 'OK' then \n" +
                " 			return '1' \n" +
                " 		end \n" +
                " 	elseif type(result) == 'boolean' and result == true then \n" +
                " 		return  '1' \n" +
                " 	end \n" +
                " end \n" +
                " return '0' \n";
    }

    /**
     * 更新锁
     * --return 1：成功；0：失败
     * <p>
     * 如果缓存中有指定的key，并且对应的值和指定的值相等，则重新设置key的过期时间
     * --key, value, expire
     * <p>
     * local value = redis.call('GET', KEYS[1]);
     * if nil ~= value and ARGV[1] == value then
     * return	redis.call('EXPIRE', KEYS[1], ARGV[2]);
     * end;
     * return 0;
     */
    public static String updateLockScript() {
        return " local value = redis.call('GET', KEYS[1]); \n" +
                " if nil ~= value and ARGV[1] == value then \n" +
                " 	 redis.call('PEXPIRE', KEYS[1], ARGV[2]); \n" +
                "  return '1'; \n" + //
                " end; \n" + //
                " return '0'; \n";
    }

    /**
     * return 1：成功；0：失败
     * 如果缓存中有指定的key，并且对应的值和指定的值相等，则删除该key
     * --key, value, expire
     * local value = redis.call('GET', KEYS[1]);
     * if nil ~= value and ARGV[1] == value then
     * redis.call('DEL', KEYS[1]);
     * return 1;
     * end;
     * if nil == value then
     * return 1;
     * end
     * return 0;
     */
    public static String unlockScript() {
        return " local value = redis.call('GET', KEYS[1]); \n" +
                " if nil ~= value and ARGV[1] == value then \n" +
                " 	redis.call('DEL', KEYS[1]); \n" +
                " 	return '1'; \n" +
                " end; \n" +
                " if nil == value then \n" +
                " 	return '1'; \n" +
                " end; \n" +
                " return '0'; \n";
    }
}
