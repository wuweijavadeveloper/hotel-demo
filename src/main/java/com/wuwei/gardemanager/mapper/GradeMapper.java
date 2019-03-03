package com.wuwei.gardemanager.mapper;

import com.wuwei.gardemanager.entity.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;


//@Mapper 已加扫描路径

public interface GradeMapper {
   // @Select("select * from grade where gid = #{gid}")
    public Grade getGrade(Integer gid);
}
