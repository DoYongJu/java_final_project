package com.damoyeo.healthyLife.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.damoyeo.healthyLife.bean.Goal;
import com.damoyeo.healthyLife.bean.GoalCount;
import com.damoyeo.healthyLife.bean.Schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyDTO<T> {
	private List<Goal> myScheduleList;
	private String type;
	private Date date;
	private Integer count;
	private Integer totalCount;
	private Integer scheduleCount;
	private double MonthProbability;
	private List<GoalCount> goalCountList;
	
	public void GoalToMonthlyDTO(Goal goal) {
		List<Goal> List = new ArrayList<>();
		long id = goal.getId();
		long memberId = goal.getMemberId();
		long scheduleId = goal.getScheduleId();
		String goall = goal.getGoal();
		Date achieveDate = goal.getAchieveDate();
		
		Goal goalInstance = new Goal(id, memberId, scheduleId,goall, achieveDate);
		List.add(goalInstance);
		myScheduleList = List;
	}
	public void SumCount(List<Goal> list) {
		count = list.size();
	}
	public void SumTotalCount(List<Goal> list) {
		totalCount = list.size();
	}

	public void SumMonthProbability() {
		double MonthP = ((double) totalCount / (double)scheduleCount);
		MonthP = Math.round(MonthProbability * 100) / 100.0;
		MonthProbability = MonthP;
	}
	public void getJsonArrayGoalList(List<GoalCount> jsonList) {
		goalCountList = jsonList;
	}
	
}
