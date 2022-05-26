package com.minsait.architecture.exceptions.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import com.minsait.architecture.exceptions.BadRequestException;
import com.minsait.architecture.exceptions.NotFoundException;
import com.minsait.architecture.exceptions.OnesaitException;
import com.minsait.architecture.exceptions.UserNotFoundException;
import com.minsait.architecture.model.error.ResponseError;
import com.minsait.architecture.model.error.ResponseErrorList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
class BaseControllerExceptionHandlerTest {

    @InjectMocks
    TestController testController;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @InjectMocks
    protected BaseControllerExceptionHandler baseControllerExceptionHandler;


    @Test
    public void testHandleUserException() throws Exception {
        log.info("->Entering into testHandleUserException");

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, 
                () -> testController.launchMeUserException());
        
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("User exception");
    }

    @Test
    public void testHandleException() throws Exception {
        log.info("->Entering into testHandleException");
        
        Exception exception = assertThrows(Exception.class, () -> testController.launchMeException());

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Exception message");
    }

    @Test
    public void testArchetypeRuntimeException() throws Exception {
        log.info("->Entering into testArchetypeRuntimeException");
        
        OnesaitException exception = assertThrows(OnesaitException.class, 
                () -> testController.launchMeMotionException());

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("1");
    }

    @Test
    public void testHandleNotFoundException() throws Exception {
        log.info("->Entering into testHandleUserException");
        
        NotFoundException exception = assertThrows(NotFoundException.class, 
                () -> testController.launchMeNotFoundException());

        assertThat(exception).isNotNull();
        assertThat(exception.getOntology()).isEqualTo("ontology");
        assertThat(exception.getId()).isEqualTo("id");
    }

    @Test
    public void testHandleBadRequestException() throws Exception {
        log.info("->Entering into testHandleUserException");
        
        BadRequestException exception = assertThrows(BadRequestException.class, 
                () -> testController.launchMeBadRequesException());

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(StringUtils.EMPTY);
    }

    @Test
    public void shouldHandleBindException() throws Exception {

        final MapBindingResult mapBindingResult = new MapBindingResult(new HashMap(), StringUtils.EMPTY);
        mapBindingResult.addError(new ObjectError(StringUtils.EMPTY, StringUtils.EMPTY));
        ResponseEntity<Object> result = baseControllerExceptionHandler.handleBindException(new BindException(mapBindingResult), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, null);

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldMethodArgumentNotValid() throws Exception {

        final MapBindingResult mapBindingResult = new MapBindingResult(new HashMap(), StringUtils.EMPTY);
        mapBindingResult.addError(new ObjectError(StringUtils.EMPTY, StringUtils.EMPTY));
        final MethodParameter methodParameter = new MethodParameter(this.getClass().getMethod("shouldHandleBindException"), -1);
        ResponseEntity<Object> result = baseControllerExceptionHandler.handleMethodArgumentNotValid(
                new MethodArgumentNotValidException(methodParameter, mapBindingResult), new HttpHeaders(), HttpStatus.BAD_REQUEST, null);

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldhandleHttpMessageNotReadable() throws Exception {

        final MapBindingResult mapBindingResult = new MapBindingResult(new HashMap(), StringUtils.EMPTY);
        mapBindingResult.addError(new ObjectError(StringUtils.EMPTY, StringUtils.EMPTY));
        ResponseEntity<Object> result = baseControllerExceptionHandler.handleHttpMessageNotReadable(new HttpMessageNotReadableException(StringUtils.EMPTY),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, null);

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createNotFoundException() {
        final NotFoundException exception = new NotFoundException(StringUtils.EMPTY);

        assertNotNull(exception);
    }

    @Test
    public void createMotionException() {
        final OnesaitException exception = new OnesaitException(StringUtils.EMPTY, null);

        assertNotNull(exception);
    }

    @Test
    public void createBadRequestException() {
        final OnesaitException exception = new BadRequestException(StringUtils.EMPTY, null);

        assertNotNull(exception);
    }

    @Test
    public void createResponseErrorList() {
        final ResponseErrorList errorList = new ResponseErrorList();

        assertNotNull(errorList);
    }

    @Test
    public void createResponseErrorEmpty() {
        final ResponseError error = new ResponseError();

        assertNotNull(error);
    }

    @Test
    public void createResponseError() {
        final ResponseError error = new ResponseError(StringUtils.EMPTY, StringUtils.EMPTY);

        assertNotNull(error);
    }

}
