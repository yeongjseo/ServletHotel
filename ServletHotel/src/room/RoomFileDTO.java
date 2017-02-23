package room;

/*
CREATE TABLE jkhotel_room_file (
		id			NUMBER NOT NULL,		-- 방 샘플 이미지 파일 고유번호
		room_typeid	NUMBER NOT NULL,		-- 방 고유번호
		filename	VARCHAR2(70) NOT NULL,	-- 파일명 (SHA-256 반환값 + 확장자 . 포함 총 4자 = 70)
		CONSTRAINT jkhotel_room_file_pk PRIMARY KEY (id),
		CONSTRAINT jkhotel_room_file_room_id_fk FOREIGN KEY (room_id) REFERENCES jkhotel_room (id)
	);
 */
public class RoomFileDTO {
	int id;
	int roomTypeId;
	String filename;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoomTypeId() {
		return this.roomTypeId;
	}
	public void setRoomId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

	
}
