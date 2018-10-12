package com.vault.jtv.exception;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.vault.jtv.beans.GlobalResponse;
import com.vault.util.MessageConstants;


@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {

	protected Logger logger;
    public GlobalExceptionHandlingControllerAdvice() {
        logger = LoggerFactory.getLogger(getClass());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse> generalException(Exception e) throws Exception{
        GlobalResponse g =new GlobalResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),MessageConstants.SERVER_DOWN);       
        return new ResponseEntity<GlobalResponse>(g,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) 
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<GlobalResponse> badRequestException(HttpServletRequest req, Exception e) throws Exception{
        GlobalResponse g =new GlobalResponse(HttpStatus.METHOD_NOT_ALLOWED.value(),HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(),MessageConstants.METHOD_NOT_ALLOWED);      
        return new ResponseEntity<GlobalResponse>(g,HttpStatus.METHOD_NOT_ALLOWED);
    }    
    
    @ResponseStatus(HttpStatus.UNAUTHORIZED)  
    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<GlobalResponse> unauthorizedException(Exception e) throws Exception{
        GlobalResponse g =new GlobalResponse(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase(),MessageConstants.UNAUTHORIZED_USER);      
        return new ResponseEntity<GlobalResponse>(g,HttpStatus.UNAUTHORIZED);
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)  
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<GlobalResponse> notFoundException(Exception e) throws Exception{
        GlobalResponse g =new GlobalResponse(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase(),MessageConstants.PAGE_NOT_FOUND);       
        return new ResponseEntity<GlobalResponse>(g,HttpStatus.NOT_FOUND);
    }
    
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)  
    @ExceptionHandler({ArithmeticException.class,NumberFormatException.class})
    public ResponseEntity<GlobalResponse> preconditionException(Exception e) throws Exception{
        GlobalResponse g =new GlobalResponse(HttpStatus.PRECONDITION_FAILED.value(),HttpStatus.PRECONDITION_FAILED.getReasonPhrase(),MessageConstants.REQUEST_NOT_PROPER);        
        return new ResponseEntity<GlobalResponse>(g,HttpStatus.PRECONDITION_FAILED);
    }
    /**
     * Convert a predefined exception to an HTTP Status code
     */
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = MessageConstants.DATA_INTEGRITY_VIOLATION) // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalResponse> conflict() {
        logger.error(MessageConstants.DIV); 
        GlobalResponse g =new GlobalResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),MessageConstants.DATA_INTEGRITY_VIOLATION);     
        return new ResponseEntity<GlobalResponse>(g,HttpStatus.INTERNAL_SERVER_ERROR);
            
    }
    /**
     * Convert a predefined exception to an HTTP Status code and specify the
     * name of a specific view that will be used to display the error.   * 
     * @return Exception view.
     */
    @ExceptionHandler({ SQLException.class, DataAccessException.class })
    public ResponseEntity<GlobalResponse> databaseError(Exception exception) {
        // Nothing to do. Return value 'databaseError' used as logical view name
        // of an error page, passed to view-resolver(s) in usual way.
        logger.error(MessageConstants.REQUEST_RAISED + exception.getClass().getSimpleName());
        GlobalResponse g =new GlobalResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),MessageConstants.DATABASE_ERROR);        
        return new ResponseEntity<GlobalResponse>(g,HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
