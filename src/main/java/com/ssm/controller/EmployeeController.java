package com.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.bean.Employee;
import com.ssm.bean.Msg;
import com.ssm.service.EmployeeService;

/**
 * 
 * @author SEN.CHEN
 *
 */
@Controller
public class EmployeeController {
	@Autowired
	EmployeeService service;

	// @RequestMapping("/emps")
	public String test(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
		PageHelper.startPage(pn, 3);
		List<Employee> allEmployee = service.getAllEmployee();
		PageInfo info = new PageInfo(allEmployee, 5);
		model.addAttribute("pageInfo", info);
		return "list";
	}

	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		PageHelper.startPage(pn, 3);
		List<Employee> allEmployee = service.getAllEmployee();
		PageInfo info = new PageInfo(allEmployee, 5);
		return new Msg().success().add("pageInfo", info);
	}

	@RequiresRoles("manager")
	@ResponseBody
	@RequestMapping(value = "/emps", method = RequestMethod.POST)
	public Msg saveEmp(@Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> map = new HashMap<>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println(fieldError.getField());
				System.out.println(fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return new Msg().fail().add("error", map);
		} else {
			service.saveEmp(employee);
			return new Msg().success();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkEmp", method = RequestMethod.GET)
	public String checkEmp(String lastName) {
		boolean checkEmp = service.checkEmp(lastName);
		return String.valueOf(checkEmp);
	}

	@ResponseBody
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	public Msg getEmpById(@PathVariable("id") Integer id) {
		Employee employee = service.getEmpById(id);
		return new Msg().success().add("emp", employee);
	}

	@ResponseBody
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.PUT)
	public Msg updateEmp(Employee employee) {
		if (!service.updateEmp(employee)) {
			return new Msg().success();
		} else {
			return new Msg().fail();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/emp/{idList}", method = RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("idList") String idList) {
		boolean flag = true;
		List<Integer> list = new ArrayList<>();
		if (idList.contains("-")) {
			String[] ids = idList.split("-");
			for (String id : ids) {
				list.add(Integer.valueOf(id));
			}
			flag = service.deleteEmpBatch(list);
		} else {
			flag = service.deleteEmpById(Integer.valueOf(idList));
		}
		if (!flag) {
			return new Msg().success();
		}
		return new Msg().fail();

	}
}
