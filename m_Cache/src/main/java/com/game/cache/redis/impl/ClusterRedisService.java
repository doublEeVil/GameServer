package com.game.cache.redis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.game.cache.redis.IRedisService;
import com.game.cache.redis.JCacheBase;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Tuple;

/**
 * 集群redis服务
 */
@Component("com.game.cache.redis.impl.ClusterRedisService")
@SuppressWarnings("rawtypes")
public class ClusterRedisService extends JCacheBase implements IRedisService {
    public int getTableID(Class c) {
        long id = this.incr(c.getName());
        if (id > 1990000000) { // 提前警告
            System.out.println("警告,id快满了! Classname:" + c.getName());
        }
        if (id == 2000000000) { // 到了20亿,把id重新归0
            this.set(c.getName(), "0");
        }
        return (int) id;
    }

    @Override
    public boolean existName(Class c, String value) {
        boolean rs = this.sismember(c.getName() + ":name", value);
        return rs;
    }

    @Override
    public void deleteName(Class c, String... value) {
        this.srem(c.getName() + ":name", value);
    }

    @Override
    public void addName(Class c, String... value) {
        this.sadd(c.getName() + ":name", value);
    }

    @Override
    public void deleteName(Class c) {
        this.delKeys(c.getName() + ":name");
    }

    @Override
    public void addSet(String key, String value) {
        this.sadd(key, value);
    }

    @Override
    public void deleteSet(String key, String value) {
        this.srem(key, value);
    }

    @Override
    public List<String> getAllSet(String key) {
        return new ArrayList<String>(this.smembers(key));
    }

    @Override
    public void addSortedSet(String key, double score, String member) {
        this.zadd(key, score, member);
    }

    @Override
    public void addSortedSet(String key, Map<String, Double> scoreMembers) {
        this.zadd(key, scoreMembers);
    }

    @Override
    public Set<Tuple> getSortedSet(String key, long start, long end) {
        return this.zrevrangeWithScores(key, start, end);
    }

    @Override
    public long delSortedSet(String key, long start, long end) {
        return this.zremrangebyrank(key, start, end);
    }

    @Override
    public long getSortedSetCount(String key) {
        return this.zcard(key);
    }

    @Override
    public long delSortedSet(String key, String... members) {
        return this.zrem(key, members);
    }

    @Override
    public int getSortedSetRevrank(String key, String member) {
        return this.zrevrank(key, member);
    }

    @Override
    public long delKeys(String key) {
        return this.delKey(key);
    }

    @Override
    public int getInt(String key) {
        String rs = get(key);
        if (rs != null && !"".equals(rs)) {
            return Integer.parseInt(rs);
        }
        return 0;
    }

    @Override
    public long getLong(String key) {
        String rs = get(key);
        if (rs != null && !"".equals(rs)) {
            return Long.parseLong(rs);
        }
        return 0;
    }

    @Override
    public void setVal(String key, String val) {
        set(key, val);
    }

    @Override
    public void addList(String key, String... val) {
        this.lpush(key, val);
    }

    @Override
    public void deleteList(String key, int start, int end) {
        this.ltrim(key, start, end);
    }

    @Override
    public List<String> getList(String key, long start, long end) {
        return this.lrange(key, start, end);
    }

    @Override
    public boolean containsSet(String key, String value) {
        return sismember(key, value);
    }

    @Override
    public long getSetCount(String key) {
        return this.scard(key);
    }

    @Override
    public double score(String key, String member) {
        return this.zscore(key, member);
    }

    @Override
    public Object eval(String script, List<String> keys, List<String> args) {
        return super.eval(script, keys, args);
    }

    @Override
    public void addHash(String key, String field, String value) {
        super.hset(key, field, value);
    }

    @Override
    public void delHash(String key, String... fields) {
        super.hrem(key, fields);
    }

    @Override
    public void addHash(String key, Map<String, String> hash) {
        super.hmset(key, hash);
    }

    @Override
    public String getHashValue(String key, String field) {
        return super.hget(key, field);
    }

    @Override
    public List<String> getHashValues(String key, String... fields) {
        return super.hmget(key, fields);
    }

    @Override
    public Map<String, String> getHashValues(String key) {
        return super.hgetAll(key);
    }

    @Override
    public boolean existsAndAdd(Class c, String name) {
        String script = "local result = redis.call('sismember', KEYS[1],ARGV[1]); " + "if (result == 0) then " + "redis.call('sadd', KEYS[1], ARGV[1]); " + "return 0; " + "end; " + "if (result == 1) then " + "return 1; " + "end;";
        List<String> keys = new ArrayList<String>();
        keys.add(c.getName() + ":name");
        List<String> params = new ArrayList<String>();
        params.add(name);
        long result = (long) this.eval(script, keys, params);
        return result == 1 ? true : false;
    }

    @Override
    public String getValue(String key) {
        return this.get(key);
    }

    @Override
    public double incrby(String key, double score, String member) {
        return super.incrby(key, score, member);
    }
}
