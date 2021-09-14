package com.it.phamthiminhthu.timetable.controller;

import com.it.phamthiminhthu.timetable.entities.Subject;
import com.it.phamthiminhthu.timetable.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/api/v1/")
public class SubjectController {
    private SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/list-subject-all-timetable")
    public String getAll(Model model) {
        model.addAttribute("subjects", subjectService.getListSubject());
        return "page";
    }

    @GetMapping(value = "/list-subject-by-tenHocPhan")
    public String showListByTenHocPhanDistinct(Model model) {
        model.addAttribute("subjectsByTenHocPhanDistinct", subjectService.getListSubjectByTenHocPhanDistinct());
        return "page";
    }

    @GetMapping(value = "/list-subject-by-tenHocPhan", params = {"tenHocPhan"})
    public ResponseEntity<List<Subject>> showListByTenHocPhan(String tenHocPhan) {
        return new ResponseEntity<List<Subject>>(subjectService.getListSubjectByTenHocPhan(tenHocPhan), HttpStatus.OK);
    }


    @PostMapping(value = "/list-subject-by-tenHocPhan")
    public ResponseEntity<List<List<Subject>>> showTimeTable(@RequestParam(name = "listSubject") List<String> listSubject) {
        return new ResponseEntity<List<List<Subject>>>(subjectService.createTimeTable(listSubject), HttpStatus.OK);
    }


}
