package com.toby.security.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;
import com.toby.security.core.entity.IUser;
import com.toby.security.demo.validator.MyConstraint;

@Entity
@Table(name="security_user")
public class User implements IUser {
	public interface UserSimpleView{};
	public interface UserDetailView extends UserSimpleView{};
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(length=30)
	@MyConstraint(message="用户名已存在")
	private String userName;
	
	@Length(max=10,message="密码长度最长不能超过10")	//校验，最长不能超过10
	@NotBlank	//校验，不能为空
	@Column(length=30)
	private String password;
	private String rolename;
	
//	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="created", columnDefinition="timestamp default current_timestamp",insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;	//创建时间，将插入和更新都屏蔽掉，这样就会使用数据库里面设置的规则
	//但是有个问题，保存成功后返回的对象中createTime是null，但是数据库其实是有数据的，如果修改完是重查还好，如果直接用返回值就要处理了
	
	@Past(message="生日必须是过去的时间")
	private Date birthDay;
	
	private String mobile;
	
	@JsonView(UserSimpleView.class)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@JsonView(UserSimpleView.class)
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	@JsonView(UserSimpleView.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@JsonView(UserSimpleView.class)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@JsonView(UserDetailView.class)
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	@JsonView(UserDetailView.class)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@JsonView(UserSimpleView.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
