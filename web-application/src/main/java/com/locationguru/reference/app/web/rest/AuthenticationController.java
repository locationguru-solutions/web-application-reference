package com.locationguru.reference.app.web.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locationguru.csf.web.rest.model.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/authentications")
public class AuthenticationController
{
	private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

	@GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<String>> login(final HttpServletRequest request, final HttpServletResponse response)
	{
		return ResponseEntity.ok(Response.ok("Success"));
	}
}
