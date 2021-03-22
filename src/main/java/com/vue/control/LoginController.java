package com.vue.control;


import com.vue.pojo.Blog;
import com.vue.pojo.User;
import com.vue.service.userserviceimp;

import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.vue.result.Poiexcel;
import com.vue.result.Poiexport;
import com.vue.result.Result;
import com.vue.result.excelEmployee;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;



@Controller
@CrossOrigin
public class LoginController {
	Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Value("${user.name}")
	public String name;
    @Autowired
    userserviceimp userservice;
    //登录
    @CrossOrigin//跨域注解
    @PostMapping(value="api/login")
    @ResponseBody//返回字符串格式
    public Result login(@RequestBody User requestUser, HttpSession session){
    	//加密后的登录
      String username=requestUser.getUsername();
      User u=userservice.findname(requestUser.getUsername());
      if(u!=null){
    	  Boolean status=u.getStatus();
    	  Subject subject = SecurityUtils.getSubject();
          UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
          //在登录方法中设置 UsernamePasswordToken 的 rememberMe 属性
          usernamePasswordToken.setRememberMe(true);
        	  if(status){
            	  try {
                      subject.login(usernamePasswordToken);
                      session.setAttribute("user",requestUser);
                      logger.info("账号或密码正确，成功登录");
                      return new Result(200);
                  } 
            	  /*catch (UnknownAccountException e){ //处理我们在Realm中抛出的异常
            		  logger.info("异常，用户不存在");
                      return new Result(400);
                  }*/catch (AuthenticationException e) {
                      logger.info("账号或密码错误");
                      return new Result(400);
                  }
              }else{
            	  logger.info("用户未启用");
            	  return new Result(300);
              }
      }else{
    	  logger.info("用户不存在");
          return new Result(400);
      }
      
      
      
      /*  username= HtmlUtils.htmlEscape(username);
        String password=requestUser.getPassword();
        User user3=new User();
        user3.setUsername(username);
        user3.setPassword(password);
        User u=userservice.selectname(user3);
        if(u==null) {
            logger.error("日志：账号密码错误");
            return new Result(400);
        }else{ 
        	logger.info(u);
           logger.info("日志：账号密码正确");
            session.setAttribute("user",u);
            return new Result(200);
            }*/
        }
          /*退出登录，清理缓存
    核心就是 subject.logout()，默认 Subject 接口是由 DelegatingSubject 类实现，其 logout 方法如下：
    public void logout() {
        try {
            this.clearRunAsIdentitiesInternal();
            this.securityManager.logout(this);
        } finally {
            this.session = null;
            this.principals = null;
            this.authenticated = false;
        }

    }*/
    //后端拦截测试
    @CrossOrigin
    @ResponseBody
    @RequestMapping("api/authentication")
    public String authentication(){
        return "身份认证成功";
    }
    
    @ResponseBody
    @GetMapping("api/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new Result(200);
    }
    
