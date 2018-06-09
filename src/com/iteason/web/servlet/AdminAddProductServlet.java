package com.iteason.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;


import com.iteason.domain.Category;
import com.iteason.domain.Product;
import com.iteason.service.AdminProductService;
import com.iteason.service.ProductService;
import com.iteason.utils.GetUUID;

public class AdminAddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//后台添加商品：收集数据封装成Product实体类，创建磁盘文件储存上传的图片
		Product product = new Product();
		HashMap<String, Object> map = new HashMap<String,Object>();
		
		try {
			//获得磁盘工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//将工厂绑定在文件上传核心类中
			ServletFileUpload upload = new ServletFileUpload(factory);
			//设置编码表
			upload.setHeaderEncoding("UTF-8");
			//解析请求
			List<FileItem> parseRequest = upload.parseRequest(request);
			//遍历集合
			for(FileItem item:parseRequest){
				//判断item是否为文件项
				if(item.isFormField()){
					//为表单项
					String formName = item.getFieldName();
					String value = item.getString("UTF-8");
					//用Map集合将其封装 
					 map.put(formName, value);
				}else{
					//文件项
					String fieldName = item.getName();
					InputStream in = item.getInputStream();//获得文件输入流
					String path = this.getServletContext().getRealPath("upload");//获得绝对路径
					FileOutputStream out = new FileOutputStream(path+"/"+fieldName);
					IOUtils.copy(in,out);//输入流复制给输出流
					out.close();
					in.close();   //关流
					item.delete();
					
					map.put("pimage", "upload/"+fieldName);
				}
			}
			
			//封装到Product中
			BeanUtils.populate(product, map);
			//product对象封装并不完全
			product.setPid(GetUUID.getUUID());
			//格式化时间,并封装
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(new Date());
			product.setPdate(date);
			product.setPflag(0);
			//category
			Category category = new Category();
			category.setCid(map.get("cid").toString());
			product.setCategory(category);
			
			//将product传递给service层
			AdminProductService service = new AdminProductService();
			service.addProduct(product);
			
		} catch (FileUploadException | IllegalAccessException | InvocationTargetException | SQLException e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/admin?method=findAllProductUI").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}