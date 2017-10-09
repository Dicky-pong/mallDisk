package com.pwx.mall.disk.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pwx.mall.disk.user.dao.UserDao;
import com.pwx.mall.disk.user.entity.Users;
import com.pwx.mall.disk.user.service.IUserService;

/**
 * <用户实现接口Service>
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * tanchao 	1.0  		2015年6月4日 	Created
 * 
 * </pre>
 * @since 1.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource
	private UserDao userDao;

	public Users findUserByuserName(String username, String password) {
		return userDao.findUserByuserName(username, password);
	}

	@Override
	public Users findUserById(String id) {
		return userDao.findUserById(id);
	}

	@Override
	public Users findUserByuserName(String username) {
		return userDao.findUserByuserName(username);
	}


}