    //注册
    @CrossOrigin//建立跨域的联系
    @PostMapping(value="api/register")
    @ResponseBody//返回字符串
    public Result register(@RequestBody User requestUser){
        String username=requestUser.getUsername();
        username= HtmlUtils.htmlEscape(username);
        String password=requestUser.getPassword();
        boolean status=requestUser.getStatus();
        System.out.println("zhuce:"+status);
        User user3=new User();
        System.out.println("66"+requestUser);
        User u=userservice.findname(username);
        if(u==null){
        	 // 生成盐,默认长度 16 位
        	 String salt=new SecureRandomNumberGenerator().nextBytes().toString();
        	// 设置 hash 算法迭代次数
        	 int times=2;
        	// 得到 hash 后的密码
        	 String encodePassword=new SimpleHash("md5",password,salt,times).toString();
        	 user3.setUsername(username);
             user3.setPassword(encodePassword);
             user3.setSalt(salt);
             user3.setStatus(status);
        	 /*user3.setPassword(password);*/
        	 String result=userservice.insert(user3);
             if(result.equals("success")) {
                 return new Result(200);
             }else{
                 System.out.println("注册失败");
                 return  new Result(400);
             }
        }else{
        	System.out.println("已有用户");
        	return new Result(400);
        }
       
    }
    //修改信息
    @CrossOrigin
    @PostMapping(value="api/update")
    @ResponseBody
    public Result update(@RequestBody User user){
    	int id=user.getId(); 
    	String username=user.getUsername();
    	String password=user.getPassword();
    	boolean status=user.getStatus();
    	System.out.println(status);
    	User u=new User();
    	u.setId(id);
    	u.setUsername(username);
    	u.setPassword(password);
    	u.setStatus(status);
    	int result=userservice.update(u);
    	if(result>0){
    		return new Result(200);
    	}else{
    		return new Result(400);
    }
    }
    @CrossOrigin
    @RequestMapping("api/updates2")
    @ResponseBody
    public Result updates2(String val1) throws UnsupportedEncodingException{
    	String val=java.net.URLDecoder.decode(val1, "UTF-8");
    	String tem[]=val.split("\\.");
    	String name=tem[0];
    	//string装换成boolean
    	boolean status=Boolean.parseBoolean(tem[1]);
    	User u=userservice.findname(name);
    	int id=u.getId();
    	String username=u.getUsername();
    	String password=u.getPassword();
    	User user=new User();
    	user.setId(id);
    	user.setUsername(username);
    	user.setPassword(password);
    	user.setStatus(status);
    	int result=userservice.update(user);
    	if(result>0){
    		logger.info("修改用户状态成功。"+status);
    		return new Result(200);
    	}else{
    		return new Result(400);
    }
    }
    //查询
    @CrossOrigin//和前端跨域相联系
    @RequestMapping(value="api/alluser")
    @ResponseBody
    public List<User> alluser(){
    	List<User> list=userservice.alluser();
    	if(list.size()>0){
    		return list;
    	}else{
    		System.out.println("没有数据");
    		return null;
    	}
    }
    //删除数据
    @CrossOrigin
    @GetMapping(value="api/deleteids")
    @ResponseBody
    public Result deleteids(@RequestParam String username){
    	logger.info("姓名");
    	logger.info("username");
    	String result=userservice.deleteids(username);
    	logger.info(result);
    	if("success".equals(result)){
    		return new Result(200);
    	}else{
    		return new Result(400);
    	}
    }
  //查找数据
	@CrossOrigin
	@RequestMapping(value="api/find")
	@ResponseBody
	public List<User> finds(String username){
		List<User> list=userservice.find(username);
		return list;
	}
	//根据id查询数据
	@CrossOrigin
	@RequestMapping(value="api/findid")
	@ResponseBody
	public User findid(Integer id){  
		User user=userservice.findid(id);
		System.out.println(user);
		return user;
	}
	//批量删除
	@CrossOrigin
	@PostMapping(value="api/findid1")
	@ResponseBody
	//用@RequestBody 接收前端传来的json对象
	public Result findid1(@RequestBody List<User> vals){ 
		if(vals.size()>0){
			for(int i=0;i<vals.size();i++){
				String result=userservice.deleteids(vals.get(i).getUsername());
			}
			 return new Result(200);
		}else{
			 return new Result(400);
		}
   }
	/*int i=0;//定时器
	@CrossOrigin
	@Async
	@RequestMapping(value="api/ceshi")
	@ResponseBody
	@Scheduled(cron= "0/5 * * * * ?")//定时刷新
	public String dingshi(){
		System.out.println(name+i);
			i++;
		return "success";
	}*/
	/**
     * 导入 Excel 数据
     *
     * @param file 你要导入的 Excel 文件
     * @return
     * @throws IOException
     */
	@CrossOrigin
	@PostMapping("api/import")
    @ResponseBody//返回字符串格式
    public Result importData(MultipartFile file) throws IOException {
		String filename=file.getOriginalFilename();
		if(filename.matches("^.+\\.(?i)(xls)$")){
    	// 1.自定义一个工具类拿到要解析的文件并解析成要存储的数据
        List<User> list = excelEmployee.excel2Employee(file);
      if(list.size()>1){
    	// 2.遍历输出你解析的数据格式是否正确
          for (User user : list) {
              System.out.println(user.toString());
          }
          // 3.进行数据库添加操作
          if (userservice.addEmp(list) == 1) {
        	  System.out.println("导入成功！");
        	  return new Result(200);
          }else{
          	System.out.println("数据格式不正确！导入失败！");
          	return new Result(500);
          }
      }else{
      	System.out.println("数据格式不正确！导入失败！");
      	return new Result(500);
      }
      }else{
    		System.out.println("文件格式不正确！导入失败！");
    		return new Result(300);
      }   
	}
	
	@CrossOrigin
	@PostMapping("api/importexcel")
	@ResponseBody
	public Result importexcel(MultipartFile file){
		String filename=file.getOriginalFilename();
		if(filename.matches("^.+\\.(?i)(xls)$")){
			List<User> list1=Poiexcel.poiexcel(file);
			System.out.println(list1);
			if(list1.size()>1){
				   for (User user : list1) {
			              System.out.println(user.toString());
			          }
				   // 3.进行数据库添加操作
			          if (userservice.addEmp(list1) == 1) {
			        	  return new Result(200);
			          }else{
			          	System.out.println("数据格式不正确！导入失败1！");
			          	return new Result(500);
			          }
			}else{
				System.out.println("数据格式不正确！导入失败2！");
		      	return new Result(500);
			}
		}else{
			System.out.println("文件格式错误");
			return new Result(300);
		}
	}
	//导出数据
	@CrossOrigin
	@GetMapping("api/export")
	@ResponseBody
	    public ResponseEntity<byte[]> exportData() {
	    	// 1.这一步就是查询你要导出的数据
	        List<User> employeeList = userservice.alluser();
	        // 2.创建一个 POIUtils 工具类进行导出操作
	        return Poiexport.employee2Excel(employeeList);
	    }
	
	    
}
