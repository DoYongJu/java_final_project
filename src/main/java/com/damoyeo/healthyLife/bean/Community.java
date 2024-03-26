package com.damoyeo.healthyLife.bean;



import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Community {
	long id;
	String image;
	String content;
	String title;
	Date date;
	long memberId;
	
	long views;
	long replies;
	
	String memberType;
	String username;
	

}
