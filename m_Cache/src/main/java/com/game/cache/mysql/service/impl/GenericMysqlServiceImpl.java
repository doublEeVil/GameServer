package com.game.cache.mysql.service.impl;

import com.game.cache.mysql.BaseEntity;
import com.game.cache.mysql.BaseIdAutoIncEntity;
import com.game.cache.mysql.dao.GenericDao;
import com.game.cache.mysql.service.GenericMySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("com.game.cache.mysql.service.impl.GenericMysqlServiceImpl")
@Transactional
public class GenericMysqlServiceImpl implements GenericMySqlService {
    @Autowired
    private GenericDao genericDao;

    @Override
    public void save(BaseIdAutoIncEntity t) {
        genericDao.save(t);
    }

    @Override
    public void update(BaseIdAutoIncEntity t) {
        genericDao.update(t);
    }

    @Override
    public void delete(BaseIdAutoIncEntity t) {
        genericDao.delete(t);
    }

    public void save(BaseEntity t) {
        genericDao.save(t);
        //GenericDao.flush();
    }

    public <T> T get(Class<T> clazz, int id) {
        return genericDao.get(clazz, id);
    }

    public <T> List<T> getAll(Class<T> clazz) {
        return genericDao.getAll(clazz);
    }

    @Override
    public <T> List<T> getAllByHql(String hql, Object... params) {
        return genericDao.getAllByHql(hql, params);
    }

    public <T> T getByHql(String hql, Object ... params) {
        return genericDao.getByHql(hql, params);
    }

    public void update(BaseEntity t) {
        genericDao.update(t);
    }

    public void delete(BaseEntity t) {
        genericDao.delete(t);
    }

    @Override
    public void flush() {
        genericDao.flush();
    }
}
