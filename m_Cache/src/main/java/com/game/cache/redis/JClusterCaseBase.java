package com.game.cache.redis;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisException;
/**
 * 集群 集成一般jedis操作 规则：保持jedis返回值
 * 
 * @author jian_xie
 */
public abstract class JClusterCaseBase {
    /**
     * 失败日志logger，用于定期del指定的key
     */
    public static Logger logger_failure = Logger.getLogger("logger_redis_failure");
    @Resource
    private JedisCluster jedisCluster;

    private JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    /**
     * 设置有效时间
     * 
     * @param key
     * @param seconds
     */
    public Long expire(String key, int seconds) {
        Long time = 0l;
        try {
            time = getJedisCluster().expire(key, seconds);
        } catch (JedisException e) {
            logger_failure.error("failed: expire,key:" + key + ",cacheSeconds:" + seconds, e);
            throw e;
        }
        return time;
    }

    ///////////////////////// String////////////////////////////////
    /**
     * 自增值
     * 
     * @param key
     */
    public long incr(String key) {
        try {
            return getJedisCluster().incr(key);
        } catch (JedisException e) {
            logger_failure.error("failed: incr,key:" + key, e);
            throw e;
        }
    }

    public void set(String key, String value) {
        try {
            getJedisCluster().set(key, value);
        } catch (JedisException e) {
            logger_failure.error("failed: set,key:" + key + ",value" + value, e);
            throw e;
        }
    }

    public String get(String key) {
        try {
            return getJedisCluster().get(key);
        } catch (JedisException e) {
            logger_failure.error("failed: get,key:" + key, e);
            throw e;
        }
    }

    /**
     * 获得旧值，设置新值
     * 
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key, String value) {
        try {
            return getJedisCluster().getSet(key, value);
        } catch (JedisException e) {
            logger_failure.error("failed: getSet,key:" + key + ",value" + value, e);
            throw e;
        }
    }

    /**
     * 将key的值设为value, 当且仅当key不存在
     * 
     * @return 1、成功，0、失败
     */
    public long setnx(String key, String value) {
        try {
            return getJedisCluster().setnx(key, value);
        } catch (JedisException e) {
            logger_failure.error("failed: setnx,key:" + key + ",value" + value, e);
            throw e;
        }
    }

    //////////////////////////// set////////////////////////////////////
    /**
     * 添加members
     * 
     * @param key
     * @param members
     * @return
     */
    public long sadd(String key, String... members) {
        try {
            return getJedisCluster().sadd(key, members);
        } catch (JedisException e) {
            logger_failure.error("failed: sadd,key:" + key + ",member:" + members, e);
            throw e;
        }
    }

    /**
     * 是否是成员
     * 
     * @param key
     * @param member
     * @return
     */
    public boolean sismember(String key, String member) {
        try {
            return getJedisCluster().sismember(key, member);
        } catch (JedisException e) {
            logger_failure.error("failed: sismember,key:" + key + ",member:" + member, e);
            throw e;
        }
    }

    /**
     * 移除元素
     * 
     * @param key
     * @param members
     * @return
     */
    public long srem(String key, String... members) {
        try {
            return getJedisCluster().srem(key, members);
        } catch (JedisException e) {
            logger_failure.error("failed: srem,key:" + key + ",member:" + members, e);
            throw e;
        }
    }

