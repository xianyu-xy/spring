package com.vue.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.vue.pojo.Blog;
import com.vue.pojo.Pinglun;
@Repository
public interface BlogDao {
	Integer insertblog(Blog blog);
	List<Blog> allblog();
	Blog selecttitle(String blogtitle);
	Integer insertpinglun(Pinglun pl);
	List<Pinglun> selectcontent(String contenttitle);
	List selects(String title);
	Pinglun getplid(int getplid);

}
