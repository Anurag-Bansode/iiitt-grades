package example.example.grading_engine.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = new Configuration()
                    .configure("con1.xml")
                    .buildSessionFactory();

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

}
