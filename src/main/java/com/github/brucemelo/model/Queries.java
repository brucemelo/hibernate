package com.github.brucemelo.model;

import org.hibernate.annotations.processing.Find;
import org.hibernate.annotations.processing.HQL;

public interface Queries {

    @HQL("where name = :name")
    Student findStudentByName(String name);

    @Find
    Student findStudentByEmail(String email);

}
