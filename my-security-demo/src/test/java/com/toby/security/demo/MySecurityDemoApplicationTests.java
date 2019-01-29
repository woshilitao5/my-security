package com.toby.security.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//静态导入，下面类中的属性、方法可以直接跟当前类中定义的一样用
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySecurityDemoApplicationTests {
	@Autowired
	private WebApplicationContext webContext;
	private MockMvc mockMvc;

	@Before // 所有的测试启动前都会先执行这个
	public void setupMockMvc() {
		// 用MockMvcBuilders创建mockMvc实例
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Test
	public void initData() throws Exception {
		// mockMvc.perform(post("/user") //执行POST请求,并传参数
		// .contentType(MediaType.APPLICATION_JSON_UTF8)
		// .param("userName", "test2") //设置表单域，模拟表单输入
		// .param("password", "123")
		// .param("rolename",
		// "admin"))//直接param来设置参数，控制器不用@RequestBody也能解析，但是用content设字符串就不行
		// //但是使用param，控制器使用@RequestBody就会报错Required request body is missing
		//// .andExpect(status().is3xxRedirection())//301到304都是重定向的状态，这里就是判断是否重定向
		//// .andExpect(header().string("Location", "/readingList")
		// )//响应的header中的值作为字符串判断
		// ;

		String user1 = "{\"userName\":\"user1\",\"password\":\"111\",\"rolename\":\"user\"}";
		mockMvc.perform(post("/user") // put用来更新
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(user1)// 直接param来设置参数，控制器不用@RequestBody也能解析，但是用content设字符串就不行
		).andExpect(status().isOk());

		String user2 = "{\"userName\":\"user2\",\"password\":\"222\",\"rolename\":\"user\"}";
		mockMvc.perform(post("/user") // put用来更新
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(user2)// 直接param来设置参数，控制器不用@RequestBody也能解析，但是用content设字符串就不行
		).andExpect(status().isOk());

		String user3 = "{\"userName\":\"user\",\"password\":\"3333upd\",\"rolename\":\"user\"}";
		mockMvc.perform(post("/user") // put用来更新
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(user3)// 直接param来设置参数，控制器不用@RequestBody也能解析，但是用content设字符串就不行
		).andExpect(status().isOk());
		System.out.println("=====初始化成功====");
	}

	@Test
	public void whenQuerySuccess() throws Exception {
		// 首先向/user发送请求
		String retmsg = mockMvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON_UTF8) // 设置请求的contentType
				.param("userName", "Toby.li").param("role", "superAdmin")

				// 分页相关参数，Spring会自动区分开查询条件对象和分页参数对象
				.param("page", "1") // 第几页
				.param("size", "10") // 每页多少行
				.param("sort", new String[] { "role,asc", "userName,desc" }) // 排序，一个排序传String，多个排序传数组
		)
				// 期望处理成功（isOk()会判断HTTP 200响应码），到不到期望就会报错
				.andExpect(status().isOk())
				// .andExpect(jsonPath("$.length()").value(3)) //判断返回的是json数组，且数组长度是3
				.andReturn().getResponse().getContentAsString();
		System.out.println("=selectAll==retmsg===" + retmsg);
	}

	@Test
	public void whenGetUserInfoSuccess() throws Exception {
		String retmsg = mockMvc.perform(get("/user/3").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(jsonPath("$.userName").exists()) // .value("user"))
				.andReturn().getResponse().getContentAsString();
		System.out.println("=getUserSuccess==retmsg===" + retmsg);
	}

	@Test
	public void whenGetUserInfoFail() throws Exception {
		String retmsg = mockMvc.perform(get("/user/abc").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError()) // 期望返回的是404等找不到的错误
				.andReturn().getResponse().getContentAsString();
		System.out.println("=fail==retmsg===" + retmsg);
	}

	@Test
	public void whenCreateUserSuccess() throws Exception {// 测试创建用户是否成功
		Date date = new Date();
		// 模拟一个json字符串（可以通过前面的测试用例的输出复制一个json来用），这里注意不要传id，因为新增时还没ID
		String requestBody = "{\"userName\":\"user12\",\"password\":\"123\",\"rolename\":\"user\",\birthDay\":"
				+ date.getTime() + "}";
		String result = mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)// 直接param来设置参数，控制器不用@RequestBody也能解析，但是用content设字符串就不行
		).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andReturn().getResponse()
				.getContentAsString(); // 保存成功后要有ID
		System.out.println("==create==result====" + result);
	}

	@Test
	public void whenHasDataField() throws Exception { // 测试日期格式
		Date date = new Date();
		System.out.println("====birthDay==" + date.getTime());
		String requestBody = "{\"userName\":\"cdcdcdcd\",\"password\":\"123\",\"rolename\":\"user\",\"birthDay\":"
				+ date.getTime() + "}";
		String result = mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)// 直接param来设置参数，控制器不用@RequestBody也能解析，但是用content设字符串就不行
		).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()) // 保存成功后要有ID
				.andReturn().getResponse().getContentAsString();
		System.out.println("====result=日期处理回传后===" + result);
	}

	@Test
	public void whenUpdateUserSuccess() throws Exception {
		Date date = new Date(
				LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		System.out.println("====birthDay==" + date.getTime());
		// 模拟一个json字符串（可以通过前面的测试用例的输出复制一个json来用），这里需要ID
		String requestBody = "{\"id\":3,\"userName\":\"user\",\"password\":\"toUpd3333bddd\",\"rolename\":\"user\",\"birthDay\":"
				+ date.getTime() + "}";
		String result = mockMvc.perform(put("/user/3") // put用来更新
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)// 直接param来设置参数，控制器不用@RequestBody也能解析，但是用content设字符串就不行
		).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println("==update==result====" + result);

		// 将password设置的超过10个字符，会触发@Valid的校验失败错误
	}

	@Test
	public void whenDeleteSuccess() throws Exception {
		// 模拟一个删除请求，这里需要ID
		mockMvc.perform(delete("/user/2") // put用来更新
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		System.out.println("==删除成功====");
	}

	@Test
	public void whenFileUploadSuccess() throws Exception {
		//这里的文件上传内容是模拟的，其中第一个参数是上传到后端的参数名（后端一定要用这个名字获取否则为空）
		//第二个参数是文件名，第三个参数是类型，第四个参数是文件内容（必须是byte数组，这里直接将字符串转换成内容）
		MockMultipartFile mockFile = new MockMultipartFile("txtFile", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE,
				"hello fileupload".getBytes("UTF-8"));
		String result = mockMvc.perform(multipart("/file").file(mockFile)).andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
		
		System.out.println("文件上传返回结果：" + result);
	}
}
