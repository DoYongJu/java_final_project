package com.damoyeo.healthyLife.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.damoyeo.healthyLife.bean.AdminUser;
import com.damoyeo.healthyLife.dao.AdminUserDAO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginService implements UserDetailsService {
	@Autowired
	AdminUserDAO admindao;

    public LoginService(AdminUserDAO admindao) {
        this.admindao = admindao;
    }

  @Override
    public UserDetails loadUserByUsername(String adminId) throws UsernameNotFoundException {
	  try {
        //adminUser 정보 조회
        AdminUser admin = admindao.selectByAdminUserId(adminId);

        if(admin != null) {
            AdminUser authAdmin = AdminUser.builder()
                    .id(admin.getId())
                    .password(admin.getPassword())
                    .role(admin.getRole())
                    .adminName(admin.getUsername())
                    .build();

            log.info("authAdmin : {}", authAdmin);
            return authAdmin;
        }
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
        return null;
    }
}
