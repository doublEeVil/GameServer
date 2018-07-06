package com.game.cache.mysql.dao.impl;

import com.game.cache.mysql.BaseEntity;
import com.game.cache.mysql.dao.GenericDao;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by wangye on 2016/6/1.
 */
@Repository
public class GenericDaoImpl implements GenericDao {
    private static final Logger LOGGER = Logger
            .getLogger(GenericDaoImpl.class);
    @Autowired
    public SessionFactory sessionFactory;
    //protected Class<T> entityClass;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }


    @Override
    public Session openSession() {
        return this.sessionFactory.openSession();
    }

//    protected Class getEntityClass() {
//        if (entityClass == null) {
//            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        }
//        return entityClass;
//    }

    @Override
    public void save(Serializable t) {
        Session session = this.getSession();
        session.save(t);
//        ID id;
//        id = (ID) session.save(t);
//        //((BaseEntity)t).setIsUpdate(0);
//        return id;
    }

//    @Override
//    public void merge(Object t) {
//        this.getSession().merge(t);
//    }

    /**
     * <保存或者更新实体>
     *
     * @param t 实体
     * @see
     */
    @Override
    public void saveOrUpdate(Serializable t) {
        this.getSession().saveOrUpdate(t);
    }

    /**
     * <load>
     * <加载实体的load方法>
     *
     * @param id 实体的id
     * @return 查询出来的实体
     * @see
     */
    @Override
    public <T> T load(Class<T> clazz, Serializable id) {
        T load = this.getSession().load(clazz, id);
        return load;
    }

    /**
     * <get>
     * <查找的get方法>
     *
     * @param id 实体的id
     * @return 查询出来的实体
     * @see
     */
    @Override
    public <T> T get(Class<T> clazz, Serializable id) {
        Session session = this.getSession();
        //session.createSQLQuery("SET NAMES utf8mb4").executeUpdate();
        T load = session.get(clazz, id);
        return load;
    }

    /**
     * <update>
     *
     * @param t 实体
     * @see
     */
    @Override
    public void update(Serializable t) {
        Session session = this.getSession();
        // session.createSQLQuery("SET NAMES utf8mb4").executeUpdate();
        session.update(t);
        ((BaseEntity)t).setUpdateFlag(0);
        //LOGGER.debug(t.getClass().toString());
        //this.getSession().update(t);
    }

    /**
     *
     */
    @Override
    public void flush() {
        this.getSession().flush();
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        Session session = this.getSession();
        Query query = session.createQuery("from " + clazz.getName());
        return query.list();
    }

    @Override
    public boolean contains(BaseEntity t) {
        return this.getSession().contains(t);
    }

    @Override
    public void delete(BaseEntity t) {
        this.getSession().delete(t);
    }


//
//    /**
//     * <根据ID删除数据>
//     *
//     * @param Id 实体id
//     * @return 是否删除成功
//     * @see
//     */
//    @Override
//    public boolean deleteById(ID Id) {
//        T t = get(Id);
//        if (t == null) {
//            return false;
//        }
//        delete(t);
//        return true;
//    }
//
//    @Override
//    public boolean deleteByPlayerId(ID Id) {
//        List<T> ts = getSession().createQuery("from " + getEntityClass().getName() + " t where t.player.id=?").setParameter(0, Id).list();
//        if (ts.size() <= 0) {
//            return false;
//        }
//        for (T t : ts) {
//            delete(t);
//        }
//
//        return true;
//    }
//
//    /**
//     * <删除所有>
//     *
//     * @param entities 实体的Collection集合
//     * @see
//     */
//    @Override
//    public void deleteAll(Collection<T> entities) {
//        for (Object entity : entities) {
//            this.getSession().delete(entity);
//        }
//    }
//
//
//    /**
//     * <执行Sql语句>
//     *
//     * @param sqlString sql
//     * @param values    不定参数数组
//     * @see
//     */
//    @Override
//    public void querySql(String sqlString, Object... values) {
//        Query query = this.getSession().createSQLQuery(sqlString);
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//                query.setParameter(i, values[i]);
//            }
//        }
//        query.executeUpdate();
//    }
//
//    /**
//     * <执行Hql语句>
//     *
//     * @param hqlString hql
//     * @param values    不定参数数组
//     * @see
//     */
//    @Override
//    public void queryHql(String hqlString, Object... values) {
//        Session session = this.getSession();
//        Query query = session.createQuery(hqlString);
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//                query.setParameter(i, values[i]);
//            }
//        }
//        query.executeUpdate();
//    }
//
//    /**
//     * <根据HQL语句查找唯一实体>
//     *
//     * @param hqlString HQL语句
//     * @param values    不定参数的Object数组
//     * @return 查询实体
//     * @see
//     */
//    @Override
//    public T getByHQL(String hqlString, Object... values) {
//        Query query = this.getSession().createQuery(hqlString);
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//                query.setParameter(i, values[i]);
//            }
//        }
//        return (T) query.uniqueResult();
//    }
//
//    /**
//     * <根据SQL语句查找唯一实体>
//     *
//     * @param sqlString SQL语句
//     * @param values    不定参数的Object数组
//     * @return 查询实体
//     * @see
//     */
//    @Override
//    public T getBySQL(String sqlString, Object... values) {
//        Query query = this.getSession().createSQLQuery(sqlString).addEntity(this.getEntityClass());
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//                query.setParameter(i, values[i]);
//            }
//        }
//        return (T) query.uniqueResult();
//    }
//
//    /**
//     * <根据HQL语句，得到对应的list>
//     *
//     * @param hqlString HQL语句
//     * @param values    不定参数的Object数组
//     * @return 查询多个实体的List集合
//     * @see
//     */
//    @Override
//    public List<T> getListByHQL(String hqlString, Object... values) {
//        Session session = this.getSession();
//        Query query = session.createQuery(hqlString);
//
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//                query.setParameter(i, values[i]);
//            }
//        }
//        return query.list();
//    }
//
//    @Override
//    public List<T> getListByHQL2(String hqlString, Object... values) {
//        Session session = this.openSession();
//        List<T> list;
//        try {
//            Query query = session.createQuery(hqlString);
//
//            if (values != null) {
//                for (int i = 0; i < values.length; i++) {
//                    query.setParameter(i, values[i]);
//                }
//            }
//            list = query.list();
//        } finally {
//            session.close();
//        }
//
//        return list;
//    }
//
//    /**
//     * <根据SQL语句，得到对应的list>
//     *
//     * @param sqlString HQL语句
//     * @param values    不定参数的Object数组
//     * @return 查询多个实体的List集合
//     * @see
//     */
//    @Override
//    public List<T> getListBySQL(String sqlString, Object... values) {
//        Query query = this.getSession().createSQLQuery(sqlString);
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//                query.setParameter(i, values[i]);
//            }
//        }
//        return query.list();
//    }
//
//    /**
//     * <根据HQL得到记录数>
//     *
//     * @param hql    HQL语句
//     * @param values 不定参数的Object数组
//     * @return 记录总数
//     * @see
//     */
//    @Override
//    public Long countByHql(String hql, Object... values) {
//        Query query = this.getSession().createQuery(hql);
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//                query.setParameter(i, values[i]);
//            }
//        }
//        return (Long) query.uniqueResult();
//    }


}

