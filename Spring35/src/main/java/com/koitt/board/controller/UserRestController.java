package com.koitt.board.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.koitt.board.model.UserInfo;
import com.koitt.board.service.UserInfoService;
	
	@Controller
	@RequestMapping("/rest")
	public class UserRestController {
	
		@Autowired
		private UserInfoService userInfoService;
		
		@Autowired
		private PasswordEncoder passwordEncoder;
		
		@RequestMapping(value = "/user/login", method = RequestMethod.POST)
		public ResponseEntity<String> login(UserInfo userInfo) {
			boolean isMatched = userInfoService.isPasswordMatched(
					userInfo.getEmail(),
					userInfo.getPassword());
			
			if (isMatched) {
				// Base64 인코딩 전 평문
				String plainCredentials = userInfo.getEmail() + ":" + userInfo.getPassword();
				
				// 평문을 Base64로 인코딩
				String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
				
				return new ResponseEntity<String>(base64Credentials, HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
	}
}


