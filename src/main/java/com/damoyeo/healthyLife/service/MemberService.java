package com.damoyeo.healthyLife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.damoyeo.healthyLife.bean.AdminUser;
import com.damoyeo.healthyLife.bean.GeneralUser;
import com.damoyeo.healthyLife.bean.Member;
import com.damoyeo.healthyLife.bean.Trainer;
import com.damoyeo.healthyLife.dao.MemberDAO;
import com.damoyeo.healthyLife.dao.TrainerDAO;
import com.damoyeo.healthyLife.exception.MemberException;
import com.damoyeo.healthyLife.exception.NotUserException;

import lombok.RequiredArgsConstructor;

@Service
public class MemberService implements UserDetailsService{
	@Autowired
	MemberDAO memberdao;
	@Autowired
	TrainerDAO trainerdao;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	public MemberService() {
		memberdao = new MemberDAO();
	}
	
	 @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			 Member m =  memberdao.selectByUsername(username);
			 return GeneralUser.builder()
		        		.id(m.getId())
		                .username(username)
		                .password(m.getPassword()) // 실제로는 암호화된 비밀번호를 사용해야 합니다.
		                .role("USER")
		                .build();   
		}catch(Exception e) {
			e.printStackTrace();

		}
		return GeneralUser.builder()
        		.id(-1)
                .username(username)
                .password("-1") 
                .role("USER")
                .build();   
	        
	 }
	 public String findUsernameById(String id) throws Exception {
			Member member =  memberdao.findAccountById(Long.valueOf(id));
			String username =  member.getUsername();
		
				if (member.getUsername() == null) {
					// 아이디가 존재하지 않을 경우 ==> 예외 발생
					throw new NotUserException(member.getUsername() + " (이)란 아이디는 존재하지 않아요");
				}

				return username;// 해당 회원 반환	
		}
	public Member getByCredentials(final String username, final String password) throws Exception {
		 Member m =  memberdao.selectByUsername(username);
		 
	
			if (m.getUsername() == null) {
				// 아이디가 존재하지 않을 경우 ==> 예외 발생
				throw new NotUserException(username + " (이)란 아이디는 존재하지 않아요");
			}

			// 비밀번호 체크
			String dbPwd = m.getPassword();
			if (!password.equals(dbPwd)) {
				// 비밀번호가 불일치라면
				 throw new NotUserException("비밀번호가 일치하지 않아요");
			
			}
			return m;// 해당 회원 반환	
	}
	public Member findIdByUsername (String username) throws Exception {
		 Member m =  memberdao.selectByUsername(username);
		 return m;
	}
	// 회원가입 판단 메서드
	public void addOrRefuse(Member member, String type) throws MemberException  {
		try{
			boolean result = memberdao.check(member.getUsername());
			
			if(result == false) { 
				System.out.println("유저 네임 중복 안됨");
				throw new MemberException();	
			}
			if(type != "일반") { //트레이너
				memberdao.addMember(member);
				Member newMember =memberdao.selectByUsername(member.getUsername());
				Trainer trainer = new Trainer();
				
				trainer.setUserName(newMember.getUsername());
				trainer.setPassword(newMember.getPassword());
				trainer.setMemberId(newMember.getId());
				
				trainerdao.addTrainer(trainer);
			}else {
				memberdao.addMember(member);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}

	//아이디 중복 판단 메서드
		public boolean usernameCheck(String username) {
			boolean usernameResult = true;
			try{
				usernameResult = memberdao.check(username);
				if(usernameResult == false) { 
					System.out.println("username 중복. 안됨");
					return usernameResult;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return usernameResult;
			
		}
	//이메일 중복 판단 메서드
	public boolean emailCheck(String email) {
		boolean EmailResult = true;
		try{
			EmailResult = memberdao.checkEmail(email);
			if(EmailResult == false) { 
				System.out.println("이메일 중복 이여서 안됨");
				return EmailResult;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return EmailResult;
		
	}
	public void resignAccount(long userId) throws Exception{ //계정삭제 함수
		memberdao.deleteAccountById(userId);
	}
	public Member findAccount(long userId) throws Exception{ //id값으로 계정정보 찾는 함수
		Member m = memberdao.findAccountById(userId);
		return m;
	}

		
		

		
	
	
	//비밀번호 수정 메서드
	public boolean editPassword(Member member) throws Exception {
		boolean result = memberdao.updatePwdByUserName(member);
		return result;
	}
	
	//비밀번호 찾기 메서드
	public String findPwd(String userName) throws Exception{
		Member m = memberdao.selectByUsername(userName);
		return m.getPassword();
	}
	
	//아이디 찾기 메서드
	public String findId(String email) throws Exception{
		Member m =memberdao.selectByEmail(email);
		return m.getUsername();
	}
	
	public void setSportType(Member m) throws Exception{		
		memberdao.updateSportType(m);
	}
	
	public void setMenuType(Member m) throws Exception{		
		memberdao.updateMenuType(m);
	}


}
