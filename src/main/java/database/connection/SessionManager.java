package database.connection;

import database.UserType;
import database.connection.exceptions.FailedLoginException;
import database.entities.UsersEntity;
import org.hibernate.Session;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

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
        StoredProcedureQuery query = currentSession.createStoredProcedureQuery("log_in_user").
                registerStoredProcedureParameter("login", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("password", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("result", Boolean.class, ParameterMode.OUT)
                .setParameter("login", login).setParameter("password", password);
        query.execute();
        Boolean loggedIn = (Boolean) query.getOutputParameterValue("result");

        if(!loggedIn) {
            throw new FailedLoginException();
        }

        TypedQuery<UsersEntity> typedQuery  = currentSession.
                createQuery("from UsersEntity where login = (:login)", UsersEntity.class)
                .setParameter("login", login);

        UsersEntity en = typedQuery.getSingleResult();
        UserType userType = en.getType();

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
