package member;

import java.sql.Date;

/*
CREATE TABLE jkhotel_user (
		id			NUMBER NOT NULL,		-- 사용자 고유번호
		account		VARCHAR2(20) NOT NULL,	-- 계정명
		password	VARCHAR2(20) NOT NULL,	-- 비밀번호
		nickname	VARCHAR2(20) NOT NULL,	-- 사용자명
		birthday	DATE NOT NULL,			-- 생일
		zipcode		VARCHAR(5) NOT NULL,	-- 우편번호
		address1	VARCHAR2(200) NOT NULL,	-- 주소1
		address2	VARCHAR2(200) NOT NULL,	-- 주소2
		email		VARCHAR2(80) NOT NULL,	-- 이메일 (FIXME: 길이 조절 필요)
		e_confirm	NUMBER(1) NOT NULL,		-- 이메일 소식 수신 여부
		tel			VARCHAR(11) NOT NULL,	-- 연락처
		CONSTRAINT jkhotel_user_pk PRIMARY KEY (id)
	);
*/
public class MemberDTO {
	int	id;
	String account;
	String password;
	String nickname;
	Date birthday;
	String zipcode;
	String address1;
	String address2;
	String email;
	int emailConfirm;
	String tel;
	boolean isAdmin;
	
	/* not in DB */
	int rowNum; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEmailConfirm() {
		return emailConfirm;
	}
	public void setEmailConfirm(int emailConfirm) {
		this.emailConfirm = emailConfirm;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
	public boolean getIsAdmin() {
		return (id == 1) ? true : false;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
