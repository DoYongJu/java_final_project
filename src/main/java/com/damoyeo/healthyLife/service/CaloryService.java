package com.damoyeo.healthyLife.service;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoyeo.healthyLife.bean.Calory;
import com.damoyeo.healthyLife.dao.CaloryDAO;




@Service
public class CaloryService {
	@Autowired
	CaloryDAO calorydao;
	
	public CaloryService() {
		calorydao = new CaloryDAO();
	}
	
	public List<Calory> findAll() {		
		return calorydao.select();
	}
	
	public List<Calory> findByTitle(String title) {
		return calorydao.selectByTitle(title);
	}
	
}
