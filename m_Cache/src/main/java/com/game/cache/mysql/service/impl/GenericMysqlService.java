package com.game.cache.mysql.service.impl;

import com.game.cache.mysql.BaseEntity;
import com.game.cache.mysql.dao.GenericDao;
import com.game.cache.mysql.service.IGenericMySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class GenericMysqlService implements IGenericMySqlService {
    @Autowired
    private  GenericDao genericDao;

    public void save(BaseEntity t) {
        genericDao.save(t);
        //genericDao.flush();
    }

    public <T> T get(Class<T> clazz, Long id) {
        return genericDao.get(clazz, id);
    }

    public <T> List<T> getAll(Class<T> clazz) {
        return genericDao.getAll(clazz);
    }

    public void update(BaseEntity t) {
        genericDao.update(t);
    }

    public void delete(BaseEntity t) {
        genericDao.delete(t);
    }
}
