package room;

/*
CREATE TABLE jkhotel_room_eval (
		id			NUMBER NOT NULL,		-- 방 평가 고유번호
		user_id		NUMBER NOT NULL,		-- 작성자 고유번호
		rate		NUMBER NOT NULL,		-- 평점 (만점은 안정함)
		content		VARCHAR2(80) NOT NULL,	-- 한줄평
		write_date	DATE NOT NULL,			-- 작성일
		CONSTRAINT jkhotel_room_eval_pk PRIMARY KEY (id),
		CONSTRAINT jkhotel_room_eval_user_id_fk FOREIGN KEY (user_id) REFERENCES jkhotel_user (id)
	);
*/

public class RoomEvalDTO {
	int id;
	int userId;
	int rate;
	String content;
	String writeDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	
}
