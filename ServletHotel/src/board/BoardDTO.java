package board;

import java.sql.Date;
import java.util.List;

import member.MemberDTO;

/*
CREATE TABLE jkhotel_board (
	id			NUMBER NOT NULL,		-- 게시물 고유번호
	user_id		NUMBER NOT NULL,		-- 작성자 고유번호
	type		NUMBER NOT NULL,		-- 게시물 종류 (0:잘못된값/1:공지사항/2:이벤트/3:미정)
	title		VARCHAR2(40) NOT NULL,	-- 제목
	content		VARCHAR2(400) NOT NULL,	-- 내용
	write_date	DATE NOT NULL,			-- 작성일
	read_count	NUMBER NOT NULL,		-- 조회수
	CONSTRAINT jkhotel_board_pk PRIMARY KEY (id),
	CONSTRAINT jkhotel_board_user_id_fk FOREIGN KEY (user_id) REFERENCES jkhotel_user (id)
);
*/

public class BoardDTO {
	public static final int BOARD_TYPE_NOTICE = 1;
	public static final int BOARD_TYPE_EVENT = 2;
	public static final int BOARD_TYPE_GUEST = 3;
	public static final int BOARD_TYPE_DEFAULT = BOARD_TYPE_NOTICE;
	public static final String BOARD_TYPE_NOTICE_DESC = "notice";
	public static final String BOARD_TYPE_EVENT_DESC = "event";
	public static final String BOARD_TYPE_GUEST_DESC = "guest";
	public static final String BOARD_TYPE_DEFAULT_DESC = BOARD_TYPE_NOTICE_DESC;
	public static final String BOARD_TYPE_NOTICE_LONG_DESC = "공지사항";
	public static final String BOARD_TYPE_EVENT_LONG_DESC = "이벤트";
	public static final String BOARD_TYPE_GUEST_LONG_DESC = "방명록";
	public static final String BOARD_TYPE_ERROR_LONG_DESC = "%%에러%%";
	int id;
	int userId;
	int type;
	String title;
	String content;
	Date writeDate;
	int readCount;
	
	/* not in DB */
	int rowNum; 
	
	MemberDTO memberDTO;
	List<BoardFileDTO> boardFileDTOList;		 
	
	public List<BoardFileDTO> getBoardFileDTOList() {
		return boardFileDTOList;
	}
	public void setBoardFileDTOList(List<BoardFileDTO> boardFileDTOList) {
		this.boardFileDTOList = boardFileDTOList;
	}
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public MemberDTO getMemberDTO() {
		return memberDTO;
	}
	public void setMemberDTO(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
	}

	public static String getBoardTypeDesc(int boardType) {
		switch (boardType) {
		case BOARD_TYPE_NOTICE:
			return BOARD_TYPE_NOTICE_DESC;
		case BOARD_TYPE_EVENT:
			return BOARD_TYPE_EVENT_DESC;
		case BOARD_TYPE_GUEST:
			return BOARD_TYPE_GUEST_DESC;
		default:
			return BOARD_TYPE_NOTICE_DESC;
		}
	}
	
	public static int getBoardType(String boardType) {
		if (boardType.equals(BOARD_TYPE_NOTICE_DESC))
			return BOARD_TYPE_NOTICE;
		else if (boardType.equals(BOARD_TYPE_EVENT_DESC))
			return BOARD_TYPE_EVENT;
		else if (boardType.equals(BOARD_TYPE_GUEST_DESC))
			return BOARD_TYPE_GUEST;
		else 
			return BOARD_TYPE_DEFAULT; 
	}
	
	public static String getBoardTypeLongDesc(int boardType) {
		switch (boardType) {
		case BOARD_TYPE_NOTICE:
			return BOARD_TYPE_NOTICE_LONG_DESC;
		case BOARD_TYPE_EVENT:
			return BOARD_TYPE_EVENT_LONG_DESC;
		case BOARD_TYPE_GUEST:
			return BOARD_TYPE_GUEST_LONG_DESC;
		default:
			return BOARD_TYPE_ERROR_LONG_DESC;
		}
	}
	
	public static String getBoardTypeLongDesc(String boardType) {
		return getBoardTypeLongDesc(getBoardType(boardType));
	}
}
