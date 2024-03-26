package com.damoyeo.healthyLife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoyeo.healthyLife.bean.Menu;
import com.damoyeo.healthyLife.dao.MenuDAO;
@Service
public class MenuService {
	@Autowired
	MenuDAO menudao;
	
	public MenuService() {
		menudao = new MenuDAO();
	}
	
	public Menu find(String title) {		
		return menudao.selectByTitle(title);
	}

}
