package com.damoyeo.healthyLife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoyeo.healthyLife.bean.Sport;
import com.damoyeo.healthyLife.dao.SportDAO;
@Service
public class SportService {
	@Autowired
	SportDAO sportdao;
	
	public SportService() {
		sportdao = new SportDAO();
	}
	
	public Sport find(String title) {
		return sportdao.selectByTitle(title);
	}


}
