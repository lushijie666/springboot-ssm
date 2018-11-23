package com.xxx.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xxx.model.JsonResponse;
import com.xxx.model.UserModel;
import com.xxx.service.UserService;
import com.xxx.util.StringUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@GetMapping(value = "/list")
	public JsonResponse getUserList(@RequestParam Map<String, String> paramMap) {
		UserModel userModel = new UserModel();
		String pageIndex = paramMap.get("page");
		if (StringUtil.isNotNullString(pageIndex)) {
			userModel.setPageIndex(Integer.parseInt(pageIndex));
		}
		String pageSize = paramMap.get("limit");
		if (StringUtil.isNotNullString(pageSize)) {
			userModel.setPageSize(Integer.parseInt(pageSize));
		}
		userModel.setQuery(paramMap.get("query"));
		long totalCount = userService.getUserCount(userModel);
		List<UserModel> pageList = userService.getUserList(userModel);
		JsonResponse response = new JsonResponse();
		response.setCount(totalCount);
		response.setSuccessed(pageList);
		return response;
	}

	@PostMapping
	public JsonResponse operUser(@RequestParam Map<String, String> paramMap) {
		String name = paramMap.get("name");
		String password = paramMap.get("password");
		int age = 0;
		String ageStr = paramMap.get("age");
		if (null != ageStr) {
			age = Integer.parseInt(ageStr);
		}
		String job = paramMap.get("job");
		UserModel userModel = new UserModel(name, age, job);
		userModel.setPassword(password);
		String id = paramMap.get("id");
		boolean result = false;
		if (null != id && id.trim().length() > 0) {
			userModel.setId(Long.parseLong(id));
			result = userService.updateUser(userModel);
		} else {
			result = userService.insertUser(userModel);
		}
		JsonResponse response = new JsonResponse();
		if (result) {
			response.setSuccessed();
		}
		return response;
	}

	@DeleteMapping(value = "/{id}")
	public JsonResponse deleteUser(@PathVariable(value = "id") Long id, @RequestParam Map<String, String> paramMap) {
		userService.deleteUser(id);
		JsonResponse response = new JsonResponse();
		response.setSuccessed();
		return response;
	}
}
