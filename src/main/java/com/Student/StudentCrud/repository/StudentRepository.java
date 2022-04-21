package com.Student.StudentCrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Student.StudentCrud.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
	List<Student> findByName(String name);
}
