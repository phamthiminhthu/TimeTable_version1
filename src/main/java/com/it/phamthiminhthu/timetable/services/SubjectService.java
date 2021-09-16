package com.it.phamthiminhthu.timetable.services;

import com.it.phamthiminhthu.timetable.entities.NodeAllPath;
import com.it.phamthiminhthu.timetable.entities.Subject;
import com.it.phamthiminhthu.timetable.entities.SubjectCommon;
import com.it.phamthiminhthu.timetable.repository.SubjectRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {Exception.class})
@Builder
public class SubjectService {

    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getListSubject() {
        return subjectRepository.findAll();
    }

    public List<Subject> getListSubjectByTenHocPhan(String tenHocPhan) {
        return subjectRepository.findListSubjectByTenHocPhan(tenHocPhan);
    }

    public List<String> getListSubjectByTenHocPhanDistinct() {
        return subjectRepository.findListTenHocPhanDistinct();
    }

    public List<SubjectCommon> getInformationCommon() {
        List<String> subject = getListSubjectByTenHocPhanDistinct();
        List<SubjectCommon> list = new ArrayList<>();
        for (int i = 0; i < subject.size(); i++) {
            List<Subject> subjects = getListSubjectByTenHocPhan(subject.get(i));
            list.add(new SubjectCommon(subject.get(i), subjects.get(0).getVien(), subjects.get(0).getMaHp(), subjects.get(0).getLoaiLop()));
        }
        return list;
    }

    public List<SubjectCommon> getInformationOfSubjectChoosen(List<String> subject) {
        subject = parseList(subject);
        List<SubjectCommon> list = new ArrayList<>();
        for (int i = 0; i < subject.size(); i++) {
            List<Subject> subjects = getListSubjectByTenHocPhan(subject.get(i));
            list.add(new SubjectCommon(subject.get(i), subjects.get(0).getVien(), subjects.get(0).getMaHp(), subjects.get(0).getLoaiLop()));
        }
        return list;
    }

