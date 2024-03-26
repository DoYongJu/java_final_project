package com.damoyeo.healthyLife.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.UUID;

import javax.servlet.annotation.MultipartConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.damoyeo.healthyLife.bean.Community;
import com.damoyeo.healthyLife.bean.GeneralUser;
import com.damoyeo.healthyLife.dto.ResponseDTO;
import com.damoyeo.healthyLife.service.CommunityService;

import lombok.Value;

@RestController
@MultipartConfig(maxFileSize = 1024 * 1024 * 2, location = "C:\\IMAGE_DATA\\")
@RequestMapping("/coummunityApi")
public class CoummunityApi {
	@Autowired
	private CommunityService communityService;
	

	@GetMapping("/deletePost/{listIdParam}")
	public ResponseEntity<ResponseDTO> deletePost(@AuthenticationPrincipal GeneralUser gerneralUser,
			@PathVariable long listIdParam) {
		try {
			communityService.deletePost(listIdParam);
			ResponseDTO responseDTO = ResponseDTO.builder().msg("요청하신 게시물이 삭제 되었습니다.").build();
			return ResponseEntity.ok().body(responseDTO);
	
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDTO responseDTO = ResponseDTO.builder().error(" failed").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
		
	}

	@PostMapping("/addPost")
	public ResponseEntity<?> addPost(@AuthenticationPrincipal GeneralUser gerneralUser,
			@RequestParam("updateParam") String updateParam, @RequestParam("imageParam") String image,
			@ModelAttribute Community communityParam,
			@RequestPart(value = "file", required = true) MultipartFile[] files) throws Exception {

		long id = gerneralUser.getId();
		System.out.println("id:    " + gerneralUser.getId());

		String update = StringUtils.defaultIfEmpty(updateParam, null);

		Date nowDate = new Date();

		Date today = DateUtils.truncate(nowDate, Calendar.DATE);
		System.out.println("communityParamId:    " + communityParam.getId());

		try {
			if (update == null) { // 수정이 아니라 처음 게시물 작성인 경우

				if (files != null) { // 이미지가 있는경우
					String uuidPicName = null;
					uuidPicName = uploadFile(files, id);
					System.out.println("List<String> list:	"+uuidPicName);
				//list를 set해야함
					communityParam.setImage( uuidPicName);
					communityParam.setDate(today);
					communityParam.setMemberId(castLong(id));
					communityService.addPost(communityParam);

					if (StringUtils.isNotEmpty(update)) {
						communityParam.setImage (uuidPicName);
					}
				} else { // 이미지가 없는 경우
					communityParam.setDate(today);
					communityParam.setMemberId(castLong(id));
					communityService.addPost(communityParam);
				}
			} // 게시물 수정 하는 경우
			if (files == null) { // 수정시 이미지가 없는 경우

				communityParam.setImage(image);

			}

			communityParam.setId(communityParam.getId());
			communityParam.setDate(today);
			communityParam.setMemberId(castLong(id));
			communityService.setPost(communityParam);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Object[] postObject = communityService.getPost(communityParam.getId());
		communityParam.setId(castLong(postObject[0]));
		communityParam.setContent(castString(postObject[1]));
		communityParam.setImage(castString(postObject[2]));
		communityParam.setDate((java.sql.Date) (postObject[3]));
		communityParam.setTitle(castString(postObject[4]));
		communityParam.setMemberId(castLong(postObject[5]));
		communityParam.setUsername(castString(postObject[6]));
		communityParam.setMemberType(castString(postObject[7]));
		return ResponseEntity.ok().body(communityParam);
	}

	// 파일명 재생성 메서드
	private String uploadFile(MultipartFile[] files, long id) throws Exception {
		String rtnFileName = ";";
		String saveName = null;
		for (MultipartFile uploadFile : files) {
			if (uploadFile.getContentType().startsWith("image") == false) {
				//savedFileNames.add("this file is not image type");
				return "this file is not image type";
			}
			String originalName = uploadFile.getOriginalFilename();
			String fileName = originalName.substring(originalName.lastIndexOf("//") + 1);

			System.out.println("fileName" + fileName);
			String uuid = UUID.randomUUID().toString();
			// 저장할 파일 이름 중간에 "_"를 이용하여 구분
			saveName = "C:\\IMAGE_DATA"+ File.separator + uuid + "_" + fileName;
			
			Path savePath = Paths.get(saveName);
			// Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)

			try {
				uploadFile.transferTo(savePath);
				// uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
			} catch (IOException e) {
				e.printStackTrace();
				// printStackTrace()를 호출하면 로그에 Stack trace가 출력됩니다.
			}
			rtnFileName =rtnFileName+saveName+";";
		}
		
		return rtnFileName;
		
	}

	@GetMapping("/communityList") //검색기능
	public ResponseEntity<?> communityList(
			@AuthenticationPrincipal GeneralUser gerneralUser,
			@RequestParam("type") String typeParam, 
			@RequestParam("searchType") String searchTypeParam,
			@RequestParam("searchContent") String searchContentParam) throws Exception {  
		
		String type = StringUtils.defaultIfEmpty(typeParam, null);
		String testString = null;

		List<Community> viewList = new ArrayList<>();
		if (StringUtils.equals(type, testString) != true) {
			viewList = communityService.getPostByType(type);
			return ResponseEntity.ok().body(viewList);
		} else {
			List<Object[]> allList = communityService.getAll(// 초기화면
					StringUtils.defaultIfEmpty(searchTypeParam, null),
					StringUtils.defaultIfEmpty(searchContentParam, null));
			for (Object[] o : allList) {
				Community c = new Community();
				c.setId(castLong(o[0]));
				c.setContent(castString(o[1]));
				c.setImage(castString(o[2]));
				c.setDate((java.sql.Date) (o[3]));
				c.setMemberId(castLong(o[4]));
				c.setTitle(castString(o[5]));
				c.setUsername(castString(o[6]));
				c.setMemberType(castString(o[7]));
				c.setViews(castLong(o[8]));
				c.setReplies(castLong(o[9]));
				viewList.add(c);
			}
			return ResponseEntity.ok().body(viewList);
		}
	}

	@GetMapping("/communityInfo")
	public ResponseEntity<?> communityInfo(@AuthenticationPrincipal GeneralUser gerneralUser,
			@RequestParam("listId") long listId) throws Exception {
		communityService.addViews(listId);// 조회수 추가
		
		Community c = new Community();
		Object[] postObject = communityService.getPost(listId);
		c.setId(castLong(postObject[0]));
		c.setContent(castString(postObject[1]));
		c.setImage(castString(postObject[2]));

		c.setDate((java.sql.Date) (postObject[3]));
		c.setTitle(postObject[4].toString());
		c.setMemberId(castLong(postObject[5]));
		c.setUsername(castString(postObject[6]));
		c.setMemberType(castString(postObject[7]));
		c.setViews(castLong(postObject[8]));
		c.setReplies(castLong(postObject[8]));
		return ResponseEntity.ok().body(c);

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
}
