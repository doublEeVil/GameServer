package com.game.cache.redis;

import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 集群
 */
public interface IRedisService {
    /**
     * 表id,
     *
     * @param c
     *            bean的类
     * @return
     */
    public int getTableID(Class c);

    /**
     * 是否存在值,这里传进来的value 当成set的value 并且保存不存在的值到 set里面
     *
     * @param
     * @param value
     * @return
     */
    public boolean existName(Class c, String value);

    /**
     * 删除这个值,这里传进来的value, 当成set的value
     *
     * @param c
     * @param value
     */
    public void deleteName(Class c, String... value);

    /**
     * 增加值
     */
    public void addName(Class c, String... value);

    /**
     * 删除名称的key的全部值
     */
    public void deleteName(Class c);

    /**
     * 增加值到set
     */
    public void addSet(String key, String value);

    /**
     * 删除值到set
     */
    public void deleteSet(String key, String value);

    /**
     * 获取所有的set
     */
    public List<String> getAllSet(String key);

    /**
     * 增加值到SortedSet
     */
    public void addSortedSet(String key, double score, String member);

    /**
     * 增加值到SortedSet
     */
    public void addSortedSet(String key, Map<String, Double> scoreMembers);

    /**
     * 获取sortedSet的值,从大到小的(end=-1取所有)
     *
     * @param start
     *            从0开始，
     * @param end
     *            -1 为最后一个
     */
    public Set<Tuple> getSortedSet(String key, long start, long end);

    /**
     * 删除 指定范围sortedSet的值 升序( end负数表示倒数.-1 表示最后一个成员， -2 表示倒数第二个成员，以此类推)
     */
    public long delSortedSet(String key, long start, long end);

    /**
     * 指定成员的排名,从大到小、
     *
     * @return -1 为没有排名
     */
    public int getSortedSetRevrank(String key, String member);

    /**
     * 删除 sortedSet的值
     *
     * @param key
     * @param memebers
     *            要删除的成员
     */
    public long delSortedSet(String key, String... members);

    /**
     * 获取有序集合的成员个数
     */
    public long getSortedSetCount(String key);

    /**
     * 删除key
     */
    public long delKeys(String key);

    /**
     * 获取整数值
     *
     * @param key
     * @return
     */
    public int getInt(String key);

    /**
     * 获取长整形值
     *
     * @param key
     * @return
     */
    public long getLong(String key);

    public void setVal(String key, String val);

    /**
     * 添加到list
     *
     * @param key
     * @param val
     */
    public void addList(String key, String... val);

    /**
     * 移除下标以外的数据
     *
     * @param key
     * @param start
     * @param end
     */
    public void deleteList(String key, int start, int end);

    /**
     * 取list
     *
     * @param key
     * @param start
     *            从0开始
     * @param end
     *            -1表示到结束
     * @return
     */
    public List<String> getList(String key, long start, long end);

    /**
     * 给定的值是否存在集合成员里
     *
     * @param key
     * @param value
     *            指定的值
     * @return
     */
    public boolean containsSet(String key, String value);

    /**
     * 获取指定集合成员个数
     *
     * @param key
     * @return
     */
    public long getSetCount(String key);

    public double score(String key, String member);

    /**
     * 增加一个hash值
     *
     * @param key
     * @param hashKey
     * @param hashValue
     */
    public void addHash(String key, String hashKey, String hashValue);

    /**
     * 删除hash值
     *
     * @param key
     * @param fields
     */
    public void delHash(String key, String... fields);

    /**
     * 增加一组hash值
     *
     * @param key
     * @param hash
     */
    public void addHash(String key, Map<String, String> hash);

    /**
     * 获取一个hash值
     *
     * @param key
     * @param hashKey
     * @return
     */
    public String getHashValue(String key, String hashKey);

    /**
     * 获取一组hash值
     *
     * @param key
     * @param hashKeys
     * @return
     */
    public List<String> getHashValues(String key, String... hashKeys);

    /**
     * 获取整hash
     *
     * @param key
     * @return
     */
    public Map<String, String> getHashValues(String key);

    /**
     * 执行lua脚本
     *
     * @param script
     *            脚本
     * @param keys
     *            key
     * @param args
     *            参数
     * @return
     * @author liumin
     */
    public Object eval(String script, List<String> keys, List<String> args);

    /**
     * 检测名字是否存在并新增(原子操作,分布式调用)
     *
     * @param c
     * @param name
     * @return 不存在则返回false 存在返回ture
     * @author liumin
     */
    public boolean existsAndAdd(Class c, String name);

    /**
     * 根据key获取value
     *
     * @param key
     * @return
     */
    public String getValue(String key);

    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 increment
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public double incrby(String key, double score, String member);
}
