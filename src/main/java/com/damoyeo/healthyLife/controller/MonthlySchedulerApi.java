package com.damoyeo.healthyLife.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.damoyeo.healthyLife.bean.Goal;
import com.damoyeo.healthyLife.bean.GoalCount;
import com.damoyeo.healthyLife.bean.Member;
import com.damoyeo.healthyLife.bean.Schedule;
import com.damoyeo.healthyLife.dto.MonthlyDTO;
import com.damoyeo.healthyLife.dto.ResponseDTO;
import com.damoyeo.healthyLife.service.GoalService;
import com.damoyeo.healthyLife.service.MemberService;
import com.damoyeo.healthyLife.service.ScheduleService;




@RestController
@RequestMapping("/monthlySchedulerApi")
public class MonthlySchedulerApi {
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private GoalService goalService;
	
	Date nowDate = new Date();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String strNowDate = simpleDateFormat.format(nowDate);
	
	@PostMapping("/getAll") 
	public ResponseEntity<ResponseDTO<MonthlyDTO>>  getAll(@AuthenticationPrincipal String id,@RequestParam("dateForWeek") String dateForWeekParam
									, @RequestParam("date") String dateParam, @RequestParam("type") String typeParam) throws Exception{

		Member m = memberService.findAccount(Long.parseLong(id));
		String type = StringUtils.defaultIfEmpty(typeParam, "주간스케쥴");
		Date date = simpleDateFormat.parse(StringUtils.defaultIfEmpty(dateParam, strNowDate));
	
		if(m != null) {
			MonthlyDTO<?> dto = new MonthlyDTO<>();
			List<MonthlyDTO> viewList =  new ArrayList<>();
			 List<Goal>myGoalList = goalService.findByUserAndDate(m.getId(), date);
			 List<Goal> totalList = goalService.find(m.getId());	
			 dto.setDate(date);
			 dto.SumCount(myGoalList);
			 dto.SumTotalCount(totalList);
			 dto.setScheduleCount(scheduleService.sumOfSchedule(m.getId()));
			 dto.SumMonthProbability();
			 dto.setType(type);
			 for (Goal e : myGoalList) {
				 dto.GoalToMonthlyDTO(e);
					viewList.add(dto);					
				}
			 
			 List<GoalCount> jsonGoalList = new ArrayList<>();
				for (GoalCount goalCount : goalService.findCountByAchieveDate(m.getId())) {
					jsonGoalList.add(goalCount);
				}
				//String jsonArrayGoalList = StringUtils.join(jsonGoalList);
				dto.getJsonArrayGoalList(jsonGoalList);
			ResponseDTO<MonthlyDTO> responseDTO = ResponseDTO.<MonthlyDTO>builder().data(viewList).build();
			
			return ResponseEntity.ok().body(responseDTO);
		}else {
			ResponseDTO responseDTO = ResponseDTO.builder().error("Comment loading failed").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
}
