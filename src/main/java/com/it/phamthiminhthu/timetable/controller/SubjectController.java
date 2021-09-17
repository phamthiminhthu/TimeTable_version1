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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/api/v1/")
public class SubjectController {
    private SubjectService subjectService;
    public static List<SubjectCommon> result = new ArrayList<>();


    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @GetMapping("/home")
    public String showHome(){
        return "home";
    }
    //show tat ca cac lich
    @GetMapping(value = "/all-lich-hoc")
    public String showAllLichHoc(Model model){
        model.addAttribute("listLichHoc", subjectService.getListSubject());
        return "listSchedule";
    }

    //tim theo ten mon hoc
    @PostMapping(value = "/all-lich-hoc", params = {"contentSub"})
    public String searchInByList(@RequestParam(name = "contentSub") String contentSub, Model model){
        model.addAttribute("listLichHoc", subjectService.searchByContentInput(contentSub));
        return "listSchedule";
    }

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

    //tìm kiếm môn học theo tên và mã học phần
    @PostMapping(value = "/list-subject-by-tenHocPhan", params = {"content"})
    public String searchSubject(@RequestParam(name = "content") String input, Model model){
        List<SubjectCommon> list = subjectService.searchByName(input);
        model.addAttribute("subjectsByTenHocPhanDistinct", list);
        return "page";
    }


    // list mon hoc da chon
    @GetMapping(value = "/list-subject-by-tenHocPhan/show-list-subject-choosen", params = {"listSubject"})
    public ResponseEntity<List<SubjectCommon>> showListSubjectChoosen(@RequestParam(name = "listSubject") List<String> listSubject) {
        result = subjectService.getInformationOfSubjectChoosen(listSubject);
        return new ResponseEntity<List<SubjectCommon>>(subjectService.getInformationOfSubjectChoosen(listSubject), HttpStatus.OK);
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

    //chuyen huong den tao thoi khoa bieu
    @GetMapping(value = "/list-subject-by-tenHocPhan/show-list-subject-choosen")
    public String showListSubjectChoosen(Model model) {
        model.addAttribute("resultSubject", result);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            list.add(result.get(i).getTenHocPhan());
        }
        model.addAttribute("timetableAll",subjectService.sapXepTKB(subjectService.createTimeTable(list)));
        Map<String, List<Subject>> listSubjectCreated = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            listSubjectCreated.put(list.get(i), subjectService.getListSubjectByTenHocPhan(list.get(i)));
        }
        model.addAttribute("myTimeTable", listSubjectCreated);
        return "createTimeTable";
    }

    //tạo thời khoá biểu cua ban
    @GetMapping(value = "/list-subject-by-tenHocPhan/show-list-subject-choosen", params = {"listId"})
    public ResponseEntity<List<Subject>> paintTimeTable(@RequestParam(name = "listId") List<String> listId, Model model) {
        if(subjectService.myCreateTimeTable(listId) == null) return new ResponseEntity<>(null, HttpStatus.OK);
        return new ResponseEntity<List<Subject>>(subjectService.sapXepTKB1(subjectService.myCreateTimeTable(listId)), HttpStatus.OK);
    }

    //about
    @GetMapping("/about")
    public String aboutMe(){
        return "about";
    }






}
