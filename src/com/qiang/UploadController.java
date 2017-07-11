package com.qiang;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;



@Controller
@RequestMapping("/upload")
public class UploadController {
	//设置好账号的ACCESS_KEY和SECRET_KEY
	  String ACCESS_KEY = "Le8jyHg71J7UuV***KYUmF-UMbJOZgmLMO"; //这两个登录七牛 账号里面可以找到
	  String SECRET_KEY = "VMO5cIW***ifokRkefGE_WWxKmC2n2d";

	  //要上传的空间
	  String bucketname = "usercenterfilesccw"; //对应要上传到七牛上 你的那个路径（自己建文件夹 注意设置公开）
	  //上传到七牛后保存的文件名
	  String key = "test.doc";  
	  //密钥配置
	  Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	  //创建上传对象
	  Configuration conf=new Configuration();
	  
	  UploadManager uploadManager;
	  {
		  uploadManager=new UploadManager(conf);
	  }

	/**上传到本地服务器
	 * @param request
	 * @param response
	 */
	@RequestMapping("/test.do")
	public void test(HttpServletRequest request,HttpServletResponse response){
		String savePath = request.getSession().getServletContext()
                .getRealPath("");
		// 得到项目的工作目录
		savePath = savePath + "/uploads/";
		File f1 = new File(savePath);
		// 如果没有的话就创建目录
		if (!f1.exists()) {
			f1.mkdirs();
		}
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return;
		}
		Iterator<FileItem> it = fileList.iterator();
		String name = "";
		String extName = "";
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {

				// 解析文件
				name = item.getName();
				long size = item.getSize();
				String type = item.getContentType();
				if (name == null || name.trim().equals("")) {
					continue;
				}
				// 得到文件的扩展名
				if (name.lastIndexOf(".") >= 0) {
					extName = name.substring(name.lastIndexOf("."));
				}
				File file = null;
				do {
					// 利用客户端IP+时间+三位随机数字生成非重复文件名：
					name = new IPTimeStamp().getIPTimeStampRandom();
					file = new File(savePath + name + extName);
				} while (file.exists());
				File saveFile = new File(savePath + name + extName);
				try {
					item.write(saveFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		try {
			response.getWriter().print(name + extName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**上传到七牛云端
	 * @param request
	 * @param response
	 */
	@RequestMapping("testQiNiu.do")
	public void testQN(HttpServletRequest request,HttpServletResponse response){
		List<FileItem> fileList = null;
		try {
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return;
		}
		Iterator<FileItem> it = fileList.iterator();
		try {
			while (it.hasNext()) {
				FileItem item = it.next();
					Response res = uploadManager.put(item.getInputStream(), key, getUpToken(), null, "application/zip");
			System.out.println(res.getInfo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	  //简单上传，使用默认策略，只需要设置上传的空间名就可以了
	  public String getUpToken(){
	      return auth.uploadToken(bucketname);
	  }
}
