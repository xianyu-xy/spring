package com.vue.service;

import com.vue.dao.UserDao;
import com.vue.pojo.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class userserviceimp implements userservice {
@Autowired
    UserDao userDao;
/*@Autowired
 RedisTemplate redistemplate;*/

    @Override
    public User selectname(User user) {
        User user1=userDao.selectname(user);
        return user1;
    }

    @Override
    public String insert(User user) {
        int result=userDao.insertuser(user);
        if(result>0){
            return "success";
        }else{
            return "null";
        }
        }

	@Override
	public List<User> alluser() {
		// TODO Auto-generated method stub
		List<User> list=userDao.alluser();
		return list;
	}

	public String deleteids(String username) {
		// TODO Auto-generated method stub
		
		Integer in=userDao.deleteid(username);
		if(in>0){
			return "success";
		}else{
			return "fail";
		}
		
	}

	@Override
	public List<User> find(String username) {
		// TODO Auto-generated method stub
		List<User> list=userDao.find(username);
		return list;
	}

	@Override
	public User findid(Integer id) {
		// TODO Auto-generated method stub
		User user=userDao.findid(id);
		return user;
	}

	@Override
	public Integer update(User user) {
		// TODO Auto-generated method stub
		int result=userDao.update(user);
		return result;
	}

	@Override
	public User findname(String username) {
		// TODO Auto-generated method stub
		User u=userDao.findname(username);
		return u;
	}

	public int addEmp(List<User> list) {
		// TODO Auto-generated method stub
		int results=0;
		for(int i=0;i<list.size();i++){
			int result=userDao.insertuser(list.get(i));
			   if(result>0){
				   results=1;
		        }else{
		        	 results=0;
		       }
		}
		return  results;
	     
	}

}
