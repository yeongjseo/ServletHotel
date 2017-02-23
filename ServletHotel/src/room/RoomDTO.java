package room;

import java.sql.Date;
import java.util.List;

import reserve.ReserveDTO;

/*
CREATE TABLE jkhotel_room (
		id			NUMBER NOT NULL,	-- 현황 고유번호
		room_num	NUMBER NOT NULL,	-- 호실
		room_type_id		NUMBER NOT NULL,	-- 방정보 고유번호
		CONSTRAINT jkhotel_room_state_pk PRIMARY KEY (id),
		CONSTRAINT jkhotel_room_state_uid_fk FOREIGN KEY (user_id) REFERENCES jkhotel_user (id),
		CONSTRAINT jkhotel_room_state_rid_fk FOREIGN KEY (room_id) REFERENCES jkhotel_room (id)
	);
*/
public class RoomDTO {
	int id;
	int roomTypeId;
	int roomNum;
	int pax;
	Date startDate;
	Date endDate;
	
	int rowNum; /* not in db */
	RoomTypeDTO roomTypeDTO;
	List<ReserveDTO> reserveDTO;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public int getPax() {
		return pax;
	}
	public void setPax(int pax) {
		this.pax = pax;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public RoomTypeDTO getRoomTypeDTO() {
		return roomTypeDTO;
	}
	public void setRoomTypeDTO(RoomTypeDTO roomTypeDTO) {
		this.roomTypeDTO = roomTypeDTO;
	}
	public List<ReserveDTO> getReserveDTO() {
		return reserveDTO;
	}
	public void setReserveDTO(List<ReserveDTO> reserveDTO) {
		this.reserveDTO = reserveDTO;
	}

	
}
