package com.github.brucemelo.infrastructure;

import com.github.brucemelo.model.Course;
import com.github.brucemelo.model.Student;
import com.github.brucemelo.model.StudentCourse;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.tool.schema.Action;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppHibernate {

    private static final Logger logger = Logger.getLogger(AppHibernate.class.getName());

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                disableLogs();

                var configuration = getConfiguration();
                var serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Throwable ex) {
                logger.log(Level.SEVERE, "Failed to create session factory", ex);
            }
        }
        return sessionFactory;
    }

    private static void disableLogs() {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    // equivalent to hibernate.properties
    private static Configuration getConfiguration() {
        var configuration = new Configuration();

        var settings = new Properties();
        settings.put(JdbcSettings.JAKARTA_JDBC_DRIVER, "org.postgresql.Driver");
        settings.put(JdbcSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/mydatabase");
        settings.put(JdbcSettings.JAKARTA_JDBC_USER, "sa");
        settings.put(JdbcSettings.JAKARTA_JDBC_PASSWORD, "sa");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.FORMAT_SQL, "true");
        settings.put(Environment.HIGHLIGHT_SQL, "true");
        settings.put(Environment.HBM2DDL_AUTO, Action.ACTION_CREATE);
//        settings.put(Environment.GENERATE_STATISTICS, "true");
//        settings.put(Environment.JAKARTA_HBM2DDL_SCRIPTS_ACTION, Action.ACTION_CREATE);
//        settings.put(Environment.JAKARTA_HBM2DDL_SCRIPTS_CREATE_TARGET, "create.sql");

        configuration.setProperties(settings);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(StudentCourse.class);
        return configuration;
    }

}
