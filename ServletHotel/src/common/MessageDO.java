package common;

import java.util.HashMap;
import java.util.Map;

public class MessageDO {
	String result;
	String code;
	String reason;
	Boolean redirect;

	public Boolean getRedirect() {
		return redirect;
	}
	public void setRedirect(Boolean redirect) {
		this.redirect = redirect;
	}
	Map<String, Boolean> error = new HashMap<>();
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Map<String, Boolean> getError() {
		return error;
	}
	public void setError(Map<String, Boolean> error) {
		this.error = error;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
