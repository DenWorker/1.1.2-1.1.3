package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.createSessionFactoryHibernate();
             Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("""
                    CREATE TABLE IF NOT EXISTS users (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        lastName VARCHAR(100) NOT NULL,
                        age TINYINT
                    );
                    """).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(UserDaoHibernateImpl.class.getName()).log(Level.SEVERE, "An error occurred", e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.createSessionFactoryHibernate();
             Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(UserDaoHibernateImpl.class.getName()).log(Level.SEVERE, "An error occurred", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.createSessionFactoryHibernate();
             Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction.commit();
            Logger.getLogger(UserDaoHibernateImpl.class.getName()).info(String.format("User с именем – %s %s добавлен в базу данных ", name, lastName));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(UserDaoHibernateImpl.class.getName()).log(Level.SEVERE, "An error occurred", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.createSessionFactoryHibernate();
             Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.delete(session.get(User.class, id));

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(UserDaoHibernateImpl.class.getName()).log(Level.SEVERE, "An error occurred", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (SessionFactory sessionFactory = Util.createSessionFactoryHibernate();
             Session session = sessionFactory.openSession()) {

            userList = session.createQuery("FROM User", User.class).getResultList();

        } catch (Exception e) {
            Logger.getLogger(UserDaoHibernateImpl.class.getName()).log(Level.SEVERE, "An error occurred", e);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.createSessionFactoryHibernate();
             Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(UserDaoHibernateImpl.class.getName()).log(Level.SEVERE, "An error occurred", e);
        }
    }
}
