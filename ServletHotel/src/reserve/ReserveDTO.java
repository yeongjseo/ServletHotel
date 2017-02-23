package reserve;

import java.sql.Date;

import member.MemberDTO;
import room.RoomDTO;
import room.RoomFileDTO;
import room.RoomTypeDTO;

/*
CREATE TABLE jkhotel_reservation (
	id			NUMBER NOT NULL,		-- 예약 고유번호
	user_id		NUMBER NOT NULL,		-- 사용자 고유번호
	room_id		NUMBER NOT NULL,		-- 방정보 고유번호 (예약시 선택한 방 종류)
	s_date		DATE NOT NULL,			-- 숙박 시작일
	e_date		DATE NOT NULL,			-- 숙박 종료일
	r_date		DATE NOT NULL,			-- 예약일
	pax			NUMBER NOT NULL,		-- 입실 예정 인원
	breakfast	NUMBER(1) NOT NULL,		-- 조식 여부
	CONSTRAINT jkhotel_reservation_pk PRIMARY KEY (id),
	CONSTRAINT jkhotel_reservation_user_id_fk FOREIGN KEY (user_id) REFERENCES jkhotel_user (id),
	CONSTRAINT jkhotel_reservation_room_id_fk FOREIGN KEY (room_id) REFERENCES jkhotel_room (id)
);
 */
public class ReserveDTO {
	int id;
	int userId;
	int roomId;
	Date dateStart;
	Date dateEnd;
	Date dateReserve;
	int pax;
	int breakfast;
	/* not in DB */
	int rowNum; 
	RoomTypeDTO roomTypeDTO = null;
	RoomDTO roomDTO = null;
	MemberDTO memberDTO = null;
	RoomFileDTO roomFileDTO = null;
	
	
	public RoomTypeDTO getRoomTypeDTO() {
		return roomTypeDTO;
	}
	public void setRoomTypeDTO(RoomTypeDTO roomTypeDTO) {
		this.roomTypeDTO = roomTypeDTO;
	}
	public RoomDTO getRoomDTO() {
		return roomDTO;
	}
	public void setRoomDTO(RoomDTO roomDTO) {
		this.roomDTO = roomDTO;
	}
	public MemberDTO getMemberDTO() {
		return memberDTO;
	}
	public void setMemberDTO(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
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
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public Date getDateReserve() {
		return dateReserve;
	}
	public void setDateReserve(Date dateReserve) {
		this.dateReserve = dateReserve;
	}
	public int getPax() {
		return pax;
	}
	public void setPax(int pax) {
		this.pax = pax;
	}
	public int getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(int breakfast) {
		this.breakfast = breakfast;
	}

	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public RoomFileDTO getRoomFileDTO() {
		return roomFileDTO;
	}
	public void setRoomFileDTO(RoomFileDTO roomFileDTO) {
		this.roomFileDTO = roomFileDTO;
	}
	
}
