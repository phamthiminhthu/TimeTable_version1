package com.it.phamthiminhthu.timetable.repository;

import com.it.phamthiminhthu.timetable.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    @Query("select s from Subject s WHERE s.tenHocPhan = ?1")
    List<Subject> findListSubjectByTenHocPhan(String tenHocPhan);

    @Query("select distinct s.tenHocPhan from Subject s")
    List<String> findListTenHocPhanDistinct();

    @Query("select s from Subject s WHERE s.maLop = ?1")
    List<Subject> findListSubjectByMaLop(int maLop);

    @Query("select s from Subject s WHERE s.tenHocPhan not like ?1")
    List<Subject> findListNoSubjectHasTenHocPhan(String tenHocPhan);







}
