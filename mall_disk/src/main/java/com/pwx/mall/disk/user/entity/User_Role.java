package com.pwx.mall.disk.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="USER_ROLE")
public class User_Role {
    
    @Id
    @Column(length = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    
    /**
     * 用户ID
     */
    @Column(name="userid")
    private String userId;
    
    /**
     * 角色ID
     */
    @Column(name="roleid")
    private String roleId;   
    
    
    
    public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id;
    }

}
