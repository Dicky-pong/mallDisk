package com.pwx.mall.disk.user.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pwx.mall.disk.common.baseUtil.BaseDao;
import com.pwx.mall.disk.user.dao.UserDao;
import com.pwx.mall.disk.user.entity.User_Role;
import com.pwx.mall.disk.user.entity.Users;


/**
 * 
 * <userDao的实现类>
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * tanchao 	1.0  		2015年6月4日 	Created
 *
 * </pre>
 * @since 1.
 */
@Service
public class UserDaoImpl extends BaseDao implements UserDao {

    @SuppressWarnings("unchecked")
	@Override
	public Users findUserByuserName(String username,String password) {
		
	    String hql = "select e from Users e where 1 = 1 ";
	    Map<String,String> maps = new HashMap<String, String>();
	    if(username != null && !"".equals(username)){
	        hql = hql+" and e.loginName = :username ";
	        maps.put("username", username);
	    }
	    if(password != null && !"".equals(password)){
	        hql = hql+" and password = :password ";
	        maps.put("password", password);
	    }
	    //根据不同条件进行查询返回
        List<Users> users = (List<Users>) super.queryObject(hql,maps); 
		
		if(users!=null && !users.isEmpty() && users.size()>0){
		    super.getHibernateTemplate().evict(users.get(0));
			return users.get(0);
		}else{
			return null;
		}
	}

    @Override
    public Users findUserById(String id) {
    	Users users = (Users) getById(Users.class, id);
        super.getHibernateTemplate().evict(users);
        return users;
    }

    @Override
    public Users findUserByuserName(String username) {
    	String hql = "SELECT u FROM Users u WHERE u.loginName = :loginName";
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginName", username);
        //根据不同条件进行查询返回
        List<Users> users = (List<Users>) super.queryObject(hql,map); 
		
		if(users!=null && !users.isEmpty() && users.size()>0){
		    super.getHibernateTemplate().evict(users.get(0));
			return users.get(0);
		}else{
			return null;
		}
    }
    
    
    @Override
    public String addUser(Users user) {
        String pKey = super.add(user);
        return pKey;
    }
    
    
    @Override
    public String addUserRole(String userid,String roleid) {
//        throw NullPointerException;
        User_Role user_role = new User_Role();
        user_role.setUserId(userid);
        user_role.setRoleId(roleid);
        String pKey = super.add(user_role);
        return pKey;
    }
}
