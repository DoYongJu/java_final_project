package com.damoyeo.healthyLife.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.damoyeo.healthyLife.bean.Calory;
import com.damoyeo.healthyLife.dto.ResponseDTO;
import com.damoyeo.healthyLife.service.CaloryService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/caloryApi")
public class CaloryApi {
	@Autowired
	private CaloryService caloryService;
	
	@GetMapping("/dictionary") 
	public ResponseEntity<?>  authenticate(@RequestParam("searchContent") String searchContent ) throws Exception{
	List<Calory> searchList = new ArrayList<>();
	List<Calory> viewList = new ArrayList<>();
	Calory unit = new Calory();
	searchList = caloryService.findByTitle(searchContent);
	if(searchList != null){
		for(Calory c : searchList) {
			unit.setId(c.getId());
			unit.setType(c.getType());
			unit.setTitle(c.getTitle());
			unit.setCalory(c.getCalory());
			unit.setUnit(c.getUnit());
			viewList.add(unit);
		}
		final Calory response =Calory.builder()
				.id(unit.getId())
				.type(unit.getType())
				.title(unit.getTitle())
				.calory(unit.getCalory())
				.unit(unit.getUnit())
				.build();
		return ResponseEntity.ok().body(response);
		
		
	}else {
		ResponseDTO responseDTO = ResponseDTO.builder().error("caloryDictionary loading failed").build();
		return ResponseEntity.badRequest().body(responseDTO);
	}
	
	}
}
