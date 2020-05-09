package com.ssm.service;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.DAO.EmployeeMapper;
import com.ssm.bean.Employee;
import com.ssm.bean.EmployeeExample;
import com.ssm.bean.EmployeeExample.Criteria;

@Service
public class EmployeeService {
	@Autowired
	EmployeeMapper mapper;

	public List<Employee> getAllEmployee() {
		EmployeeExample example = new EmployeeExample();
		example.setOrderByClause("id");
		return mapper.selectByExampleWithDept(example);
	}

	@RequiresRoles("manager")
	public void saveEmp(Employee employee) {
		mapper.insertSelective(employee);
	}

	public boolean checkEmp(String empname) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andLastNameEqualTo(empname);
		long count = mapper.countByExample(example);
		return count == 0;
	}

	public Employee getEmpByName(String empname) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andLastNameEqualTo(empname);
		List<Employee> emps = mapper.selectByExample(example);
		return emps.size() == 0 ? null : emps.get(0);
	}

	public Employee getEmpById(Integer id) {
		Employee employee = mapper.selectByPrimaryKey(id);
		return employee;
	}

	public boolean updateEmp(Employee employee) {
		int i = mapper.updateByPrimaryKeySelective(employee);
		return i == 0;
	}

	public boolean deleteEmpById(Integer id) {
		int i = mapper.deleteByPrimaryKey(id);
		return i == 0;
	}

	public boolean deleteEmpBatch(List<Integer> list) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		int i = mapper.deleteByExample(example);
		return i == 0;
	}
}
