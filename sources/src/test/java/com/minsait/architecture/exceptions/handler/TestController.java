package com.minsait.architecture.exceptions.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.architecture.exceptions.BadRequestException;
import com.minsait.architecture.exceptions.NotFoundException;
import com.minsait.architecture.exceptions.OnesaitException;
import com.minsait.architecture.exceptions.UserNotFoundException;

@RestController
public class TestController {

	@GetMapping(value = "/launchMeException")
	public ResponseEntity<Void> launchMeException() throws Exception {
		throw new Exception("Exception message");
	}

	@GetMapping(value = "/launchMeUserException")
	public ResponseEntity<Void> launchMeUserException() throws UserNotFoundException {
		throw new UserNotFoundException("User exception");
	}

	@GetMapping(value = "/launchMeMotionException")
	public ResponseEntity<Void> launchMeMotionException() throws OnesaitException {
		throw new OnesaitException("1");
	}

	@GetMapping(value = "/launchMeNotFoundException")
	public ResponseEntity<Void> launchMeNotFoundException() throws Exception {
		throw new NotFoundException("ontology", "id");
	}

	@GetMapping(value = "/launchMeBadRequestException")
	public ResponseEntity<Void> launchMeBadRequesException() throws Exception {
		throw new BadRequestException(StringUtils.EMPTY);
	}

}
