package com.vue.dao;

import com.vue.pojo.User;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
   User selectname(User user);
   Integer insertuser(User user);
   //list
   Integer addChangeDatas(@Param("list")List<User> list);
   Integer insertuser2(User user);
   List<User> alluser();
   Integer deleteid(String username);
   List<User> find(String username);
   User findid(Integer id);
   Integer update(User user);
   User findname(String username);
}
