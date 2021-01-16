package database.connection;

import database.UserType;
import database.connection.exceptions.FailedLoginException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

/**
 * It is a singleton.
 */
public class SessionManager {
    private static final SessionManager instance = new SessionManager();
    private Session currentSession;
    private String sessionsOwner;
    private final SessionFactoryBuilder sessionFactoryBuilder;
    private SessionFactory sessionFactory;

    private SessionManager() {
        sessionFactoryBuilder = new SessionFactoryBuilder();
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public UserType openSessionForUser(String login, String password) throws FailedLoginException {
        sessionFactory = sessionFactoryBuilder.buildSessionFactory("login", "login");
        currentSession = openSession();

        StoredProcedureQuery procedureQuery = currentSession.createStoredProcedureQuery("user_exists").
                registerStoredProcedureParameter("login", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("password", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("result", Boolean.class, ParameterMode.OUT)
                .setParameter("login", login).setParameter("password", password);
        procedureQuery.execute();
        Boolean userExists = (Boolean) procedureQuery.getOutputParameterValue("result");

        if(!userExists) {
            throw new FailedLoginException();
        }

        sessionsOwner = login;

        Query<UserType> userTypeQuery  = currentSession.
                createQuery("SELECT type FROM UsersEntity WHERE login = (:login)", UserType.class)
                .setParameter("login", login);

        UserType userType = userTypeQuery.getSingleResult();
        closeSession();

        switch (userType) {
            case store_keeper: {
                sessionFactory = sessionFactoryBuilder.buildSessionFactory("store_keeper", "store_keeper");
                break;
            }
            case store_manager: {
                sessionFactory = sessionFactoryBuilder.buildSessionFactory("store_manager", "store_manager");
                break;
            }
            case salesman: {
                sessionFactory = sessionFactoryBuilder.buildSessionFactory("salesman", "salesman");
                break;
            }
            case admin: {
                sessionFactory = sessionFactoryBuilder.buildSessionFactory("admin", "admin");
            }
        }

        return userType;
    }

    public Session openSession() {
        if (currentSession != null) {
            closeSession();
        }
        currentSession = sessionFactory.openSession();
        return currentSession;
    }

    public void closeSession() {
        currentSession.close();
        currentSession = null;
    }

    public String getSessionsOwner() {
        return sessionsOwner;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public String getSessionFactoryOwner() {
        return sessionFactoryBuilder.getSessionFactoryOwner();
    }
}
