package com.damoyeo.healthyLife.bean;



import java.util.ArrayList;
import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member{

	long id;
	String username;
	String password;
	String email;
	String menuType;
	String sportType;
	String type;
	String token;
	String role;

	

	public void setMenuType(String menuType) {
		this.menuType = menuType;
		if(this.menuType == null) {
			this.menuType = "테스트를 통해 나의 식단유형을 알아보세요!";
		}
	}


	public void setSportType(String sportType) {
		this.sportType = sportType;
		if(this.sportType == null) {
			this.sportType = "테스트를 통해 나의 운동유형을 알아보세요!";
		}
	}

	

}
