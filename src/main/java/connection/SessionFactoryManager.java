package connection;

import entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryManager {
    private SessionFactory sessionFactory;

    public void buildSessionFactory(String login, String password) {
        Configuration configuration = new Configuration();

        System.setProperty("hibernate.connection.username", login);
        System.setProperty("hibernate.connection.password", password);
        configuration.setProperties(System.getProperties());

        sessionFactory = configuration.configure("hibernate.cfg.xml").
//                addAnnotatedClass(AlbumsEntity.class).
//                addAnnotatedClass(ArtistsEntity.class).
//                addAnnotatedClass(DeliveryEntity.class).
//                addAnnotatedClass(InstrumentManufacturersEntity.class).
//                addAnnotatedClass(InstrumentsEntity.class).
//                addAnnotatedClass(OrdersEntity.class).
//                addAnnotatedClass(OrdersProductsEntity.class).
//                addAnnotatedClass(DeliveryEntity.class).
//                addAnnotatedClass(OtherEntity.class).
//                addAnnotatedClass(ProductsEntity.class).
//                addAnnotatedClass(StatusLogsEntity.class).
//                addAnnotatedClass(StorageEntity.class).
                addAnnotatedClass(UsersEntity.class).
                buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
