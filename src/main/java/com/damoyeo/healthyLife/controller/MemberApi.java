package com.damoyeo.healthyLife.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.damoyeo.healthyLife.bean.AdminUser;
import com.damoyeo.healthyLife.bean.Member;
import com.damoyeo.healthyLife.dto.ResponseDTO;
import com.damoyeo.healthyLife.exception.MemberException;
import com.damoyeo.healthyLife.exception.NotUserException;
import com.damoyeo.healthyLife.mail.RegisterMail;
import com.damoyeo.healthyLife.security.TokenProvider;
import com.damoyeo.healthyLife.service.AdminService;
import com.damoyeo.healthyLife.service.MemberService;

@RestController
@RequestMapping("/memberApi")
public class MemberApi {

	@Autowired
	private MemberService memberService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private RegisterMail registerMail;
	@Autowired
	private TokenProvider tokenProvider;
	
	@PostMapping("/pwdEdit") //비밀번호 수정
	public ResponseEntity<?> pwdEdit(@RequestParam("password") String password, @AuthenticationPrincipal String id) throws Exception {
		Map <String, String> editMap = new HashMap<>();
		try {
			Member m = memberService.findAccount(Long.parseLong(id));
			m.setPassword(password);
			boolean daoSuccess = memberService.editPassword(m);
			if(daoSuccess != false) {//성공한 경우
				editMap.put("data", "비밀번호 변경이 완료 되었습니다.");
			}
			return ResponseEntity.ok().body(editMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseDTO responseDTO = ResponseDTO.builder().error("faild").build();
		return ResponseEntity.badRequest().body(responseDTO);
	}
	@GetMapping("/findPwd")// 비밀번호 찾기
	public ResponseEntity<?> findPwd (@RequestParam("userName") String userName) throws Exception{
		
		try {
			String passWord = memberService.findPwd(userName);
			if(passWord != null) {
				Member responseUser = Member.builder()
						.password(passWord)
						.build();
				return ResponseEntity.ok().body(responseUser);
			}
			
		} catch (MemberException e) {
			e.printStackTrace();
		}
		ResponseDTO responseDTO = ResponseDTO.builder().error("faild").build();
		return ResponseEntity.badRequest().body(responseDTO);
	}
	@DeleteMapping("/resign")//탈퇴
	public void resign(@AuthenticationPrincipal String id) throws Exception { 
		System.out.println(id);
		try {
			memberService.resignAccount(Long.parseLong(id));
			
			
		} catch (MemberException e) {
			e.printStackTrace();
		}
	}
	@GetMapping("/findId")// 아이디 찾기
	public ResponseEntity<?> findId (@RequestParam("email") String email) throws Exception{
		
		try {
			String userName = memberService.findId(email);
			if(userName == null) {
				Member responseUser = Member.builder()
						.username(userName)
						.build();
				return ResponseEntity.ok().body(responseUser);
			}
			
		} catch (MemberException e) {
			e.printStackTrace();
		}
		ResponseDTO responseDTO = ResponseDTO.builder().error("faild").build();
		return ResponseEntity.badRequest().body(responseDTO);
	}
	@PostMapping("/signup") //회원가입
	public void doSignUp(@RequestBody Member member) throws IOException {
		
		try {
			memberService.addOrRefuse(member, member.getType()); //type 파라미터 삽입예정
			System.out.println(member.getType());
			member.setMenuType(null);
			member.setSportType(null);
		} catch (MemberException e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("/signin")
	ResponseEntity<?>  signin(@RequestBody Member userDTO) throws Exception {
		AdminUser admin  = null;
		Member user = null;
		String token = null;
		String username = userDTO.getUsername();

		try {
			if(username.equals("관리자") != true) { //일반 사용자
				user = memberService.getByCredentials(username, userDTO.getPassword());
				token = tokenProvider.create(user);
				Member responseUser = Member.builder()
							.username(user.getUsername())
							.id(user.getId())
							.role(user.getRole())
							.token(token)
							.build();
					return ResponseEntity.ok().body(responseUser);
			}else {
				admin = adminService.getByCredentials(userDTO.getUsername(), userDTO.getPassword());
				token = tokenProvider.createAdmin(admin);
				AdminUser responseAdmin = AdminUser.builder()
						.adminName(userDTO.getUsername())
						.id(admin.getId())
						.role(admin.getRole())
						.token(token)
						.type("관리자")
						.build();
				return ResponseEntity.ok().body(responseAdmin);
			}			
			
		}catch(NotUserException ne) {
			String msg = ne.getMessage();
			ResponseDTO responseDTO = ResponseDTO.builder().error(msg).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("???");
		
	
	}
	

	@GetMapping("/checkUsername")//유저 중복 여부 확인
	ResponseEntity<Map <String, String>> checkUserNameToEdit(@RequestParam("userName") String username) throws Exception {
		boolean checked = memberService.usernameCheck(username);
		Map <String, String> usernameMap = new HashMap<>();
		if(checked == true) {
			
			usernameMap.put("data", "가능한 아이디 입니다.");
		}else {
			usernameMap.put("data", "이미 사용중인 아이디입니다. 다른 아이디를 입력하세요.");
		}
		
		
		
		return ResponseEntity.ok().body(usernameMap);
	}
	@PostMapping("/checkEmail")//이메일 중복 여부 확인
	ResponseEntity<Map <String, String>> checkEmail(@RequestParam("email") String email) throws Exception {
		boolean checked = memberService.emailCheck(email);
		Map <String, String> emailMap = new HashMap<>();
		if(checked == true) {
			
			emailMap.put("data", "회원가입 가능한 이메일 주소입니다.");
		}
		emailMap.put("data", "이미 가입된 이메일 주소입니다.");
		
		
		return ResponseEntity.ok().body(emailMap);
	}
	// 이메일 인증
	@PostMapping("login/mailConfirm")
	ResponseEntity<Map <String, String>> mailConfirm(@RequestParam("email") String email) throws Exception {

		String code = registerMail.sendSimpleMessage(email);
		System.out.println("인증코드 : " + code);
		Map <String, String> codeMap = new HashMap<>();
		codeMap.put("code", code);
		return ResponseEntity.ok().body(codeMap);
	}
}
