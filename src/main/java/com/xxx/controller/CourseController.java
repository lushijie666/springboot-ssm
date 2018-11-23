package com.xxx.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xxx.model.CourseModel;
import com.xxx.model.JsonResponse;
import com.xxx.model.UserModel;
import com.xxx.service.CourseService;
import com.xxx.util.StringUtil;

@RestController
@RequestMapping("/course")
public class CourseController {
	
	@Resource
	private CourseService courseService;

	@GetMapping(value = "/list")
	public JsonResponse getCourseList(@RequestParam Map<String, String> paramMap){
		CourseModel courseModel = new CourseModel();
		String pageIndex = paramMap.get("page");
		if (StringUtil.isNotNullString(pageIndex)) {
			courseModel.setPageIndex(Integer.parseInt(pageIndex));
		}
		String pageSize = paramMap.get("limit");
		if (StringUtil.isNotNullString(pageSize)) {
			courseModel.setPageSize(Integer.parseInt(pageSize));
		}
		courseModel.setQuery(paramMap.get("query"));
		long totalCount = courseService.getCourseCount(courseModel);
		List<CourseModel> pageList = courseService.getCourseList(courseModel);
		JsonResponse response = new JsonResponse();
		response.setCount(totalCount);
		response.setSuccessed(pageList);
		return response;
	}
	
	@PostMapping
	public JsonResponse operCourse(@RequestParam Map<String, String> paramMap) {
		String name = paramMap.get("name");
		int type = 0;
		String typeStr = paramMap.get("type");
		if (null != typeStr){
			type = Integer.parseInt(typeStr);
		}
		int score = 0;
		String scoreStr = paramMap.get("score");
		if (null != scoreStr){
			score = Integer.parseInt(scoreStr);
		}
		CourseModel courseModel = new CourseModel(name, type, score);
		String id = paramMap.get("id");
		boolean result = false;
		if(StringUtil.isNotNullString(id)){
			courseModel.setId(Long.parseLong(id));
			result =  courseService.updateCourse(courseModel);
		} else {
			result =  courseService.insertCourse(courseModel);
		}
		JsonResponse response = new JsonResponse();
		if (result) {
			response.setSuccessed();
		}
		return response;
	}
	@DeleteMapping(value = "/{id}")
	public JsonResponse deleteCourse(@PathVariable(value = "id") Long id, @RequestParam Map<String, String> paramMap) {
		courseService.deleteCourse(id);
		JsonResponse response = new JsonResponse();
		response.setSuccessed();
		return response;
	}
	
	
}
