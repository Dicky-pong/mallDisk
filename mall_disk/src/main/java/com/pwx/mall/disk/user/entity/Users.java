package com.pwx.mall.disk.user.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;



/**
 * 
 * <用户实体类>
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * tanchao 	1.0  		2015年6月4日 	Created
 * 
 * </pre>
 * @since 1.
 */
@Entity
@Table(name = "USERS")
public class Users implements Cloneable {

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	// 用户名
	@Column(name = "loginName")
	private String loginName;

	// 密码
	@Column(name = "password")
	private String password;

	/**
	 * 用户昵称
	 */
	@Column(name = "nickName")
	private String nickName;

	// 企业地址
	// 省
	@Column(name = "address_province")
	private String addressProvince;
	// 市
	@Column(name = "address_city")
	private String addressCity;
	// 区
	@Column(name = "address_area")
	private String addressArea;
	
	// 详细地址
	@Column(name = "address_detail")
	private String addressDetail;


	// 会员Logo
	@Column(name = "u_logo")
	private String u_logo;

	/**
	 * 0：审核通过 1：未认证,2:锁定状态 3：审核中 4、5：审核失败	
	 */
	@Column(name = "user_state")
	private String state;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate")
	private Date createDate = new Date();

	// 备注
	@Column(name = "remark")
	private String remark;


	// 经度
	@Column(name = "lng")
	private String lng;

	// 纬度
	@Column(name = "lat")
	private String lat;


	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = { @JoinColumn(name = "roleid") })
	private List<Roles> roles = new ArrayList<Roles>();

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAddressProvince() {
		return addressProvince;
	}

	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressArea() {
		return addressArea;
	}

	public void setAddressArea(String addressArea) {
		this.addressArea = addressArea;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getU_logo() {
		return u_logo;
	}

	public void setU_logo(String u_logo) {
		this.u_logo = u_logo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public Users(String id, String loginName, String password, String nickName,
			String addressProvince, String addressCity, String addressArea,
			String addressDetail, String u_logo, String state, Date createDate,
			String remark, String lng, String lat, List<Roles> roles) {
		super();
		this.id = id;
		this.loginName = loginName;
		this.password = password;
		this.nickName = nickName;
		this.addressProvince = addressProvince;
		this.addressCity = addressCity;
		this.addressArea = addressArea;
		this.addressDetail = addressDetail;
		this.u_logo = u_logo;
		this.state = state;
		this.createDate = createDate;
		this.remark = remark;
		this.lng = lng;
		this.lat = lat;
		this.roles = roles;
	}

	public Users() {
		super();
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", loginName=" + loginName + ", password="
				+ password + ", nickName=" + nickName + ", addressProvince="
				+ addressProvince + ", addressCity=" + addressCity
				+ ", addressArea=" + addressArea + ", addressDetail="
				+ addressDetail + ", u_logo=" + u_logo + ", state=" + state
				+ ", createDate=" + createDate + ", remark=" + remark
				+ ", lng=" + lng + ", lat=" + lat + ", roles=" + roles + "]";
	}


	
}
