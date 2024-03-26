package com.damoyeo.healthyLife;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.commons.dbutils.QueryLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@SpringBootApplication
public class HealthyLifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthyLifeApplication.class, args);
	}
	@Bean
	public Map<String, String> queryMap() {
		String QUERY_PATH_MADANG = "/com/damoyeo/healthyLife/member_sql.properties";
		String QUERY_PATH_MADANG1 = "/com/damoyeo/healthyLife/wisesaying_sql.properties";
		String QUERY_PATH_MADANG2 = "/com/damoyeo/healthyLife/sport_sql.properties";
		String QUERY_PATH_MADANG3 = "/com/damoyeo/healthyLife/menu_sql.properties";
		String QUERY_PATH_MADANG4 = "/com/damoyeo/healthyLife/calory_sql.properties";
		String QUERY_PATH_MADANG5 = "/com/damoyeo/healthyLife/community_sql.properties";
		String QUERY_PATH_MADANG6 = "/com/damoyeo/healthyLife/comment_sql.properties";
		String QUERY_PATH_MADANG7 = "/com/damoyeo/healthyLife/schedule_sql.properties";
		String QUERY_PATH_MADANG8 = "/com/damoyeo/healthyLife/goal_sql.properties";
		String QUERY_PATH_MADANG9 = "/com/damoyeo/healthyLife/trainer_sql.properties";
		String QUERY_PATH_MADANG10 = "/com/damoyeo/healthyLife/postMessage_sql.properties";
		String QUERY_PATH_MADANG11 = "/com/damoyeo/healthyLife/cart_sql.properties";
		String QUERY_PATH_MADANG12 = "/com/damoyeo/healthyLife/ordering_sql.properties";
		String QUERY_PATH_MADANG13 = "/com/damoyeo/healthyLife/product_sql.properties";
		
		
		Map<String, String> queryMap = new HashMap<>();

		try {

			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG1));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG2));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG3));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG4));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG5));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG6));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG7));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG8));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG9));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG10));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG11));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG12));
			queryMap.putAll(QueryLoader.instance().load(QUERY_PATH_MADANG13));
			
			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new ExceptionInInitializerError(ioe);
		}
		return queryMap;
	}
	//passwordEncoder
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
	


}
