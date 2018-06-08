package by.prostrmk.clouddrive.dao;

import by.prostrmk.clouddrive.model.entity.IEntity;

import java.util.List;

public interface Dao {

    IEntity getById(Long id, Class clazz);
    IEntity getByStringParamUnique(String paramName, String paramValue, Class clazz);
    List getByStringParamList(String paramName, String paramValue, Class clazz);
    List getLatest(String tableName, int count);
    List getAll(String param, Class clazz);
    void saveEntity(IEntity entity);
    void deleteEntity(IEntity entity);
    void updateEntity(IEntity entity);


}
