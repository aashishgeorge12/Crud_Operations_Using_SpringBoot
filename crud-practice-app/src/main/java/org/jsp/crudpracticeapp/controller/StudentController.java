package org.jsp.crudpracticeapp.controller;

import java.util.List;
import java.util.Optional;

import org.jsp.crudpracticeapp.dto.Students;
import org.jsp.crudpracticeapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/students")
public class StudentController 
{
	@Autowired
	private StudentRepository sr;
	
	@PostMapping
	public Students SaveStudent(@RequestBody Students st) {
		return sr.save(st);
	}

	@GetMapping
	public List<Students>findall(){
		return sr.findAll();
	}
	
	@GetMapping(value="/find_name/{name}")
	public List<Students>findbyname(@PathVariable String name)
	{
		return sr.findByName(name);
	}
	
	@PostMapping(value="/verify-by-email")
	public Students verifyStudent(@RequestParam(name="name")String name, @RequestParam(name="email")String email )
	{
		Optional<Students> result= sr.findByNameAndEmail(name, email);
		if(result.isPresent())
		{
			return result.get();
		}
		return null;
	}
	
	@PutMapping
	public Students updateStudent(@RequestBody Students st)
	{
		Optional<Students>result=sr.findById(st.getId());
		if(result.isPresent()) {
			Students stud=result.get();
			stud.setName(st.getName());
			stud.setEmail(st.getEmail());
			stud.setAge(st.getAge());
			
			return sr.save(stud);
		}
		return null;
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable(name = "id")int id)
	{
		Optional<Students>result=sr.findById(id);
		if(result.isPresent()) {
			sr.delete(result.get());
			return "user deleted";
		}
		return "smthing went wrong";
	}
	
	
}