package com.damoyeo.healthyLife.service;




import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoyeo.healthyLife.bean.Community;
import com.damoyeo.healthyLife.dao.CommunityDAO;

import io.jsonwebtoken.lang.Collections;




@Service
public class CommunityService {
	@Autowired
	CommunityDAO communitydao;

	public CommunityService() {
		communitydao = new CommunityDAO();
	}
	
	public void addViews(long listId) {
		communitydao.updateViews(listId);
	}
	public List<Object[]> getAll(String searchType, String searchContent) {
		List<Object[]> rtn = null;
		if(searchType == null) {
			rtn = communitydao.select();
		}else {
			if(searchType.equals("content")) { 
				rtn =communitydao.getSearchByContent(searchContent); 
				}
			if(searchType.equals("userName")) { 
				rtn =communitydao.getSearchByUserName(searchContent); 
				}
			if(searchType.equals("date")) { 
				rtn =communitydao.getSearchByUserDate(searchContent); 
				}
		}
		
		
		
		return rtn;
	}
	//관리자의 아이디 조회
	public List<Community> usernameFind(String username) {
		List<Community> rtn = new ArrayList<>();
		List<Object[]> allList = null;
		
		try{
			allList = communitydao.getSearchByUserName(username);
			if(allList == null) { 
				System.out.println("username 조회 불가능, 유저가 작성한 게시물이 없습니다.");
				return rtn;
			}
			for(Object[] o : allList) {
				Community c = new Community();
				c.setId(castLong(o[0]));
				c.setContent(castString(o[1]));
				c.setImage(castString(o[2]));
				c.setDate(castDate(o[3]));
				c.setMemberId(castLong(o[4]));
				c.setTitle(castString(o[5]));
				c.setUsername(castString(o[6]));
				c.setMemberType(castString(o[7]));
				c.setViews(castLong(o[8]));
				c.setReplies(castLong(o[9]));
				rtn.add(c);			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rtn;
		
	}
	public Object[] getPost(long listId) {
		return communitydao.selectById(listId);
	}

	public synchronized void addPost(Community c) {
		communitydao.insert(c);
	}
	
	public synchronized void setPost(Community c) {
		communitydao.update(c);
	}

	public synchronized void deletePost(long listId) {
		Object[] communities = communitydao.selectById(listId);
		
			if(communities[2] != null) { //이미지가 있는 경우
				String fileName =communities[2].toString();
				String[] names = fileName.split(";");
				for(String name : names) {
					File file = new File(name);
					System.out.println("file: " + file);
					file.delete();
				}
			
		}
		communitydao.delete(listId);
	
		
	}
	public List<Community>getPostByType(String type) { 
		List<Community> rtn = new ArrayList<>();
		List<Object[]> allList= null;
		if(type.equals("전문가꿀팁")) {
			allList =communitydao.selectByPro();
		}
		
		if(type.equals("자유게시판")) { allList =communitydao.selectByPeople(); }
		 
		if(type.equals("전체보기")) {
			allList =communitydao.select();
		}
		if(type.equals("오름차순보기")) {
			//힘듦... 나중에..	
		}
		
		for(Object[] o : allList) {
			Community c = new Community();
			c.setId(castLong(o[0])); 
			c.setContent(castString(o[1]));
			c.setImage(castString(o[2]));
			c.setDate((java.sql.Date)o[3]);
			c.setMemberId(castLong(o[4]));
			c.setTitle(castString(o[5]));
			c.setUsername(castString(o[6]));
			c.setMemberType(castString(o[7]));
			c.setViews(castLong(o[8]));
			c.setReplies(castLong(o[9]));
			rtn.add(c);			
		}
		
		return rtn;
	}

//	public Community getByCommunityId(long listId) {
//		return communitydao.selectByCommunityId(listId);
//	}
	public static Long castLong(Object o) {
        Long value = null;
        if (o != null) {
            value = Long.parseLong(o.toString());
        }
        return value;
    }
	public static String castString(Object o) {
        String value = null;
        if (o != null) {
            value = o.toString();
        }
        return value;
    }
	public static Date castDate(Object o) throws Exception {
        Date value = null;
        if (o != null) {
        	String from = o.toString();

        	SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");

        	value = fm.parse(from);
           
        }
        return value;
    }
}
