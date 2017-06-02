package com.ssm.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mjmtest.Test;

import org.apache.commons.httpclient.HttpState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssm.dao.IUsersMapper;
import com.ssm.entity.User;
import com.sun.corba.se.impl.ior.GenericIdentifiable;



@Controller
@RequestMapping("/usert")
public class UserController {
	//private static final List<List<String>> ArrayList <ArrayList<String>> = null;
	//璋冪敤UserMapp鎺ュ彛鎿嶄綔鐨勫姛鑳�
	IUsersMapper userMapper=null;
	public IUsersMapper getUserMapper() {
		return userMapper;
	}
	@Autowired
	public void setUserMapper(@Qualifier("usersModel")IUsersMapper userMapper) {
		this.userMapper = userMapper;
	}
		
	@RequestMapping("/login")
	@ResponseBody  
	public Map<String, Object> UsersLogin(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("username")String usn,
			@RequestParam("pwd")String pwd) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//System.out.println(usn+"  //  "+pwd);		

		Map<String, Object> map=new HashMap<String, Object>();
		User user=userMapper.SelectUserByLogin(usn, pwd);		
		if(user!=null){
			System.out.println(user.getUid()+"  +");
			
			map.put("id", user.getUid());
			map.put("name",user.getUname());
			 
		}
		else{
			map.put("id", 0);
		}
		return map;
	}
	@RequestMapping("/sinfo1")
	@ResponseBody
	public List<Map<String, Object>> TestMethod(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("sid")String sid,
			@RequestParam("stype")String stype) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("id", "100");
		map.put("name","100");
		map.put("pwd", "100");
		
		res.add(map);
		System.out.println("put");
		
		return res;
	}

	@RequestMapping("/sinfo")
	@ResponseBody 
	public String Sinfo(HttpServletRequest request, HttpServletResponse response,@RequestParam("sid")String sid,
			@RequestParam("stype")String stype) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");//html页面请求的编码格式，国际标准中文编码格式
		response.setCharacterEncoding("utf-8");//往往异步机制返回的页面编码格式，中国标准的中文编码格式
	
		Test my=new Test();
		
		
		String sr=my.sendPost("http://jieone.com/demo/stock/data/stock.php?callback=jQuery17206472256606980518_1495438225734&Action=minute&stockID="+sid+"&stockType="+stype, "");
		String[] astr1 = sr.split(",");
		int ii=0;
		String strr ="";
		String[] r_str= new String [1000];
		int count =0;
        for (int i = 0; i < astr1.length-1; i++) {
        	if(ii==6)
        	{
        		String[] astr2 = astr1[i].split(":");
        		String str1 = astr2[0].substring(0,astr2[0].length()-2);
        		//System.out.println();
        		String str2 = astr2[0].substring(astr2[0].length()-2,astr2[0].length())+":"+astr2[1];

        		//String str2 = astr2[0].substring(astr2[0].length()-2,astr2[0].length())+":"+astr2[1];
        		//System.out.println(str1);
        		strr = strr+','+str1;
        		r_str[count] = strr;
       		//System.out.println(r_str[count]);
        		count++;
        		strr = str2;
        		//System.out.println(str2);
        		ii=0;
        	}else{
        		if(i==0)
        		{
        			strr = astr1[i];
        		}else{
        			strr = strr+','+astr1[i];
       		}
        	}
            //System.out.println(sourceStrArray[i]);
        	ii++;
        }
        String strc="";
        for (int i = 0; i < count; i++) {
        	strc = strc + r_str[i];
        	strc = strc + "\n";
        }
        //System.out.println(strc);
        //System.out.println("strc");
        return strc;
		//return r_str[0];
		//return "aaaa";
	}
	@RequestMapping("/kinfo")
	@ResponseBody  
	public List<List<String>> Kinfo(HttpServletRequest request, HttpServletResponse response,@RequestParam("code")int code,
			@RequestParam("start")int start,@RequestParam("end")int end) throws UnsupportedEncodingException{
		int iStockCode = code,iCurStart=start, iCurEnd=end;
		String strUrl = null;
		String str;
		if (iStockCode>=600000)
		{
			str = String.format("http://quotes.money.163.com/service/chddata.html?code=0%06d&start=%d&end=%d&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;VOTURNOVER;VATURNOVER", iStockCode, iCurStart, iCurEnd);

		}
		else
		{
			str = String.format("http://quotes.money.163.com/service/chddata.html?code=1%06d&start=%d&end=%d&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;VOTURNOVER;VATURNOVER", iStockCode, iCurStart, iCurEnd);
		}
		//System.out.println(str);
		Test my=new Test();
		String sr=my.sendPost(str, "");
		String[] k_str = sr.split(",");
		
		String[] k_str1 = new String[10000];;
		int j=0;
		//System.out.println(sr);
		for(int i =0,o=0;i<k_str.length;i++)
		{
			j++;
			if(j==10)
			{
				if(i==k_str.length-1)
				{
					
					k_str1[o] =k_str[i];
					
					
					o++;
					j=1;
				}else{
					String s1 = k_str[i].substring(k_str[i].length()-10,k_str[i].length());
					String s2 = k_str[i].substring(0,k_str[i].length()-10);
					//System.out.println(i);
					//System.out.println(s1);
					k_str1[o] = s2;
					o++;
					//System.out.println(s2);
					k_str1[o] = s1;
					o++;
					j=1;
				}
				
			}else{
				if(j==2)
				{
					k_str1[o] =k_str[i];
				}else{
					k_str1[o] = k_str[i];
				}
				
				o++;
			}
			//System.out.println(k_str[i]);
		}
		System.out.println(k_str1.length);
		int l=k_str.length/9;
		List<List<String>> arr_str = new ArrayList<List<String>>();
		
		for(int i =1,x=0,y=0;i<k_str.length/9;i++)
		{
			List<String> arr_str1 = new ArrayList<String>();
			for(int o=0;o<10;o++)
			{
				arr_str1.add(k_str1[i*10+o]);
			}
			arr_str.add(arr_str1);
			//System.out.println(k_str1[i]);
			//arr_str[o] = arr_str[o]+
			//System.out.println(i);
		}
		//System.out.println(k_str.length/9*10);
		for(int i =0;i<1;i++)
		{
			for(int o =0;o<10;o++)
			{
				//System.out.println(arr_str[i][o]);
			}
		}
		 //System.out.println(Arrays.deepToString(arr_str));
		return arr_str;
	}
	@RequestMapping("/loginAndroid")
	@ResponseBody  
	public String AndroidLogin(HttpServletRequest request, HttpServletResponse response,@RequestParam("username")String usn,
			@RequestParam("pwd")String pwd) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		Map<String, Object> map=new HashMap<String, Object>();
		User user=userMapper.SelectUserByLogin(usn, pwd);
		
		if(user!=null){
			System.out.println(user.getUid()+"  +");
			
			map.put("id", user.getUid());
			map.put("name",user.getUname());
		}
		else{
			map.put("id", 0);
		}
				
		return map.toString();
	}
	
	//璇ユ柟娉曡姹傜殑url: http://127..../SSMdemo/usert/searchUser.action
	@RequestMapping("/searchUser")
	@ResponseBody
	public List<Map<String, Object>> FindUsersAll(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		List<User> listUser=this.userMapper.SelectUserAll();
		Map<String, Object> map=null;
		
//<div class='btn btn-primary' data-toggle='modal' data-target='#addModal'>Edit</div>
		
		for (User user : listUser) {
			map=new HashMap<String, Object>();
			map.put("id", user.getUid());
			map.put("name",user.getUname());
			map.put("pwd", user.getUpwd());


			res.add(map);
		}
		//json  [{},{},{}]
		return res;
	}
	@RequestMapping("/searchObject")
	@ResponseBody
	public List<Map<String, Object>> FindObjectToAndroid(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");		
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();	
		Map<String, Object> map=null;
//{"date":"2017...","name":"xxx","id":3,"glogo":"ww.jpg","gradetext":"内容...."}
		for (int i=0;i<6;i++) {
			map=new HashMap<String, Object>();
			map.put("id", i);
			map.put("name","jerry"+i);
			map.put("date","2017"+i);
			map.put("glogo","t"+(i+1)+".gif");
			map.put("gradetest","td"+i);
			//超链a 是json数据被异步显示到页面中，添加title属性值是该条记录的users主键，每一条记录的title值是不同的
			res.add(map);
		}
		//json  [{},{},{}]
		return res;
	}
	//Springmvc下载二进制文件的实质是ResponseEntity<byte []>对象，包裹了字节流对象
	@RequestMapping("/download1")
	@ResponseBody
	public ResponseEntity<byte []> DownloadImg(@RequestParam("logo")String logo)
			throws Exception{
		System.out.println("图片:"+logo);
		//创建路径，创建File对象操作
		String path="E:/Android/images/"+logo;
		File file=new File(path);
		//创建文件流
		InputStream is=new FileInputStream(file);
		//创建输出的自己数组
		byte[] by=new byte[is.available()];
		//读取文件流
		is.read(by);
	    HttpHeaders http=new HttpHeaders();
	    //设置下载二进制文件类型
//	             "Content-Disposition", "attchement;filename=" + file.getName()
	    http.add("Content-Disposition", "attchement;filename=" + file.getName());
	    HttpStatus status=HttpStatus.OK;
	    ResponseEntity<byte []> entity=new ResponseEntity<byte[]>(by,http,status);
		return entity;
	}
	@RequestMapping("/download")
	@ResponseBody
	public ResponseEntity<byte []> DownloadImg1(@RequestParam("logo")String logo,
			HttpServletRequest request,	HttpServletResponse response)
			throws Exception{
		System.out.println("图片:"+logo);
		//创建路径，创建File对象操作
//		String path="E:/Android/images/"+logo;
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		//设置返回的二进制文件流，不是网页
		response.setContentType("application/x-msdownload");
		//ulogo是赋值下载图片的图片名称 包含扩展名
//		String logo=request.getParameter("ulogo");	        
//	    System.out.println("image:"+logo);	       
	    response.setHeader("Content-Disposition", "attachment;filename="+java.net.URLEncoder.encode(logo,"utf-8"));
	        //获取下载文件的真实路径
	    String filename="E:/Android/images/"+logo;//+filename	          
	          //创建文件输入流
	    FileInputStream fis=new FileInputStream(filename);
	        //创建缓冲输入流
	    BufferedInputStream bis=new BufferedInputStream(fis);
	        //获取响应的输出流
	    OutputStream  os=response.getOutputStream();
	        //创建缓冲输出流
	    BufferedOutputStream bos=new BufferedOutputStream(os);	          
	        //把输入流的数据写入到输出流
	    byte[] b=new byte[1024];
	    int len=0;
	    while((len=bis.read(b))!=-1){
	          bos.write(b, 0, len);
	    }
	    bos.close();
	    bis.close();
		return null;
	}
	
	@RequestMapping("/uploadFile")
	@ResponseBody
	public String uploadFile(@RequestParam("file")MultipartFile file, 
			@ModelAttribute("users")User users, 
			HttpServletRequest request) throws IllegalStateException, IOException {
		String filePath = request.getSession().getServletContext().getRealPath("/") + "/css/"
				+ file.getOriginalFilename();
		// 杞瓨鏂囦欢
		file.transferTo(new File(filePath));
		// 涓婁紶鐨勬枃浠跺悕
		String filename = file.getOriginalFilename();
		System.out.println("fff " + filename + "/" +users.getUname());

		String contextpath = request.getScheme() +"://" + 
		              request.getServerName()  + ":" +
				      request.getServerPort() +request.getContextPath();
//		System.out.println("path:"+request.getRequestURL());
//		System.out.println("path1:"+request.getRequestURI());
//		String pa=request.getServletPath();
//		System.out.println("path2:"+pa);
//		System.out.println("path2: "+pa.substring(pa.lastIndexOf("/")+1,pa.indexOf(".action")));
//		System.out.println("path3:"+request.getQueryString());

		return "success";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