    /**
     * 所有成员
     * 
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
        try {
            return getJedisCluster().smembers(key);
        } catch (JedisException e) {
            logger_failure.error("failed: smembers,key:" + key, e);
            throw e;
        }
    }

    /////////////////////////////// hashmap///////////////////////////////
    public long hset(String key, String field, String value) {
        try {
            return getJedisCluster().hset(key, field, value);
        } catch (JedisException e) {
            logger_failure.error("failed: hset,key:" + key + ",field:" + field + ",value" + value, e);
            throw e;
        }
    }

    public long hrem(String key, String... field) {
        try {
            return getJedisCluster().hdel(key, field);
        } catch (JedisException e) {
            logger_failure.error("failed: hset,key:" + key + ",field:" + field);
            throw e;
        }
    }

    public String hmset(String key, Map<String, String> hash) {
        try {
            return getJedisCluster().hmset(key, hash);
        } catch (JedisException e) {
            logger_failure.error("failed: hmset,key:" + key + ",hash:" + hash, e);
            throw e;
        }
    }

    public String hget(String key, String field) {
        try {
            return getJedisCluster().hget(key, field);
        } catch (JedisException e) {
            logger_failure.error("failed: hget,key:" + key + ",field:" + field, e);
            throw e;
        }
    }

    public Map<String, String> hgetAll(String key) {
        try {
            return getJedisCluster().hgetAll(key);
        } catch (JedisException e) {
            logger_failure.error("failed: hgetAll,key:" + key, e);
            throw e;
        }
    }

    public List<String> hmget(String key, String... fields) {
        try {
            return getJedisCluster().hmget(key, fields);
        } catch (JedisException e) {
            logger_failure.error("failed: hget,key:" + key + ",fields:" + fields, e);
            throw e;
        }
    }

    //////////////////////////////// List////////////////////////////////
    /**
     * 添加有序集合
     * 
     * @param key
     * @param fields
     * @return
     */
    public long lpush(String key, String... fields) {
        try {
            return getJedisCluster().lpush(key, fields);
        } catch (JedisException e) {
            logger_failure.error("failed: lpush,key:" + key + ",fields:" + fields, e);
            throw e;
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
        try {
            getJedisCluster().ltrim(key, start, end);
        } catch (JedisException e) {
            logger_failure.error("failed: ltrim,key:" + key + ",start:" + start + ",end:" + end, e);
            throw e;
        }
    }

    /**
     * 指定范围的结果 倒序 全部:start:0,end:-1
     * 
     * @param key
     * @return
     */
    public List<String> lrange(String key, long start, long end) {
        try {
            return getJedisCluster().lrange(key, start, end);
        } catch (JedisException e) {
            logger_failure.error("failed: lrange,key:" + key + ",start:" + start + ",end:" + end, e);
            throw e;
        }
    }

    //////////////////////////////// sortedset////////////////////////////////
    /**
     * 添加有序集合
     * 
     * @param key
     * @return
     */
    public long zadd(String key, double score, String member) {
        try {
            return getJedisCluster().zadd(key, score, member);
        } catch (JedisException e) {
            logger_failure.error("failed: zadd,key:" + key + ",score:" + score + ",member:" + member, e);
            throw e;
        }
    }

    /**
     * 添加有序集合
     */
    public long zadd(String key, Map<String, Double> scoreMembers) {
        try {
            if (scoreMembers.size() == 0) {
                return 0;
            }
            return getJedisCluster().zadd(key, scoreMembers);
        } catch (JedisException e) {
            logger_failure.error("failed: zadd,key:" + key + ",scoreMembers:" + scoreMembers, e);
            throw e;
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
        try {
            return getJedisCluster().zrangeByScore(key, min, max);
        } catch (JedisException e) {
            logger_failure.error("failed: zrangeByScore,key:" + key + ",min:" + min + ",max:" + max, e);
            throw e;
        }
    }

    /**
     * 指定范围的结果 倒序
     * 
     * @param key
     * @return
     */
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        try {
            return getJedisCluster().zrevrangeWithScores(key, start, end);
        } catch (JedisException e) {
            logger_failure.error("failed: zrevrange,key:" + key + ",start:" + start + ",end:" + end, e);
            throw e;
        }
    }

    /**
     * 指定范围的结果 升序
     * 
     * @param key
     * @return
     */
    public Set<String> zrange(String key, long start, long end) {
        try {
            return getJedisCluster().zrange(key, start, end);
        } catch (JedisException e) {
            logger_failure.error("failed: zrange,key:" + key + ",start:" + start + ",end:" + end, e);
            throw e;
        }
    }

    /**
     * 获取成员排名 升序
     * 
     * @param key
     * @return
     */
    public Long zrank(String key, String member) {
        try {
            return getJedisCluster().zrank(key, member);
        } catch (JedisException e) {
            logger_failure.error("failed: zrank,key:" + key + ",member:" + member);
            throw e;
        }
    }

    /**
     * 返回成员的排名
     * 
     * @param key
     * @return -1 为没有排名
     */
    public int zrevrank(String key, String member) {
        try {
            Long rank = getJedisCluster().zrevrank(key, member);
            return rank == null ? -1 : rank.intValue();
        } catch (JedisException e) {
            logger_failure.error("failed: zrevrange,key:" + key + ",member:" + member, e);
            throw e;
        }
    }

    /**
     * 返回成员的score值
     */
    public double zscore(String key, String member) {
        try {
            Double score = getJedisCluster().zscore(key, member);
            return score == null ? -1 : score;
        } catch (JedisException e) {
            logger_failure.error("failed: zscore,key:" + ",member:" + member, e);
            throw e;
        }
    }

    /**
     * 返回有序集合个数
     */
    public long zcount(String key, long min, long max) {
        try {
            return getJedisCluster().zcount(key, min, max);
        } catch (JedisException e) {
            logger_failure.error("failed: zcount,key:" + key);
            throw e;
        }
    }

    /**
     * 删除指定范围的结果 升 序
     * 
     * @param key
     * @return
     */
    public long zremrangebyrank(String key, long start, long end) {
        try {
            return getJedisCluster().zremrangeByRank(key, start, end);
        } catch (JedisException e) {
            logger_failure.error("failed: zrevrange,key:" + key + ",start:" + start + ",end:" + end, e);
            throw e;
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
        try {
            return getJedisCluster().zrem(key, members);
        } catch (JedisException e) {
            logger_failure.error("failed: zrem,key:" + key + ", strings" + members, e);
            throw e;
        }
    }

    /**
     * 有序集合数量
     * 
     * @param key
     * @return
     */
    public long zcard(String key) {
        try {
            return getJedisCluster().zcard(key);
        } catch (JedisException e) {
            logger_failure.error("failed: zcard,key:" + key, e);
            throw e;
        }
    }

    /**
     * 有序集合数量
     * 
     * @param key
     * @return
     */
    public long scard(String key) {
        try {
            return getJedisCluster().scard(key);
        } catch (JedisException e) {
            logger_failure.error("failed: zcard,key:" + key, e);
            throw e;
        }
    }

    ///////////////////////////// key////////////////////////////
    public boolean existKey(String key) {
        try {
            return getJedisCluster().exists(key);
        } catch (JedisException e) {
            logger_failure.error("failed: existKey,key:" + key, e);
            throw e;
        }
    }

    public long delKey(String key) {
        try {
            return getJedisCluster().del(key);
        } catch (JedisException e) {
            logger_failure.error("failed: delKey,key:" + key, e);
            throw e;
        }
    }

    ///////////////////////////// eval////////////////////////////
    public Object eval(String script, List<String> keys, List<String> args) {
        try {
            return getJedisCluster().eval(script, keys, args);
        } catch (JedisException e) {
            logger_failure.error("failed: eval,script:" + script, e);
            throw e;
        }
    }

    public double incrby(String key, double score, String member) {
        try {
            return getJedisCluster().zincrby(key, score, member);
        } catch (JedisException e) {
            logger_failure.error("failed: incrby,key:" + key + ",score : " + score + ".member : " + member, e);
            throw e;
        }
    }
}
