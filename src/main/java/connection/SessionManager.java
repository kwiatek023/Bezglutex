package connection;

import org.hibernate.Session;

/**
 * It is a singleton.
 */
public class SessionManager {
    private static SessionManager instance;
    private Session session;
    private SessionFactoryManager sessionFactoryManager;

    private SessionManager() {
        sessionFactoryManager = new SessionFactoryManager();
        sessionFactoryManager.buildSessionFactory("user", "user");
        session = sessionFactoryManager.getSessionFactory().openSession();
    };

    public static SessionManager getInstance() {
        return instance;
    }

    public boolean sessionIsOpen() {

    }
}
