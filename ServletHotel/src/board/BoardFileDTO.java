package board;


/*
CREATE TABLE jkhotel_board_file (
	id			NUMBER NOT NULL,		-- 첨부파일 고유번호
	board_id	NUMBER NOT NULL,		-- 게시물 고유번호
	filename		VARCHAR2(256) NOT NULL,	-- 파일명 (SHA-256 반환값 + 확장자 . 포함 총 4자 = 70)
	saved_filename	VARCHAR2(256) NOT NULL,	-- 파일명 (SHA-256 반환값 + 확장자 . 포함 총 4자 = 70)
	filesize	NUMBER NOT NULL,		-- 첨부파일 크기
	CONSTRAINT jkhotel_board_file_pk PRIMARY KEY (id),
	CONSTRAINT jkhotel_board_file_aid_fk FOREIGN KEY (board_id) REFERENCES jkhotel_board (id)
);
*/
public class BoardFileDTO {
	int id;
	int boardId;
	String filename;
	String savedFilename;
	int filesize;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSavedFilename() {
		return savedFilename;
	}
	public void setSavedFilename(String savedFilename) {
		this.savedFilename = savedFilename;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	
	
	
}