    public List<Subject> findSubjectById(int id) {
        Subject subject = subjectRepository.findByIdSubject(id);
        List<Subject> list = subjectRepository.findByMaLop(subject.getMaLop());
        return list;
    }
    public List<Integer> parseIntId(List<String> id) {
        id = parseList(id);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            list.add(Integer.parseInt(id.get(i)));
        }
        return list;
    }


    public List<Subject> myCreateTimeTable(List<String> listId) {
        List<Integer> newListId = parseIntId(listId);
        List<Subject> list = new ArrayList<>();
        for (int i = 0; i < newListId.size(); i++) {
            list.addAll(findSubjectById(newListId.get(i)));
        }

        if (list.size() == 1) return list;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (!checkTimeTwoSubjects1(list.get(i), list.get(j))) {
                    return null;
                }
            }
        }
        return list;
    }


    //tifm kieems

    public List<SubjectCommon> searchByName(String name) {
        List<Subject> subjects = subjectRepository.findByNameSubject(name);
        List<Subject> removeSub = new ArrayList<>();
        for (int i = 0; i < subjects.size() - 1; i++) {
            for (int j = i + 1; j < subjects.size(); j++) {
                if (subjects.get(i).getTenHocPhan().equals(subjects.get(j).getTenHocPhan())) {
                    removeSub.add(subjects.get(j));
                }
            }
        }
        subjects.removeAll(removeSub);
        List<SubjectCommon> subjectCommons = new ArrayList<>();
        for (int i = 0; i < subjects.size(); i++) {
            subjectCommons.add(new SubjectCommon(subjects.get(i).getTenHocPhan(), subjects.get(i).getVien(),
                    subjects.get(i).getMaHp(), subjects.get(i).getLoaiLop()));
        }
        return subjectCommons;
    }

    //danh sach nhung tên lop can hoc -> sắp xếp theo số lượng mã lớp từ bé đến cao
    public List<String> sortListBySoLuongMaLop(List<String> subjects) {
        Map<String, Integer> mapSubject = new HashMap<>();
        for (int i = 0; i < subjects.size(); i++) {
            List<Subject> list = new ArrayList<>();
            list = listSubjectDistinct(subjects.get(i));
            List<Integer> listMaLop = new ArrayList<>();
            for (int j = 0; j < list.size(); j++) {
                listMaLop.add(list.get(j).getMaLop());
            }
            mapSubject.put(subjects.get(i), listMaLop.size());
        }
        List<String> listSub = new ArrayList<>();

        Map<String, Integer> sortedMap = mapSubject.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<String, Integer> map : sortedMap.entrySet()) {
            listSub.add(map.getKey());
        }
        return listSub;
    }


    // 1 môn học mấy buổi trong tuần-> tra ve mon hoc
    public List<Subject> countThuTrongTuanHocSubject(Subject subject) {
        List<Subject> list = getListSubjectByTenHocPhan(subject.getTenHocPhan());
        List<Subject> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMaLop() == subject.getMaLop()) {
                result.add(list.get(i));
            }
        }
        return result;
    }
    public boolean checkTimeTwoSubjects1(Subject subject1, Subject subject2){
        if (subject1.getThu() != subject2.getThu()) return true;
        String[] time1 = subject1.getThoiGian().split("-");
        String[] time2 = subject2.getThoiGian().split("-");

        int i = Integer.parseInt(time1[0].trim());
        int j = Integer.parseInt(time1[1].trim());
        int h = Integer.parseInt(time2[0].trim());
        int k = Integer.parseInt(time2[1].trim());
        if (j < h || k < i) {
            return true;
        } else {
            return false;
        }
    }

    //check thoi gian 2 lop hoc có bị trùng hay gì không
    public boolean checkTimeTwoSubjects(Subject subject1, Subject subject2) {
        if (countThuTrongTuanHocSubject(subject1).size() == 1 && countThuTrongTuanHocSubject(subject2).size() == 1) {
            checkTimeTwoSubjects1(subject1, subject2);

        } else {
            List<Subject> list1 = countThuTrongTuanHocSubject(subject1);
            List<Subject> list2 = countThuTrongTuanHocSubject(subject2);
            for (int i = 0; i < list1.size(); i++) {
                for (int j = 0; j < list2.size(); j++) {
                    if (list1.get(i).getThu() == list2.get(j).getThu()) {
                        String[] time1 = list1.get(i).getThoiGian().split("-");
                        String[] time2 = list2.get(j).getThoiGian().split("-");
                        int p = Integer.parseInt(time1[0].trim());
                        int q = Integer.parseInt(time1[1].trim());
                        int h = Integer.parseInt(time2[0].trim());
                        int k = Integer.parseInt(time2[0].trim());
                        if (p > k || q < h) {
                            continue;
                        } else {
                            return false;
                        }

                    }
                }
            }

        }

        return true;

    }

    //kiem tra nhung mon cung gio, cung thu (khac ma thoi)
    public boolean checkTwoSubTrungNgayTrungGio(Subject subject1, Subject subject2) {
        if (subject1.getTenHocPhan().equals(subject2.getTenHocPhan()) && subject1.getThu() == subject2.getThu()
                && subject1.getThoiGian().trim().equals(subject2.getThoiGian().trim())) {
            return true;
        }
        return false;
    }

    //chinhr sửa đầu vào
    public List<String> parseList(List<String> subjects) {
        List<String> sub = new ArrayList<>();
        if (subjects.size() == 1) {
            sub.add(subjects.get(0).substring(2, subjects.get(0).length() - 2));
            return sub;
        }
        sub.add(subjects.get(0).substring(2, subjects.get(0).length() - 1));
        for (int i = 1; i < subjects.size() - 1; i++) {
            sub.add(subjects.get(i).substring(1, subjects.get(i).length() - 1));
        }
        sub.add(subjects.get(subjects.size() - 1).substring(1, subjects.get(subjects.size() - 1).length() - 2));

        return sub;


    }

    // 1 list cung mot mon nhung khong giong nhau ve gio hoc
    public List<Subject> listSubjectDistinct(String nameSub) {
        List<Subject> list = getListSubjectByTenHocPhan(nameSub);
        List<Subject> listCopy = new ArrayList<>();

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).getMaLop() == list.get(j).getMaLop() || checkTwoSubTrungNgayTrungGio(list.get(i), list.get(j))) {
                    listCopy.add(list.get(j));
                }
            }
        }
        list.removeAll(listCopy);
        return list;
    }

    // canh cua mon hoc tiep theo phai la khong trung gio voi mon hoc truoc, loai bo nhung ma cung gio ra
    public List<Subject> listEdgeSubject(List<Subject> subject1, String subject2) {
        List<Subject> listSub2 = listSubjectDistinct(subject2);
        List<Subject> listNew = new ArrayList<>();
        for (int i = 0; i < listSub2.size(); i++) {
            for (int j = 0; j < subject1.size(); j++) {
                if (!checkTimeTwoSubjects(listSub2.get(i), subject1.get(j))) {
                    listNew.add(listSub2.get(i));
                }
            }
        }

        listSub2.removeAll(listNew);
        return listSub2;

    }

    //kiem tra 2 mang kqua xem giong nhau chua ( ve do dai, string -> du mon hoc hay chua)
    public boolean checkTwoArrayEquals(List<String> subject, List<Subject> timeTable) {
        List<String> subject1 = new ArrayList<>();
        for (int i = 0; i < timeTable.size(); i++) {
            subject1.add(timeTable.get(i).getTenHocPhan());
        }
        subject1 = sortListBySoLuongMaLop(subject1);
        if (subject.size() == subject1.size() && subject1.equals(subject)) return true;
        return false;
    }


    // ham chinh tra ve tat ca tkb -> dung dfs
    public List<List<Subject>> createTimeTable(List<String> subjects) {
        List<List<Subject>> result = new ArrayList<>();
        subjects = sortListBySoLuongMaLop(subjects);
        if (subjects.size() == 0) return null;
        if (subjects.size() == 1) {
            List<Subject> list = new ArrayList<>();
            list = listSubjectDistinct(subjects.get(0));
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    List<Subject> sub = new ArrayList<>();
                    sub.add(list.get(i));
                    result.add(sub);
                }
            }
            return result;
        }

        List<Subject> list = listSubjectDistinct(subjects.get(0));
        for (int i = 0; i < list.size(); i++) {
            Stack<NodeAllPath> stack = new Stack<>();
            stack.push(new NodeAllPath(list.get(0), Arrays.asList(list.get(i))));
            while (!stack.isEmpty()) {
                NodeAllPath node_popped = stack.pop();
                List<Subject> subDaDangKy = new ArrayList<>();
                subDaDangKy.addAll(node_popped.getVisited());
                if (subDaDangKy.size() == subjects.size()) {
                    stack.clear();
                    break;
                }
                List<Subject> subjectDangXet = listEdgeSubject(subDaDangKy, subjects.get(subDaDangKy.size()));
                if (subjectDangXet.size() == 0 && subDaDangKy.size() < subjects.size()) {
                    stack.clear();
                    break;
                }
                for (Subject subject1 : subjectDangXet) {
                    if (Collections.frequency(node_popped.getVisited(), subject1) < 2) {
                        List<Subject> timeTable = new ArrayList<>(node_popped.getVisited());
                        timeTable.add(subject1);
                        if (checkTwoArrayEquals(subjects, timeTable)) {
                            result.add(timeTable);
                        } else {
                            stack.push(new NodeAllPath(subject1, timeTable));
                        }
                    }
                }

            }
        }
        return result;

    }

    //sap xep lai thoi gian cua 1 thoi khoa bieu
    public List<List<Subject>> sapXepTKB(List<List<Subject>> list) {
        for (int i = 0; i < list.size(); i++) {
            Collections.sort(list.get(i), new SortByDateTime());
        }
        return list;
    }

    //sap xep 1 tkb
    public List<Subject> sapXepTKB1(List<Subject> subjects) {
        Collections.sort(subjects, new SortByDateTime());
        return subjects;
    }


}
