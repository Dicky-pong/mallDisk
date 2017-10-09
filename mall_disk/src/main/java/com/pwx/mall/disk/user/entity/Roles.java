package com.pwx.mall.disk.user.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 
 * <用户角色类>
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * tanchao 	1.0  		2015年6月4日 	Created
 *
 * </pre>
 * @since 1.
 */
@Entity
@Table(name="ROLES")
public class Roles implements Serializable {

	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")   @GeneratedValue(generator = "generator")   // ������
    @Column(name = "id") 
    private int id;
    
    @Column(name="roleCode")
    private String roleCode ;
    
    @Column(name="despripe")
    private String despripe ;
    
    @ManyToMany
    @JoinTable(name="user_role" , joinColumns = {  
            @JoinColumn(name = "roleid")  
    }, inverseJoinColumns = {@JoinColumn(name="userid")})   
	private List<Users> users = new ArrayList<Users>(); 
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDespripe() {
		return despripe;
	}

	public void setDespripe(String despripe) {
		this.despripe = despripe;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}
    
}
