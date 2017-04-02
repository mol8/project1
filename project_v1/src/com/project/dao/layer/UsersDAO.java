package com.project.dao.layer;

import java.util.List;

import com.project.pojo.Users;

public interface UsersDAO {
	
	public Users getUserByUserName(String username);
	
	public boolean addUser(Users user);
	
	public List<Users> getAllUsers();
	
	public boolean deleteUser(Integer iduser);
	
	public boolean updateUser(Users user);
	
	public Users getUserByID(Integer iduser);
	

}
