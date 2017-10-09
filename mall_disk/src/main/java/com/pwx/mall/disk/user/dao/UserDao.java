package com.pwx.mall.disk.user.dao;

import com.pwx.mall.disk.user.entity.Users;


/**
 * 
 * <用户接口类>
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * tanchao 	1.0  		2015年6月4日 	Created
 *
 * </pre>
 * @since 1.
 */
public interface UserDao {
	 
    /**
     * 
     * 根据商户登录名查询商户信息.
     * @param username
     * @return
     */
    Users findUserByuserName(String username);
    
    /**
     * 
     * 根据商户登录名和pass查询商户信息进行验证.
     * @param username
     * @param password
     * @return
     */
    Users findUserByuserName(String username,String password);
    
    /**
     * 
     * 根据商户id.查询商户信息
     * @param id
     * @return
     */
    Users findUserById(String id);

    /**
     * 
     * TODO 新增商户信息.
     * @param user
     * @return
     * @throws Exception
     */
    String addUser(Users user) throws Exception;
    
    /**
     * 
     * TODO 给商户分配权限.
     * @param userid
     * @param roleid
     */
    String addUserRole(String userid,String roleid);
    
}
