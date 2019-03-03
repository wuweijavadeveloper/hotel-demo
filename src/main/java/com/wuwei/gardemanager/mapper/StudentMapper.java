package com.wuwei.gardemanager.mapper;

import com.wuwei.gardemanager.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper 已加扫描路径

public interface StudentMapper {
    public List<Student> getAll();

    public int addStu(Student student);

    public int delStu(Integer xh);

    public int updateStu(Student student);

    public Student selectOne(Integer xh);

    public List<Student> selectOneByName(String name);

}
