package database.connection;

import database.UserType;
import database.connection.exceptions.FailedLoginException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

/**
 * It is a singleton.
 */
public class SessionManager {
    private static final SessionManager instance = new SessionManager();
    private Session currentSession;
    private SessionFactoryManager sessionFactoryManager;

    private SessionManager() {
        sessionFactoryManager = new SessionFactoryManager();
        sessionFactoryManager.buildSessionFactory("login", "login");
        currentSession = sessionFactoryManager.getSessionFactory().openSession();
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public void openSessionForUser(String login, String password) throws FailedLoginException {
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

        Query<UserType> userTypeQuery  = currentSession.
                createQuery("SELECT type FROM UsersEntity WHERE login = (:login)", UserType.class)
                .setParameter("login", login);

        UserType userType = userTypeQuery.getSingleResult();

        switch (userType) {
            case store_keeper: {
                sessionFactoryManager.buildSessionFactory("store_keeper", "store_keeper");
                break;
            }
            case store_manager: {
                sessionFactoryManager.buildSessionFactory("store_manager", "store_manager");
                break;
            }
            case salesman: {
                sessionFactoryManager.buildSessionFactory("salesman", "salesman");
                break;
            }
            case admin: {
                sessionFactoryManager.buildSessionFactory("admin", "admin");
            }
        }

        currentSession = sessionFactoryManager.getSessionFactory().openSession();
    }
}
