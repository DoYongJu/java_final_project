package com.damoyeo.healthyLife.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.damoyeo.healthyLife.bean.Comment;
import com.damoyeo.healthyLife.bean.Community;
import com.damoyeo.healthyLife.bean.GeneralUser;
import com.damoyeo.healthyLife.bean.Member;

import com.damoyeo.healthyLife.dto.ResponseDTO;
import com.damoyeo.healthyLife.service.CommentService;
import com.damoyeo.healthyLife.service.MemberService;

@RestController
@RequestMapping("/commentApi")
public class CommentApi {
	@Autowired
	private CommentService commentService;
	@Autowired
	private MemberService memberService;
	
	
	@PostMapping("/getAll") //토큰달고 오는 테스트코드
	public ResponseEntity<?>  authenticate(@AuthenticationPrincipal String id,@RequestParam("listId") long listId) throws Exception{
		//1. 유저 토큰, sportType이 파라미터 날라옴. 토큰에 해당하는 아이디를 조회하여 고객을 추출, 그 고객의 스포츠 타입을 삽입, 고객객체를 디비에 저장. sportType정보 추출후 출력
		Member m = memberService.findAccount(Long.parseLong(id));
		List<Comment> viewList = new ArrayList<>();
		Comment c = new Comment();
		if(m != null) {
			List<Object[]> allCommentList= commentService.getComment(listId);
			for(Object[] o : allCommentList) {
				c.setId(castLong(o[0]));
				c.setMemberId(castLong(o[1]));
				c.setUsername(castString(o[2]));
				c.setMemberType(castString(o[3]));
				c.setCommentDate(castDate(o[4]));
				c.setContent(castString(o[5]));
				c.setCommunityId(castLong(o[6]));
				viewList.add(c);			
			}
			return ResponseEntity.ok().body(viewList);
		}else {
			ResponseDTO responseDTO = ResponseDTO.builder().error("Comment loading failed").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	//댓글 삭제
	@PostMapping("/deleteComment")
	public ResponseEntity<?> deleteComment(@AuthenticationPrincipal GeneralUser generalUser, @RequestBody Map<String, Long> param) throws Exception {
		//맵 안에 코멘트아이디, 리스트아이디 넣어서 requestBody로 받는 걸로 수정함. 1024 인화
		//파람.코멘트 아이디(삭제할 댓글 아이디), 파람.포스트 아이디(해당 게시물 아이디)
		
		commentService.deleteComment(param.get("commentId"));
		commentService.addViewsAtCommunity(param.get("postId"));
		
		List<Comment> viewList = new ArrayList<>();
		List<Object[]> allCommentList= commentService.getComment(param.get("postId"));
			
		for(Object[] o : allCommentList) {
			Comment c = new Comment();
			c.setId(castLong(o[0]));
			c.setMemberId(castLong(o[1]));
			c.setUsername(castString(o[2]));
			c.setMemberType(castString(o[3]));
			c.setCommentDate((java.sql.Date)(o[4]));
			c.setContent(castString(o[5]));
			c.setCommunityId(castLong(o[6]));
			viewList.add(c);			
		}
		return ResponseEntity.ok().body(viewList);
	}
	@PostMapping("/addComment") //댓글추가
	public ResponseEntity<?>  addComment(@AuthenticationPrincipal GeneralUser gerneralUser,@RequestParam("listId") long listId ,@RequestParam("commentText") String commentText) throws Exception{
		//1. 유저 토큰, sportType이 파라미터 날라옴. 토큰에 해당하는 아이디를 조회하여 고객을 추출, 그 고객의 스포츠 타입을 삽입, 고객객체를 디비에 저장. sportType정보 추출후 출력
		Map <String, Long> commentMap = new HashMap<>();
		long memberId = gerneralUser.getId();
		commentService.addComment(listId, memberId, commentText);
		commentService.addViewsAtCommunity(listId);
		commentMap.put("listId", listId);
		return ResponseEntity.ok().body(commentMap);
	}
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
