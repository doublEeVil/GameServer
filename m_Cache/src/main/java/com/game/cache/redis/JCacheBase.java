package com.game.cache.redis;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisException;
/**
 * jedis 工具, 集成一般jedis操作 规则：保持jedis返回值
 * 
 * @author jian_xie
 */
public abstract class JCacheBase {
    /**
     * 默认使用0库，如果严格区分库，需要在调用方法前设置dbIndex, 因为是bean实例，本次设置dbIndex会保持到下次dbIndex改变
     */
    private int          dbIndex        = 0;
    /**
     * 失败日志logger，用于定期del指定的key
     */
    public static Logger logger_failure = Logger.getLogger("logger_redis_failure");
    @Resource
    protected JedisPool  jedisPool;

    @SuppressWarnings("deprecation")
    protected Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (JedisException e) {
            logger_failure.error("failed:jedisPool getResource.", e);
            if (jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            throw e;
        }
        return jedis;
    }

    /**
     * 回收jedis
     * 
     * @param jedis
     */
    protected void release(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 设置有效时间
     * 
     * @param key
     * @param cacheSeconds
     */
    public Long expire(String key, int cacheSeconds) {
        Jedis jedis = null;
        Long time = Long.valueOf(0);
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            time = jedis.expire(key, cacheSeconds);
        } catch (JedisException e) {
            logger_failure.error("failed: expire,key:" + key + ",cacheSeconds:" + cacheSeconds, e);
            throw e;
        } finally {
            release(jedis);
        }
        return time;
    }

