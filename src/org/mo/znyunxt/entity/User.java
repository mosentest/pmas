package org.mo.znyunxt.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 登录模块用的
 */
public class User implements Serializable {
	/**
	 * 账户名 loginname
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 验证码
	 */
	private String vcode;
	/**
	 * 登录令牌
	 */
	private String loginTicket;
	/**
	 * 执行代码
	 */
	private String execution;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getLoginTicket() {
		return loginTicket;
	}

	public void setLoginTicket(String loginTicket) {
		this.loginTicket = loginTicket;
	}

	public String getExecution() {
		return execution;
	}

	public void setExecution(String execution) {
		this.execution = execution;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", vcode=" + vcode + ", loginTicket=" + loginTicket
				+ ", execution=" + execution + "]";
	}

}
