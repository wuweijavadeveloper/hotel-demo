package com.wuwei.gardemanager.controller;

import com.wuwei.gardemanager.entity.Grade;
import com.wuwei.gardemanager.entity.Student;
import com.wuwei.gardemanager.mapper.GradeMapper;
import com.wuwei.gardemanager.mapper.StudentMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Controller_01 {
    @Autowired
    GradeMapper gradeMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    private SolrClient solrClient;
    List<Student> list =new ArrayList<Student>();

    @RequestMapping("/getgrade")
    //  @GetMapping("getgrade")
    @ResponseBody
    public Grade getGrade(Integer gid) {
        Grade grade = gradeMapper.getGrade(gid);
        return grade;
    }

    // @ResponseBody
    //  @RequestMapping("/getall")
    @GetMapping("getall")
    // @ResponseBody
    public String getAll(Model model) {
        List<Student> list = studentMapper.getAll();
        model.addAttribute("list", list);
        return "index";
    }


    //  @RequestMapping("addstu")
    @PostMapping("/addstu")
    public String addStu(Student student) {
        int temp = studentMapper.addStu(student);
        if (temp > 0) {
            System.out.println("添加成功");
            return "redirect:/getall";
        } else {
            System.out.println("添加失败");
            return "add";
        }
    }

    @GetMapping("/delstu")
    public String delStu(Integer xh) {
        int temp = studentMapper.delStu(xh);
        if (temp > 0) {
            System.out.println("删除成功");
            return "redirect:/getall";
        } else {
            System.out.println("删除失败");
            return "redirect:/getall";
        }
    }


    @GetMapping("/update")
    //  @ResponseBody
    public String update(Integer xh, Model model) {
        Student stu = studentMapper.selectOne(xh);
        model.addAttribute("stu", stu);
        return "update";
    }


    @PostMapping("/updatestu")
    public String updateStu(Student student) {
        int temp = studentMapper.updateStu(student);
        if (temp > 0) {
            System.out.println("修改成功");
            return "redirect:/getall";
        } else {
            System.out.println("修改失败");
            return "redirect:/getall";
        }
    }

    @GetMapping("/init")
    @ResponseBody
    public String init() throws IOException, SolrServerException {
        List<Student> list = studentMapper.getAll();

        for (Student stu: list) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("xh", stu.getXh().toString());
            document.addField("name", stu.getName());
            solrClient.add(document);
            solrClient.commit();
        }

        return "初始化成功";
    }

    @GetMapping("/selectbyname")
    //@ResponseBody
    public String  selectByName(String query,Model model) throws IOException, SolrServerException {

       // List<Student> list=studentMapper.selectOneByName(name);
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.setQuery("stukeywords:"+query);
      //  solrQuery.setQuery(name);
      //  solrQuery.set("df","stukeywords");
        System.out.println(query);
       // solrQuery.set("q","xh:101");
        QueryResponse queryResponse=solrClient.query(solrQuery);
        SolrDocumentList solrDocuments = queryResponse.getResults();
           // model.addAttribute("list",solrDocuments);
          //  return "redirect:index";
         list =new ArrayList<Student>();
        System.out.println(solrDocuments.getNumFound());
        for (SolrDocument solrDocument : solrDocuments ) {
            System.out.println("我");
            Student student=new Student();
            student.setXh(Integer.parseInt(solrDocument.getFieldValue("xh").toString()) );
            student.setName(solrDocument.get("name").toString());
            list.add(student);
        }

          model.addAttribute("list",list);
        return "redirect:/goindex ";

    }

    @GetMapping("/goindex")
    // @ResponseBody
    public String goIndex(Model model) {
        model.addAttribute("list",list);

        return "index";
    }
}