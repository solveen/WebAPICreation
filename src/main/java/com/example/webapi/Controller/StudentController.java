package com.example.webapi.Controller;

import com.example.webapi.Model.StudentModel;
import com.example.webapi.SecurityConfig.SecurityConfiguration;
import com.example.webapi.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
//controller le page render garni kam garxa
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    SecurityConfiguration securityConfiguration;

    @GetMapping("/")
    //list ma pathako attributes sab model ma gayera basxa
    public String homePage(Model model) {
        List<StudentModel> students = studentService.getStudents();
        //student list attribute name uta chainxa html ma th ma use garda pheri
        model.addAttribute("studentlist", students);
        return "index";
    }

    @GetMapping("/new")
    public String addStudents(Model model) {
        StudentModel student = new StudentModel();
        model.addAttribute("student", student);
        return "addusers";
    }

    @GetMapping("/DeleteUsers/{id}")
    //mathi ko id ra path ko id same hunu parxa
    public String deleteStudents(@PathVariable(name = "id") int id) {
        studentService.deleteById(id);
        // yele same page ma redirect garxa
        return "redirect:/";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveStudent(@ModelAttribute("student") StudentModel student) {
        student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
        studentService.saveUser(student);
        return "redirect:/";
    }


    @RequestMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable(name = "id") int id) {
        //  Model and view return garxa ek palta ma
        ModelAndView modelAndView = new ModelAndView("addusers");
        StudentModel student = studentService.findStudent(id);
        modelAndView.addObject("student",student);
        return modelAndView;
    }
}
