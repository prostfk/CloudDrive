package by.prostrmk.clouddrive.dao;

import by.prostrmk.clouddrive.model.entity.IEntity;
import by.prostrmk.clouddrive.model.entity.News;
import by.prostrmk.clouddrive.model.entity.User;
import by.prostrmk.clouddrive.model.util.DataBaseWork;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DaoTest {

    private FileDao fileDao = new FileDao();

    @Test
    public void checkInsert(){
        User byId = (User)fileDao.getById((long) 1, User.class);
        User user = new User("roma","password");
        assertEquals(user.getUsername(), byId.getUsername());
    }

    @Test
    public void checkCountOfInserts(){
        List news = fileDao.getLatest("News", 2);
        assertEquals(2,news.size());
    }

    @Test
    public void testSearch(){
        List search = DataBaseWork.search("User", "username", "roma");
        assertEquals(2,search.size());
    }

}