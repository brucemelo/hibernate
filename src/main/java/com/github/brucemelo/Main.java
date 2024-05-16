package com.github.brucemelo;

import com.github.brucemelo.infrastructure.AppHibernate;
import com.github.brucemelo.model.Queries_;
import com.github.brucemelo.model.Student;
import com.github.brucemelo.model.Student_;
import org.hibernate.query.criteria.CriteriaDefinition;


public class Main {

    public static void main(String[] args) {
        AppHibernate.getSessionFactory().inTransaction(session -> {
            var student = Student.newStudent("Bruce Melo", "bruce@example.com");
            session.persist(student);
        });

        AppHibernate.getSessionFactory().inStatelessTransaction(session -> {
            var student = Student.newStudent("Cássia", "cassia@example.com");
            session.insert(student);
        });

        AppHibernate.getSessionFactory().inStatelessTransaction(session -> {
            var student = Student.newStudent("Mary", "mary@example.com");
            session.insert(student);
        });

        AppHibernate.getSessionFactory().inStatelessTransaction(session -> {
            var student = Student.newStudent("Aerith", "aerith@example.com");
            session.insert(student);
        });

        AppHibernate.getSessionFactory().inSession(session -> {
            var students = session.createSelectionQuery("from Student", Student.class).list();
            students.forEach(student -> {
                System.out.println(student.getName());
            });
        });

        AppHibernate.getSessionFactory().inStatelessSession(statelessSession -> {
            var cb = statelessSession.getCriteriaBuilder();
            var criteria = cb.createQuery(Student.class);
            var root = criteria.from(Student.class);
            criteria.select(root).where(cb.equal(root.get(Student_.NAME), "Bruce Melo"));
            var student = statelessSession.createSelectionQuery(criteria).getSingleResult();
            System.out.println(student.getName());
        });

        AppHibernate.getSessionFactory().inStatelessSession(statelessSession -> {
            var query = new CriteriaDefinition<>(statelessSession, Student.class) {
                {
                    var student = from(Student.class);
                    restrict(ilike(student.get(Student_.NAME), "%melo%"));
                    orderBy(asc(student.get(Student_.NAME)));
                }
            };
            var student = statelessSession.createSelectionQuery(query).getSingleResultOrNull();
            System.out.println(student.getName());
        });

        AppHibernate.getSessionFactory().inStatelessSession(statelessSession -> {
            var entityManager = statelessSession.getFactory().createEntityManager();
            var student = Queries_.findStudentByName(entityManager, "Aerith");
            System.out.println(student);
        });

        AppHibernate.getSessionFactory().inStatelessSession(statelessSession -> {
            var entityManager = statelessSession.getFactory().createEntityManager();
            var student = Queries_.findStudentByEmail(entityManager, "mary@example.com");
            System.out.println(student.getEmail());
        });
    }

}