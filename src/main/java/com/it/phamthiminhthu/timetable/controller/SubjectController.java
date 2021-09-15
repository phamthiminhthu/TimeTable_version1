package com.it.phamthiminhthu.timetable.controller;

import com.it.phamthiminhthu.timetable.entities.Subject;
import com.it.phamthiminhthu.timetable.entities.SubjectCommon;
import com.it.phamthiminhthu.timetable.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/api/v1/")
public class SubjectController {
    private SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //    @GetMapping("/list-subject-all-timetable")
//    public String getAll(Model model) {
//        model.addAttribute("subjects", subjectService.getListSubject());
//        return "page";
//    }
    //show toan bo thong tin cua 1 list danh sach
    @GetMapping(value = "/list-subject-by-tenHocPhan")
    public String showListByTenHocPhanDistinct(Model model) {
        model.addAttribute("subjectsByTenHocPhanDistinct", subjectService.getInformationCommon());
        return "page";
    }

    //chi tiet tung mon
    @GetMapping(value = "/list-subject-by-tenHocPhan/details")
    public String showDetailsSubject(@RequestParam(name = "tenHocPhan") String tenHocPhan, Model model) {
        List<Subject> subjects = subjectService.getListSubjectByTenHocPhan(tenHocPhan);
        SubjectCommon subjectCommon = new SubjectCommon(subjects.get(0).getTenHocPhan(), subjects.get(0).getVien(),
                subjects.get(0).getMaHp(), subjects.get(0).getLoaiLop());
        model.addAttribute("subjectsDetail", subjects);
        model.addAttribute("subjectDetail", subjectCommon);
        return "details";

    }

    public static List<SubjectCommon> result = new ArrayList<>();

    // list mon hoc da chon
    @GetMapping(value = "/list-subject-by-tenHocPhan/show-list-subject-choosen", params = {"listSubject"})
    public ResponseEntity<List<SubjectCommon>> showListSubjectChoosen(@RequestParam(name = "listSubject") List<String> listSubject) {
        result = subjectService.getInformationOfSubjectChoosen(listSubject);
        return new ResponseEntity<List<SubjectCommon>>(subjectService.getInformationOfSubjectChoosen(listSubject), HttpStatus.OK);
    }

    //chuyen huong den tao thoi khoa bieu
    @GetMapping(value = "/list-subject-by-tenHocPhan/show-list-subject-choosen")
    public String showListSubjectChoosen(Model model) {
        model.addAttribute("resultSubject", result);
        List<String> list = new ArrayList<>();
        for(int i = 0; i < result.size(); i++){
            list.add(result.get(i).getTenHocPhan());
        }

        model.addAttribute("timetableAll", subjectService.createTimeTable(list));
        return "createTimeTable";
    }

    //delete 1 subject trong list
    @GetMapping(value = "/list-subject-by-tenHocPhan/show-list-subject-choosen", params = {"nameSubject"})
    public String showListSubjectWhenChange(@RequestParam(name = "nameSubject") String nameSubject, Model model) {
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getTenHocPhan().equals(nameSubject)) {
                result.remove(i);
            }
        }
        model.addAttribute("resultSubject", result);
        return "createTimeTable";
    }


//    @PostMapping(value = "/list-subject-by-tenHocPhan")
//    public ResponseEntity<List<List<Subject>>> showTimeTable(@RequestParam(name = "listSubject") List<String> listSubject) {
//        return new ResponseEntity<List<List<Subject>>>(subjectService.createTimeTable(listSubject), HttpStatus.OK);
//    }

    //tạo thời khoá biểu


}
