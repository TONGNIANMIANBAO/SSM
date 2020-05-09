package com.ssm.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.bean.Department;
import com.ssm.bean.Msg;
import com.ssm.service.DepartmentService;

@Controller
public class DepartmentController {
	@Autowired
	DepartmentService service;

	// 可以批量操作的sqlsession
	@Autowired
	SqlSession sqlSession;

	@ResponseBody
	@RequestMapping("/departments")
	public Msg getDepartments() {
		List<Department> allDepartment = service.getAllDepartment();
		return new Msg().success().add("depts", allDepartment);
	}

}
