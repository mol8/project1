package com.project.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.dao.registry.RegistryDAO;
import com.project.pojo.Users;



public class UserDetailsServiceImpl implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//spring security compara el password introducido por el usuario
		
		//Usuarios user = RegistryDAO.getUsuariosDAO().getUsuarioByUserName(username);
		Users user = RegistryDAO.getUsersDAO().getUserByUserName(username);
		
		if(user == null){
			return null;
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.
				User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
		return userDetails;
	}

}
