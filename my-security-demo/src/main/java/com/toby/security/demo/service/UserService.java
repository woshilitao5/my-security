package com.toby.security.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toby.security.core.entity.IUser;
import com.toby.security.core.service.IUserService;
import com.toby.security.demo.entity.User;
import com.toby.security.demo.repository.UserRepository;

@Service	//标记为service，里面也包括了作为一个bean公开等操作
@Transactional //开启spring事务
public class UserService implements IUserService {
	@Autowired
	private UserRepository userRepository;
	
	public List<User> findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	public List<User> findAll(){
		return this.userRepository.findAll();
	}
	
//	public List<User> findAll(){
//		return this.userRepository.;
//	}
	
	public User save(User user) {
		return this.userRepository.save(user);
	}
	
	public User findOne(Long id) {
		return this.userRepository.findById(id).get();
	}
	
	public void deleteById(Long id) {
		this.userRepository.deleteById(id);
	}

	@Override
	public IUser findUserByUserName(String userName) {
		List<User> users = userRepository.findByUserName(userName);
		return users.get(0);
	}

	@Override
	public IUser findUserByMobile(String mobile) {
		return userRepository.findByMobile(mobile);
	}
}
