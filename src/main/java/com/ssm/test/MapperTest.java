package com.ssm.test;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;
import com.ssm.DAO.DepartmentMapper;
import com.ssm.DAO.EmployeeMapper;
import com.ssm.bean.Employee;
import com.ssm.bean.EmployeeExample;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"file:./WebContent/WEB-INF/springDispatcherServlet-servlet.xml" })
@WebAppConfiguration
public class MapperTest {
	@Autowired
	DepartmentMapper mapper;

	@Autowired
	EmployeeMapper eMapper;

	MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@Before
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testPage() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();
		MockHttpServletRequest request = result.getRequest();
		PageInfo page = (PageInfo) request.getAttribute("pageInfo");
		System.out.println(page.getPages());
		System.out.println(page.getPageNum());
		System.out.println(page.getTotal());
		int[] nums = page.getNavigatepageNums();
		for (int i : nums) {
			System.out.println(i);
		}

		List<Employee> employees = page.getList();
		for (Employee employee : employees) {
			System.out.println(employee.getId() + "--" + employee.getLastName());
		}
	}

	@Test
	public void testCRUD() {
		// Department department = new Department(null, "OA");
		// mapper.insertSelective(department);
		Employee selectByPrimaryKey = eMapper.selectByPrimaryKeyWithDept(1);
		EmployeeExample example = new EmployeeExample();
		example.setOrderByClause("id");
		List<Employee> selectByExampleWithDept = eMapper.selectByExampleWithDept(example);
		List<Employee> selectByExample = eMapper.selectByExample(null);
		System.out.println(selectByExample);
		System.out.println(selectByExampleWithDept);
		System.out.println("fin");
		System.out.println(selectByPrimaryKey);
	}

	@Test
	public void testInsert() {
		Random random = new Random();
		Employee employee = new Employee();
		for (int i = 0; i < 5; i++) {
			employee.setId(null);
			employee.setLastName("name1" + i);
			employee.setEmail(i + "1@dd.com");
			employee.setGender(String.valueOf(random.nextInt(2)));
			employee.setdId(random.nextInt(2) + 1);
			eMapper.insertSelective(employee);
		}
	}

}
