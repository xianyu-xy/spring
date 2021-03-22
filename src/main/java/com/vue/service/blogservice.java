package com.vue.service;

import java.util.List;

import com.vue.pojo.Blog;
import com.vue.pojo.Pinglun;

public interface blogservice {
	String insert(Blog b);
	List<Blog> allblog();
	Blog selectblog(String title);
    String insertpinglun(Pinglun pl);
    List<Pinglun> selectcontent(String content);
    List<Pinglun> selects(String content);
    Pinglun getplid(int plid);
}
