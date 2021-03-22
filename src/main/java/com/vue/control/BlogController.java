package com.vue.control;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.catalina.util.URLEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vue.pojo.Blog;
import com.vue.pojo.Pinglun;
import com.vue.result.Result;
import com.vue.service.Blogserviceimp;
@Controller
@CrossOrigin
public class BlogController {
	@Autowired Blogserviceimp blogserviceimp;
	Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    //发布博客
	@CrossOrigin
	@PostMapping("api/fabublog")
	@ResponseBody
	public Result blog(@RequestBody Blog b){
		String bloguser=b.getBloguser();
		String blogtitle=b.getBlogtitle();
		String blogcontent=b.getBlogcontent();
		SimpleDateFormat dt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=dt.format(new Date());
		Blog blog=new Blog();
		blog.setBloguser(bloguser);
		blog.setDate(date);
		blog.setBlogtitle(blogtitle);
		blog.setBlogcontent(blogcontent);
		String result=blogserviceimp.insert(blog);
		if(result.equals("success")){
			logger.info("发布博客成功");
			return new Result(200);
		}else{
			logger.info("发布失败");
			return new Result(400);
		}
	}
	//显示博客信息
	@CrossOrigin
	@GetMapping("api/blogs")
	@ResponseBody
	public List<Blog> allblog(){
		List<Blog> b=blogserviceimp.allblog();
		return b;
	}
	//每条博客详细信息
	@CrossOrigin
	@GetMapping("api/xiangxi")
	@ResponseBody
	public Blog selectblog(String contenttitle) throws UnsupportedEncodingException{
		//前端传过来的参数解码
		String title=java.net.URLDecoder.decode(contenttitle, "UTF-8");
		Blog b=blogserviceimp.selectblog(java.net.URLDecoder.decode(title, "UTF-8"));
		return b;
	}
	
	//评论博客
	@CrossOrigin
	@RequestMapping("api/pinglun")
	@ResponseBody
	public Result pinglun(@RequestBody Pinglun pl){
		String pluser=pl.getPluser();
		String plcontent=pl.getPlcontent();
		SimpleDateFormat dt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=dt.format(new Date());
		String contenttitle=pl.getContenttitle();
		Pinglun pl2=new Pinglun();
		pl2.setPldate(date);
		pl2.setPluser(pluser);
		pl2.setPlcontent(plcontent);
		pl2.setContenttitle(contenttitle);
		String result=blogserviceimp.insertpinglun(pl2);
		if(result.equals("success")){
			return new Result(200);
		}else{
			return new Result(400);
		}
		
	}
	
	//显示评论博客
		@CrossOrigin
		@RequestMapping("api/pingluncontent")
		@ResponseBody
		public List<Pinglun> selectcontent(String contenttitle) throws UnsupportedEncodingException{
			//前端传过来的参数解码
			String title=java.net.URLDecoder.decode(contenttitle, "UTF-8");
			List<Pinglun> list=blogserviceimp.selectcontent(title);
			return list;
		}
		
		//显示评论博客标题内容
		@CrossOrigin
		@RequestMapping("api/pingluncontenttitle")
		@ResponseBody
		public List<Pinglun> selects(String contenttitle) throws UnsupportedEncodingException{
			//前端传过来的参数解码
			String title=java.net.URLDecoder.decode(contenttitle, "UTF-8");
			List<Pinglun> list=blogserviceimp.selects(title);
			return list;
		}
		//回复博客
		@CrossOrigin
		@RequestMapping("api/huifu")
		@ResponseBody
		public Result huifu(@RequestBody Pinglun pl){
			int faid=pl.getPlid();
			logger.info("id"+faid);
			String pluser=pl.getPluser();
			String contenttitle=pl.getPlcontent();
			String plcontent=pl.getPlcontent();
			SimpleDateFormat dt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date=dt.format(new Date());
			Pinglun pinglun=new Pinglun();
			pinglun.setFaid(faid);
			pinglun.setPluser(pluser);
			pinglun.setContenttitle(contenttitle);
			pinglun.setPlcontent(plcontent);
			pinglun.setPldate(date);
			logger.info(pinglun);
			return new Result(200);
		}
		//查找评论
		@CrossOrigin
		@RequestMapping("api/getplid")
		@ResponseBody
		public Pinglun geiplid(int plid){
			logger.info(plid);
			Pinglun pl=blogserviceimp.getplid(plid);
			logger.info(pl);
			return pl;
		}
		
}
