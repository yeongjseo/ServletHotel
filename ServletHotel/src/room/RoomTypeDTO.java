package room;

import java.util.List;

/*
CREATE TABLE jkhotel_room_type (
		id			NUMBER NOT NULL,		-- 방정보 고유번호
		type		NUMBER NOT NULL,		-- 방 유형 (1:standard/2:deluxe/3:twin-deluxe/4:superior/5:luxury)
		max_pax		NUMBER NOT NULL,		-- 수용 가능 인원
		cost		NUMBER NOT NULL,		-- 1일 사용요금
		CONSTRAINT jkhotel_room_pk PRIMARY KEY (id)
	);
*/
public class RoomTypeDTO {
	public final static int ROOM_TYPE_ALL				= 0; /* internal */
	public final static int ROOM_TYPE_STANDARD			= 1;
	public final static int ROOM_TYPE_DELUXE 			= 2;
	public final static int ROOM_TYPE_TWIN_DELUXE		= 3;
	public final static int ROOM_TYPE_TWIN_SUPERIOR		= 4;
	public final static int ROOM_TYPE_LUXURY			= 5;
	int id;
	int type;
	int maxPax;
	int cost;
	List<RoomFileDTO> files;
	


	int rowNum; /* not in DB */
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getMaxPax() {
		return maxPax;
	}
	public void setMaxPax(int maxPax) {
		this.maxPax = maxPax;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getRowNum() {
		return cost;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
	public List<RoomFileDTO> getFiles() {
		return files;
	}
	public void setFiles(List<RoomFileDTO> files) {
		this.files = files;
	}
	
	public String getTypeDesc() {
		switch (type) {
		case ROOM_TYPE_STANDARD:
			return "표준";
		case ROOM_TYPE_DELUXE:
			return "디럭스";
		case ROOM_TYPE_TWIN_DELUXE:
			return "트윈-디럭스";
		case ROOM_TYPE_TWIN_SUPERIOR:
			return "슈피리어";
		case ROOM_TYPE_LUXURY:
			return "럭셔리";
		default:
			return "%%에러%%";
		}
	}
	
	
}
