package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        testUserDAO(new UserDaoJDBCImpl());
        testUserDAO(new UserDaoHibernateImpl());
    }

    public static void testUserDAO(UserDao userDao) {
        userDao.createUsersTable();

        userDao.saveUser("Мария", "Шереметьевская", (byte) 23);
        userDao.saveUser("Денис", "Иванов", (byte) 25);
        userDao.saveUser("Михаил", "Гальвас", (byte) 23);
        userDao.saveUser("Владик", "Зайцев", (byte) 24);

        System.out.println(userDao.getAllUsers());

        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
