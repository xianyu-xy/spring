package com.vue.service;

import com.vue.dao.UserDao;
import com.vue.pojo.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface userservice {
    User selectname(User user);
    String insert(User user);
    List<User> alluser();
    String deleteids(String username);
    List<User> find(String username);
    User findid(Integer id);
    Integer update(User user);
    User findname(String username);
    int addEmp(List<User> list);
}
