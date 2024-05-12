package com.github.brucemelo;

import com.github.brucemelo.infrastructure.AppHibernate;
import com.github.brucemelo.model.Student;
import com.github.brucemelo.model.Student_;


public class Main {

    public static void main(String[] args) {

        AppHibernate.getSessionFactory().inTransaction(session -> {
            var student = new Student();
            student.setName("Bruce Melo");
            session.persist(student);
        });

        AppHibernate.getSessionFactory().inStatelessTransaction(session -> {
            var student = new Student();
            student.setName("Cássia");
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

    }

}