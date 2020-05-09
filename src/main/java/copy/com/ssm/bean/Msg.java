package copy.com.ssm.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author SEN.CHEN
 *
 */
public class Msg {
	private String code;
	private String msg;
	private Map<String, Object> extend = new HashMap<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getExtend() {
		return extend;
	}

	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}

	public Msg success() {
		Msg msg = new Msg();
		msg.setCode("200");
		msg.setMsg("success");
		return msg;
	}

	public Msg fail() {
		Msg msg = new Msg();
		msg.setCode("400");
		msg.setMsg("fail");
		return msg;
	}

	public Msg add(String key, Object value) {
		this.getExtend().put(key, value);
		return this;
	}

}
