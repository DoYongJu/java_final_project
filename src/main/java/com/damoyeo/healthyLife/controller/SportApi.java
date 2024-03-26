package com.damoyeo.healthyLife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.damoyeo.healthyLife.bean.Member;
import com.damoyeo.healthyLife.bean.Menu;
import com.damoyeo.healthyLife.bean.Sport;
import com.damoyeo.healthyLife.dto.ResponseDTO;
import com.damoyeo.healthyLife.service.MemberService;
import com.damoyeo.healthyLife.service.SportService;



@RestController
@RequestMapping("/sportApi")
public class SportApi {

	@Autowired
	private SportService sportService;
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/sportTest") //토큰달고 오는 테스트코드
	public void sportTest() throws Exception{
		
	}
	
	@GetMapping("/sportTestResult") //토큰달고 오는 테스트코드
	public ResponseEntity<?>  sportTestResult(@AuthenticationPrincipal String id,@RequestParam("title") String sportTypeTitle ) throws Exception{
		//1. 유저 토큰, sportType이 파라미터 날라옴. 토큰에 해당하는 아이디를 조회하여 고객을 추출, 그 고객의 스포츠 타입을 삽입, 고객객체를 디비에 저장. sportType정보 추출후 출력
		Member m = memberService.findAccount(Long.parseLong(id));
		if(m != null) {
			m.setSportType(sportTypeTitle);
			memberService.setSportType(m);
			Sport s = sportService.find(sportTypeTitle);
			final Sport response = Sport.builder()
					.title(s.getTitle())
					.content(s.getContent())
					.nickname(s.getNickname())
					.build();
			return ResponseEntity.ok().body(response);
		}else {
			ResponseDTO responseDTO = ResponseDTO.builder().error("sportTestResult loading failed").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@GetMapping("/mypage/testResult")
	public ResponseEntity<?>  testResult(@AuthenticationPrincipal String id) throws Exception{
	
		Member m = memberService.findAccount(Long.parseLong(id));
		if(m != null) {
			Sport sport = sportService.find(m.getSportType());
			Sport response = Sport.builder()
					.id(sport.getId())
					.title(sport.getTitle())
					.content(sport.getContent())
					.nickname(sport.getNickname())
					.build();
			
			return ResponseEntity.ok().body(response);
		}else {
			ResponseDTO responseDTO = ResponseDTO.builder().error("myPage sportTestResult loading failed").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
}
