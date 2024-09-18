package cn.lchospital.baby.service;


import cn.lchospital.baby.dto.StudentDto;
import cn.lchospital.baby.vo.MomentVo;

import java.util.List;

public interface BabyService {
    void login();

    String token();

    StudentDto getLoginStudent();

    StudentDto getStudent();

    String teacherList();

    List<MomentVo> getMoments(String lastMomentId);
}