    // /////////////////////String////////////////////
    /**
     * 自增值
     * 
     * @param key
     * @return
     * @throws Exception
     */
    protected long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            return jedis.incr(key);
        } catch (JedisException e) {
            logger_failure.error("failed: incr,key:" + key, e);
            throw e;
        } finally {
            release(jedis);
        }
    }
    // /////////////////////list////////////////////

    // /////////////////////set////////////////////
    /**
     * 添加members
     * 
     * @param key
     * @param members
     * @return
     */
    protected long sadd(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            return jedis.sadd(key, members);
        } catch (JedisException e) {
            logger_failure.error("failed: sadd,key:" + key + ",member:" + members, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 是否是成员
     * 
     * @param key
     * @param member
     * @return
     */
    protected boolean sismember(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            return jedis.sismember(key, member);
        } catch (JedisException e) {
            logger_failure.error("failed: sismember,key:" + key + ",member:" + member, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 移除元素
     * 
     * @param key
     * @param members
     * @return
     */
    protected long srem(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            return jedis.srem(key, members);
        } catch (JedisException e) {
            logger_failure.error("failed: srem,key:" + key + ",member:" + members, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 所有成员
     * 
     * @param key
     * @return
     */
    protected Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            return jedis.smembers(key);
        } catch (JedisException e) {
            logger_failure.error("failed: smembers,key:" + key, e);
            throw e;
        } finally {
            release(jedis);
        }
    }
    // /////////////////////hashmap////////////////////

    // /////////////////////sortedset////////////////////
    /**
     * 添加有序集合
     * 
     * @param key
     * @return
     */
    public long zadd(String key, double score, String member) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            return jedis.zadd(key, score, member);
        } catch (JedisException e) {
            logger_failure.error("failed: zadd,key:" + key + ",score:" + score + ",member:" + member, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 指定范围的结果 倒序
     * 
     * @param key
     * @return
     */
    protected Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            return jedis.zrevrangeWithScores(key, start, end);
        } catch (JedisException e) {
            logger_failure.error("failed: zrevrange,key:" + key + ",start:" + start + ",end:" + end, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 返回成员的排名
     * 
     * @param key
     * @return -1 为没有排名
     */
    protected int zrevrank(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            Long rank = jedis.zrevrank(key, member);
            return rank == null ? -1 : rank.intValue();
        } catch (JedisException e) {
            logger_failure.error("failed: zrevrange,String:" + member, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 删除指定范围的结果 升 序
     * 
     * @param key
     * @return
     */
    protected long zremrangebyrank(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            return jedis.zremrangeByRank(key, start, end);
        } catch (JedisException e) {
            logger_failure.error("failed: zrevrange,key:" + key + ",start:" + start + ",end:" + end, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 删除成员
     * 
     * @param key
     * @param members
     * @return
     */
    public long zrem(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            return jedis.zrem(key, members);
        } catch (JedisException e) {
            logger_failure.error("failed: zrem,key:" + key + ", strings" + members, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 数量
     * 
     * @param key
     * @return
     */
    protected long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            return jedis.zcard(key);
        } catch (JedisException e) {
            logger_failure.error("failed: zcard,key:" + key, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /***************************** key *********************************/
    public boolean existKey(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            return jedis.exists(key);
        } catch (JedisException e) {
            logger_failure.error("failed: existKey,key:" + key, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    protected long delKey(String... key) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            return jedis.del(key);
        } catch (JedisException e) {
            logger_failure.error("failed: delKey,key:" + key, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    protected int getDbIndex() {
        return dbIndex;
    }

    protected void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }
    //// *************************************************************
    //// 以下是自定义添加方法,主要目的是与ReidsService同步，而不是产生大量的代码修改
    //// *************************************************************

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } catch (JedisException e) {
            logger_failure.error("failed: set,key:" + key + ",value" + value, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 添加有序集合
     */
    public long zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (scoreMembers.size() == 0) {
                return 0;
            }
            return jedis.zadd(key, scoreMembers);
        } catch (JedisException e) {
            logger_failure.error("failed: zadd,key:" + key + ",scoreMembers:" + scoreMembers, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } catch (JedisException e) {
            logger_failure.error("failed: get,key:" + key, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 添加有序集合
     * 
     * @param key
     * @param fields
     * @return
     */
    public long lpush(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.lpush(key, fields);
        } catch (JedisException e) {
            logger_failure.error("failed: lpush,key:" + key + ",fields:" + fields, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 删除下标以外的数据
     * 
     * @param key
     * @param start
     * @param end
     */
    public void ltrim(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.ltrim(key, start, end);
        } catch (JedisException e) {
            logger_failure.error("failed: ltrim,key:" + key + ",start:" + start + ",end:" + end, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 指定范围的结果 倒序 全部:start:0,end:-1
     * 
     * @param key
     * @return
     */
    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.lrange(key, start, end);
        } catch (JedisException e) {
            logger_failure.error("failed: lrange,key:" + key + ",start:" + start + ",end:" + end, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 有序集合数量
     * 
     * @param key
     * @return
     */
    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.scard(key);
        } catch (JedisException e) {
            logger_failure.error("failed: zcard,key:" + key, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 返回成员的score值
     */
    public double zscore(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Double score = jedis.zscore(key, member);
            return score == null ? -1 : score;
        } catch (JedisException e) {
            logger_failure.error("failed: zscore,key:" + ",member:" + member, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    public Object eval(String script, List<String> keys, List<String> args) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.eval(script, keys, args);
        } catch (JedisException e) {
            logger_failure.error("failed: eval,script:" + script, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    public long hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hset(key, field, value);
        } catch (JedisException e) {
            logger_failure.error("failed: hset,key:" + key + ",field:" + field + ",value" + value, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    public long hrem(String key, String... field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hdel(key, field);
        } catch (JedisException e) {
            logger_failure.error("failed: hset,key:" + key + ",field:" + field);
            throw e;
        } finally {
            release(jedis);
        }
    }

    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hmset(key, hash);
        } catch (JedisException e) {
            logger_failure.error("failed: hmset,key:" + key + ",hash:" + hash, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    public String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hget(key, field);
        } catch (JedisException e) {
            logger_failure.error("failed: hget,key:" + key + ",field:" + field, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    public double incrby(String key, double score, String member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zincrby(key, score, member);
        } catch (JedisException e) {
            logger_failure.error("failed: incrby,key:" + key + ",score : " + score + ".member : " + member, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    public List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hmget(key, fields);
        } catch (JedisException e) {
            logger_failure.error("failed: hget,key:" + key + ",fields:" + fields, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    public Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hgetAll(key);
        } catch (JedisException e) {
            logger_failure.error("failed: hgetAll,key:" + key, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 获取成员排名 升序
     * 
     * @param key
     * @return
     */
    public Long zrank(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zrank(key, member);
        } catch (JedisException e) {
            logger_failure.error("failed: zrank,key:" + key + ",member:" + member);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 返回有序集合个数
     */
    public long zcount(String key, long min, long max) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zcount(key, min, max);
        } catch (JedisException e) {
            logger_failure.error("failed: zcount,key:" + key);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 指定范围的结果 升序
     * 
     * @param key
     * @return
     */
    public Set<String> zrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zrange(key, start, end);
        } catch (JedisException e) {
            logger_failure.error("failed: zrange,key:" + key + ",start:" + start + ",end:" + end, e);
            throw e;
        } finally {
            release(jedis);
        }
    }

    /**
     * 指定范围的结果
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zrangeByScore(String key, long min, long max) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zrangeByScore(key, min, max);
        } catch (JedisException e) {
            logger_failure.error("failed: zrangeByScore,key:" + key + ",min:" + min + ",max:" + max, e);
            throw e;
        } finally {
            release(jedis);
        }
    }
}
