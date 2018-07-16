package com.game.cache.mysql.dao;

import com.game.cache.mysql.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author wangye
 * @version 2.2
 *          <p>
 *          Dao通用接口
 */

public interface GenericDao {
    Session openSession();

    Session getSession();
    /**
     * <保存实体>
     * <完整保存实体>
     *
     * @param t 实体参数
     */
    void save(Serializable t);

    SessionFactory getSessionFactory();
    /**
     * <保存或者更新实体>
     *
     * @param t 实体
     */
    void saveOrUpdate(Serializable t);

    /**
     * <load>
     * <加载实体的load方法>
     *
     * @param id 实体的id
     * @return 查询出来的实体
     */
    <T> T load(Class<T> clazz, Serializable id);

    /**
     * <get>
     * <查找的get方法>
     *
     * @param id 实体的id
     * @return 查询出来的实体
     */
     <T> T get(Class<T> clazz, Serializable id);


    /**
     *
     * @param clazz
     * @param <T>
     * @return
     */
     <T> List<T> getAll(Class<T> clazz);

    /**
     * 根据hql查找唯一
     * @param hql
     * @param params
     * @param <T>
     * @return
     */
     <T> T getByHql(String hql, Object ... params);

    /**
     *
     * @param hql
     * @param params
     * @param <T>
     * @return
     */
     <T> List<T> getAllByHql(String hql, Object ... params);

    /**
     * <update>
     *
     * @param t 实体
     */
    void update(Serializable t);

    /**
     * 刷新写入
     */
    void flush();

    /**
     * <contains>
     * <查找是否有该实例>
     *
     * @param t 实体
     * @return 是否包含
     */
    boolean contains(Serializable t);

    /**
     * <delete>
     * <删除表中的t数据>
     *
     * @param t 实体
     */
    void delete(Serializable t);
//
//    /**
//     * <根据ID删除数据>
//     *
//     * @param Id 实体id
//     * @return 是否删除成功
//     */
//    boolean deleteById(ID Id);
//
//    boolean deleteByPlayerId(ID id);
//
//    /**
//     * <删除所有>
//     *
//     * @param entities 实体的Collection集合
//     */
//    void deleteAll(Collection<T> entities);
//
//
//    /**
//     *
//     * @param t
//     */
//    void merge(T t);
//    /**
//     * <执行Hql语句>
//     *
//     * @param hqlString hql
//     * @param values    不定参数数组
//     */
//    void queryHql(String hqlString, Object... values);
//
//    /**
//     * <执行Sql语句>
//     *
//     * @param sqlString sql
//     * @param values    不定参数数组
//     */
//    void querySql(String sqlString, Object... values);
//
//    /**
//     * <根据HQL语句查找唯一实体>
//     *
//     * @param hqlString HQL语句
//     * @param values    不定参数的Object数组
//     * @return 查询实体
//     */
//    T getByHQL(String hqlString, Object... values);
//
//    /**
//     * <根据SQL语句查找唯一实体>
//     *
//     * @param sqlString SQL语句
//     * @param values    不定参数的Object数组
//     * @return 查询实体
//     */
//    T getBySQL(String sqlString, Object... values);
//
//    /**
//     * <根据HQL语句，得到对应的list>
//     *
//     * @param hqlString HQL语句
//     * @param values    不定参数的Object数组
//     * @return 查询多个实体的List集合
//     */
//    List<T> getListByHQL(String hqlString, Object... values);
//
//    List<T> getListByHQL2(String hqlString, Object... values);
//    /**
//     * <根据SQL语句，得到对应的list>
//     *
//     * @param sqlString HQL语句
//     * @param values    不定参数的Object数组
//     * @return 查询多个实体的List集合
//     */
//    List<T> getListBySQL(String sqlString, Object... values);
//
//    /**
//     * <根据HQL得到记录数>
//     *
//     * @param hql    HQL语句
//     * @param values 不定参数的Object数组
//     * @return 记录总数
//     */
//    Long countByHql(String hql, Object... values);

}