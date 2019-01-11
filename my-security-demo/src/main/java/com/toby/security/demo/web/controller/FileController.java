package com.toby.security.demo.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toby.security.demo.dto.FileInfo;

@RestController
@RequestMapping("/file")
public class FileController {
	private String targetFold = "F:\\NewLife\\3. 工具、技术\\技术相关\\安全与权限相关\\Spring Security技术栈开发企业级认证与授权\\my-security\\my-security-demo\\src\\main\\java\\com\\toby\\security\\demo\\web\\controller";
	/**
	 * 这里的@RequestParam("txtFile")可以不要，但是不要的时候，要求参数名要跟前端传过来的参数名一致，不然就是Null
	 * @param txtFile
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	public FileInfo upload(@RequestParam("txtFile") MultipartFile mulfile) throws Exception {
		System.out.println(mulfile.getName());	//看一下mulfile里面有哪些东西可以获取
		System.out.println(mulfile.getOriginalFilename());
		System.out.println(mulfile.getSize());
		
		//下面代码是为了创建一个服务器本地的文件
		File serverFile = new File(targetFold, new Date().getTime() + ".txt");
		
		mulfile.transferTo(serverFile);	//将上传上来的文件存到本地去
		
		return new FileInfo(serverFile.getAbsolutePath());	//将服务器文件真实路径返回给前端
	}
	
	@GetMapping("{id:\\d+}")
	public void download(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//JDK1.8新语法，用这种方式会自动释放资源
		try ( InputStream input = new FileInputStream(new File(targetFold ,id + ".txt"));
				OutputStream output = response.getOutputStream(); ){
			//设置响应类型为文件下载的类型
			response.setContentType("application/x-download");
			//设置下载时的文件名
			response.addHeader("Content-Disposition", "attachment;filename=youDownload.txt");
			
			IOUtils.copy(input, output);//用commons-io包里面的工具类来将输入流复制到输出流
			output.flush();
		}
	}
}
