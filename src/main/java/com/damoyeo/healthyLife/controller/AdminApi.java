package com.damoyeo.healthyLife.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.damoyeo.healthyLife.exception.MemberException;
import com.damoyeo.healthyLife.service.AdminService;
import com.damoyeo.healthyLife.service.CommentService;
import com.damoyeo.healthyLife.service.CommunityService;
import com.damoyeo.healthyLife.service.MemberService;

@RestController
@RequestMapping("/adminApi")
public class AdminApi {
	@Autowired
	private AdminService adminService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/resignUser") //회원아이디로 탈퇴
	public ResponseEntity<ResponseDTO> resignUser(@RequestParam("userId") Long userIdParam) throws Exception {
		try {
			memberService.resignAccount(userIdParam);
			
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDTO responseDTO = ResponseDTO.builder().error("faild").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
		ResponseDTO responseDTO = ResponseDTO.builder().msg("해당 회원이 탈퇴 되었습니다.").build();
		return ResponseEntity.ok().body(responseDTO);
	}
	@GetMapping("/findUser/{usernameParam}") //회원이름으로 조회
	public ResponseEntity<ResponseDTO<Member>> findUser(@AuthenticationPrincipal String id, @PathVariable String usernameParam) throws Exception {
		List<Member> viewList =  new ArrayList<>();
		try {
			Member m = memberService.findIdByUsername(usernameParam);
			viewList.add(m);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDTO responseDTO = ResponseDTO.builder().error("faild").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
		ResponseDTO<Member> responseDTO = ResponseDTO.<Member>builder().data(viewList).build();
		return ResponseEntity.ok().body(responseDTO);
	}
	@GetMapping("/findPostByuser/{usernameParam}") //관리자가 게시판 회원조회할때 매서드
	public ResponseEntity<ResponseDTO<Community>> findPostByusername(@AuthenticationPrincipal String id,@PathVariable String usernameParam){
		List<Community> viewList =  new ArrayList<>();
		 try{
			 viewList= communityService.usernameFind(usernameParam);
		 }catch(Exception e) {
				e.printStackTrace();
				ResponseDTO responseDTO = ResponseDTO.builder().error("admin couldn't load fail").build();
				return ResponseEntity.badRequest().body(responseDTO);
			}
		ResponseDTO<Community> responseDTO = ResponseDTO.<Community>builder().data(viewList).build();
		return ResponseEntity.ok().body(responseDTO);
	}
	@GetMapping("/findCommentByuser/{usernameParam}") //관리자가 댓글 회원조회할때 매서드
	public ResponseEntity<ResponseDTO<Comment>> findCommentByuser(@AuthenticationPrincipal String id,@PathVariable String usernameParam){
		List<Comment> viewList =  new ArrayList<>();
		 try{
			 long userId =memberService.findIdByUsername(usernameParam).getId();
			 viewList= commentService.findByUsername(userId);
			 
		 }catch(Exception e) {
				e.printStackTrace();
				ResponseDTO responseDTO = ResponseDTO.builder().error("admin couldn't load fail").build();
				return ResponseEntity.badRequest().body(responseDTO);
			}
		ResponseDTO<Comment> responseDTO = ResponseDTO.<Comment>builder().data(viewList).build();
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping("/deleteComment")
	public ResponseEntity<?> deleteComment(@AuthenticationPrincipal GeneralUser generalUser, @RequestBody Map<String, Long> param) throws Exception {
		//맵 안에 코멘트아이디, 리스트아이디 넣어서 requestBody로 받는 걸로 수정함. 1024 인화
		//파람.코멘트 아이디(삭제할 댓글 아이디), 파람.포스트 아이디(해당 게시물 아이디)
		
		commentService.deleteComment(param.get("commentId"));
	
		
		List<Comment> viewList = new ArrayList<>();
		 viewList= commentService.findByUsername(param.get("memberId"));			
		
		return ResponseEntity.ok().body(viewList);
	}
	@DeleteMapping("/resign/{userId}")//강퇴
	public ResponseEntity<ResponseDTO> resign(@AuthenticationPrincipal String id, @PathVariable String userId) throws Exception { 
		
		try {
			memberService.resignAccount(Long.parseLong(userId));
			ResponseDTO responseDTO = ResponseDTO.builder().msg("탈퇴 처리 되었습니다.").build();
			return ResponseEntity.ok().body(responseDTO);
		} catch (MemberException e) {
			e.printStackTrace();
			ResponseDTO responseDTO = ResponseDTO.builder().error("faild").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
		
	}
	


    @GetMapping("/admin")
    public String admin(){ //test코드
        return "admin";
    }
	
}
