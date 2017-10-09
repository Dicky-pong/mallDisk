package com.pwx.mall.disk.user.service;

import com.pwx.mall.disk.user.entity.Users;


/**
 * 
 * <用户信息Service接口>
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * tanchao 	1.0  		2015年6月4日 	Created
 *
 * </pre>
 * @since 1.
 */
public interface IUserService {
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
    

}
