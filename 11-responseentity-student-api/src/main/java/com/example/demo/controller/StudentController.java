package com.example.demo.controller;

import com.example.demo.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final List<Student> students = List.of(
            new Student(1L, "Maria", 22L),
            new Student(2L, "Nikos", 24L),
            new Student(3L, "Elena", 22L)
    );

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Student> search(@PathVariable String name){
        return students.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Student> search1(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) Long age){
        return (ResponseEntity<Student>) students.stream()
                .filter(s -> (name == null || s.getName().equalsIgnoreCase(name)))
                .filter(s -> (age == null || s.getAge().equals(age)))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        // In a real application, you would save the student to the database here
        return (ResponseEntity<Student>)students.stream()
                .filter(s -> s.getId().equals(student.getId()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
