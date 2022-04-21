package com.Student.StudentCrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Student.StudentCrud.entity.Student;
import com.Student.StudentCrud.repository.StudentRepository;


//https://spring.io/guides/gs/handling-form-submission/

@Controller
@RequestMapping("/students/")
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping("showForm")
	public String showStudentForm(Model model) {
		model.addAttribute("student", new Student());
		System.out.println("inside");
		return "add-student";
	}
	
	
	@GetMapping("list")
	public String students(Model model) {
		model.addAttribute("students", this.studentRepository.findAll());
		return "index";
	}
	
	@PostMapping("add")
	public String addStudent(@Validated Student student, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "add-student";
		}
		
		this.studentRepository.save(student);
		return "redirect:list";
	}
	
	@GetMapping("edit/{id}")
	public String showUpdatedForm(@PathVariable("id") long id, Model model) {
		Student student = this.studentRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid Student ---> " + id));
		
		model.addAttribute("student", student);
		return "update-student";
	}
	
	@PostMapping("update/{id}")
	public String updateStudent(@PathVariable("id") long id, @Validated Student student, BindingResult result, Model model) {
		if(result.hasErrors()) {
			student.setId(id);
			return "update-student";
		}
		
		//update student
		studentRepository.save(student);
		
		//get all students with updated one
		model.addAttribute("students", this.studentRepository.findAll());
		return "index";
	}
	
	
	//@DeleteMapping("delete/{id}")
	@GetMapping("delete/{id}")
	//@RequestMapping(path="delete/{id}", method = RequestMethod.DELETE)
	public String DeleteStudent(@PathVariable("id") long id, Model model) {
		
		Student student = this.studentRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid Student ---> " + id));
		
		this.studentRepository.delete(student);
		//System.out.println("After delete");
		//this.studentRepository.deleteById(id);
		model.addAttribute("students", this.studentRepository.findAll());
		return "index";
	}
}
