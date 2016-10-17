package org.tat.api.core.exceptions;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * This class is reposible for wrapping the technical error to a user define
 * exception with HTTP code. This class can be used efficiently in code to
 * transform the technical error to user defined error with a relevant HTTP
 * response code.
 * 
 * @author satish
 *
 */
@JsonAutoDetect
@JsonInclude(Include.NON_NULL)
public class Error {
	private String code;
	private String message;
	private int status;
	static final String SPLIT_REGEX_COLON = "\\s*:\\s*";

	public Error(String message) {
		List<String> list = Arrays.asList(message.split(SPLIT_REGEX_COLON));
		this.code = list.get(0);
		this.message = list.get(1);
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
