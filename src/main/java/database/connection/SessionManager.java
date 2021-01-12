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
    private final SessionBuilder sessionBuilder;

    private SessionManager() {
        sessionBuilder = new SessionBuilder();
        currentSession = sessionBuilder.buildSession("login", "login");
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public UserType openSessionForUser(String login, String password) throws FailedLoginException {
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
        currentSession.close();

        switch (userType) {
            case store_keeper: {
                currentSession = sessionBuilder.buildSession("store_keeper", "store_keeper");
                break;
            }
            case store_manager: {
                currentSession = sessionBuilder.buildSession("store_manager", "store_manager");
                break;
            }
            case salesman: {
                currentSession = sessionBuilder.buildSession("salesman", "salesman");
                break;
            }
            case admin: {
                currentSession = sessionBuilder.buildSession("admin", "admin");
            }
        }

        return userType;
    }
}
