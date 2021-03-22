package com.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vue.dao.BlogDao;
import com.vue.pojo.Blog;
import com.vue.pojo.Pinglun;
@Service
public class Blogserviceimp implements blogservice{
@Autowired BlogDao blogdao;
	@Override
	public String insert(Blog b) {
		// TODO Auto-generated method stub
		int result=blogdao.insertblog(b);
		if(result>0){
			return "success";
		}else{
			return "null";
		}
		
	}
	@Override
	public List<Blog> allblog() {
		// TODO Auto-generated method stub
		List<Blog> b=blogdao.allblog();
		return b;
	}
	@Override
	public Blog selectblog(String title) {
		// TODO Auto-generated method stub
		Blog result=blogdao.selecttitle(title);
			return result;
	}
	@Override
	public String insertpinglun(Pinglun pl) {
		// TODO Auto-generated method stub
		int result=blogdao.insertpinglun(pl);
		if(result>0){
			return "success";
		}else{
			return "fail";
		}
	}
	@Override
	public List<Pinglun> selectcontent(String content) {
		// TODO Auto-generated method stub
		List<Pinglun> list=blogdao.selectcontent(content);
		return list;
	}
	@Override
	public List<Pinglun> selects(String content) {
		// TODO Auto-generated method stub
		List<Pinglun> list=blogdao.selects(content);
		return list;
	}
	@Override
	public Pinglun getplid(int plid) {
		// TODO Auto-generated method stub
		Pinglun pl=blogdao.getplid(plid);
		return pl;
	}
}
