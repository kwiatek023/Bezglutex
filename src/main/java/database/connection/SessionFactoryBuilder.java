package database.connection;

import database.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryBuilder {
    public SessionFactory buildSessionFactory(String login, String password) {
        Configuration configuration = new Configuration();

        System.setProperty("hibernate.connection.username", login);
        System.setProperty("hibernate.connection.password", password);
        configuration.setProperties(System.getProperties());

        SessionFactory sessionFactory = configuration.configure().
                addAnnotatedClass(BreadstuffEntity.class).
                addAnnotatedClass(CustomersEntity.class).
                addAnnotatedClass(DessertsEntity.class).
                addAnnotatedClass(OrdersEntity.class).
                addAnnotatedClass(OrdersProductsEntity.class).
                addAnnotatedClass(OrdersProductsEntity.class).
                addAnnotatedClass(PastaEntity.class).
                addAnnotatedClass(ProductsEntity.class).
                addAnnotatedClass(StorageEntity.class).
                addAnnotatedClass(SuppliersEntity.class).
                addAnnotatedClass(SuppliesEntity.class).
                addAnnotatedClass(SuppliesProductsEntity.class).
                addAnnotatedClass(SuppliesProductsEntityPK.class).
                addAnnotatedClass(UsersEntity.class).
                buildSessionFactory();

        return sessionFactory;
    }
}
