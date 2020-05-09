package com.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.DAO.DepartmentMapper;
import com.ssm.bean.Department;

@Service
public class DepartmentService {
	@Autowired
	DepartmentMapper dMapper;

	public List<Department> getAllDepartment() {
		return dMapper.selectByExample(null);
	}
}
