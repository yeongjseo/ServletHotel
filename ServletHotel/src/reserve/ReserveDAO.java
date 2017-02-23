package reserve;

import java.util.ArrayList;
import java.util.List;

import board.BoardDTO;
import common.PagingDO;
import database.BaseDAO;
import member.MemberDAO;
import member.MemberDTO;
import room.RoomDAO;
import room.RoomDTO;
import room.RoomFileDAO;
import room.RoomFileDTO;
import room.RoomTypeDAO;
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

public class ReserveDAO extends BaseDAO {
	static final private String table = "jkhotel_reservation";
	static final private String sequence = "jkhotel_reservation_seq.nextval";
		
	public ReserveDAO() {
		super();
	}
	
	public int count(int type, String sKey, String sVal) {
		int cnt = 0;
		StringBuffer cond = new StringBuffer();
		if (sKey.length() > 0)
			cond.append(" where "+ sKey +" like '%"+ sVal +"%' ");
		
		
		try {
			connect();
			sql = "select count(*) as cnt from " + table + cond.toString();
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next( ) == true) { 
				cnt = rs.getInt("cnt");
			}
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return cnt;
	}
	
	public int count(int type, PagingDO paging) {
		return count(type, paging.getSearchKey(), paging.getSearchVal());
	}
	
	public ReserveDTO select(String id) {
		ReserveDTO dto = null; 
		System.out.printf("id %s\n", id);
		
		
		try {
			connect();
			sql = "select * from " + table + " where id = " + id;
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()){
				dto = new ReserveDTO();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setRoomId(rs.getInt("room_id"));
				dto.setDateStart(rs.getDate("s_date"));
				dto.setDateEnd(rs.getDate("e_date"));
				dto.setDateReserve(rs.getDate("r_date"));
				dto.setPax(rs.getInt("pax"));
				dto.setBreakfast(rs.getInt("breakfast"));
			}				
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return dto;
	}
	
	public List<ReserveDTO> selectByMemberId(String memberId) {
		List<ReserveDTO> list = new ArrayList<>();
		ReserveDTO dto = null; 
		MemberDTO memberDTO = null;
		RoomTypeDTO roomTypeDTO = null;
		RoomDTO roomDTO = null;
		RoomFileDTO roomFileDTO = null;
		System.out.printf("memberId %s\n", memberId);
		StringBuffer sql = new StringBuffer();

		/*
		select re.*, u.account, r.ROOM_TYPE_ID, r.room_num, rf.filename from 
			(select * from jkhotel_reservation where user_id = 104) re, 
			jkhotel_user u, jkhotel_room r, jkhotel_room_type rt, jkhotel_room_file rf 
		where re.user_id = u.id and re.room_id = r.id and r.room_type_id = rt.id and rf.id = r.room_type_id;
		*/
		
		/*
		select re.*, to_char (r_date,  'yyyy/mm/dd hh24:mi:ss') as r_time, 
			u.account, r.ROOM_TYPE_ID, r.room_num, rf.filename from (
				select * from jkhotel_reservation where user_id = 1) re, jkhotel_user u, 
				    jkhotel_room r, jkhotel_room_type rt, jkhotel_room_file rf 
		where re.user_id = u.id and re.room_id = r.id and r.room_type_id = rt.id and rf.id = r.room_type_id;
		*/
		try {
			connect();
			sql.append("select re.*, " + toChar("r_date", "r_time") + ", u.account, r.ROOM_TYPE_ID, r.room_num, rf.filename from (");
				sql.append("select * from jkhotel_reservation where user_id = " + memberId + ") ");
			sql.append("re, " + MemberDAO.table + " u, " + RoomDAO.table + " r, " +RoomTypeDAO.table + " rt, " + RoomFileDAO.table + " rf ");
			sql.append("where re.user_id = u.id and re.room_id = r.id and r.room_type_id = rt.id and rf.id = r.room_type_id");
					
			System.out.println(sql);
			pst = cn.prepareStatement(sql.toString());
			rs = pst.executeQuery();
			while (rs.next()){
				dto = new ReserveDTO();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setRoomId(rs.getInt("room_id"));
				dto.setDateStart(rs.getDate("s_date"));
				dto.setDateEnd(rs.getDate("e_date"));
				//dto.setDateReserve(rs.getDate("r_date"));
				dto.setDateReserve(toDate(rs.getString("r_time")));
				dto.setPax(rs.getInt("pax"));
				dto.setBreakfast(rs.getInt("breakfast"));
				
				memberDTO = new MemberDTO();
				memberDTO.setAccount(rs.getString("account"));
				dto.setMemberDTO(memberDTO);
				
				roomDTO = new RoomDTO();
				roomDTO.setRoomNum(rs.getInt("room_num"));
				dto.setRoomDTO(roomDTO);
				
				roomTypeDTO = new RoomTypeDTO();
				roomTypeDTO.setType(rs.getInt("room_type_id"));
				dto.setRoomTypeDTO(roomTypeDTO);
				
				roomFileDTO = new RoomFileDTO();
				roomFileDTO.setFilename(rs.getString("filename"));
				dto.setRoomFileDTO(roomFileDTO);
				
				
				list.add(dto);
			}				
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return list;
	}
	
	public List<ReserveDTO> select(int rowStart, int rowEnd, String sKey, String sVal) {  
		List<ReserveDTO> list = new ArrayList<ReserveDTO>();
		ReserveDTO dto = null;
		StringBuffer sql = new StringBuffer( );
		StringBuffer cond = new StringBuffer();
		if (sKey.length() > 0)
			cond.append(" where "+ sKey +" like '%"+ sVal +"%' ");

		try {
			connect();
			sql.append("select * from ( ");
				sql.append("select rownum rn, id, user_id, room_id, s_date, e_date, r_date, pax, breakfast from ");
				sql.append(" (select * from " + table + " " + cond.toString() + ")");
			sql.append(") where rn between " + rowStart + " and " + rowEnd);
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				dto = new ReserveDTO();
				dto.setRowNum(rs.getInt("rn"));
				dto.setId(rs.getInt("id"));
				
				dto.setUserId(rs.getInt("user_id"));
				dto.setRoomId(rs.getInt("room_id"));
				dto.setDateStart(rs.getDate("s_date"));
				dto.setDateEnd(rs.getDate("e_date"));
				dto.setDateReserve(rs.getDate("r_date"));
				dto.setPax(rs.getInt("pax"));
				dto.setBreakfast(rs.getInt("breakfast"));
				list.add(dto);
			}
		} catch (Exception ex ) {
			System.out.println(ex.toString());
		} finally { 
			close();
		}
			
		return list;
	}
	
	public List<ReserveDTO> select(PagingDO paging) {  
		return select(paging.getRowStart(), paging.getRowEnd(), paging.getSearchKey(), paging.getSearchVal());
	}

	
	public List<ReserveDO> selectReservedRooms(String dateStart, String dateEnd) {  
		List<ReserveDO> list = new ArrayList<ReserveDO>();
		ReserveDO o = null;
		StringBuffer sql = new StringBuffer();
		
		// ----------S============E-----------
		// ----------[-----s------)-----------
		// ------s---]------------[------e----
		// ----------(-----e------]-----------
		try {
			connect();
			sql.append("select distinct(room_id) from " + table + " where ");
			sql.append(makeDateCond(dateStart, dateEnd).toString());
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				o = new ReserveDO();
				o.setRoomId(rs.getInt("room_id"));
				list.add(o);
			}
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return list;
	}

	
	public List<RoomTypeDTO> selectVacantRoomType(String dateStart, String dateEnd) {
		List<RoomTypeDTO> list = new ArrayList<RoomTypeDTO>();
		RoomTypeDTO roomTypeDTO = null;
		StringBuffer sql = new StringBuffer();
		
		try {
			connect();
			
			sql.append("select * from " + RoomTypeDAO.table + " where type in (");
				sql.append("select distinct(room_type_id) from (");
					sql.append("select * from " + RoomDAO.table + " where id not in (");
						sql.append("select room_id from " + table + " where ");
						sql.append(makeDateCond(dateStart, dateEnd).toString());
					sql.append(")");
				sql.append(")");
			sql.append(") order by type");
			
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				roomTypeDTO = new RoomTypeDTO();
				roomTypeDTO.setId(rs.getInt("id"));
				roomTypeDTO.setType(rs.getInt("type"));
				roomTypeDTO.setMaxPax(rs.getInt("max_pax"));
				roomTypeDTO.setCost(rs.getInt("cost"));
				list.add(roomTypeDTO);
			}
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return list;
	}

	// search----------dateStart==================dateEnd-------------
	// --------s_date===============e_date------------------------
	// ------===================s_date=====e_date------------------------	
	// ----------------------------s_date=====================e_date===
	
	public StringBuffer makeDateCond(String dateStart, String dateEnd) {
		StringBuffer cond = new StringBuffer();
		cond.append("('" + dateStart + "' >= s_date and '" + dateStart + "' < e_date) or ");
		cond.append("('" + dateStart + "' <= s_date and '" + dateEnd + "' >= e_date) or ");
		cond.append("('" + dateEnd + "' > s_date and '" + dateEnd + "' <= e_date)");
		return cond;
	}
	
	public RoomDTO selectVacantAnyRoom(String roomType, String dateStart, String dateEnd) {
		RoomDTO roomDTO = null;
		StringBuffer sql = new StringBuffer();
		
		try {
			connect();
			sql.append("select * from (");
				sql.append("select * from " + RoomDAO.table + " where id not in (");
					sql.append("select room_id from " + table + " where ");
					sql.append(makeDateCond(dateStart, dateEnd).toString());
				sql.append(")");
			sql.append(") where room_type_id = " + roomType);
			
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			if (rs.next()) {
				roomDTO = new RoomDTO();
				roomDTO.setId(rs.getInt("id"));
				roomDTO.setRoomTypeId(rs.getInt("room_type_id"));
				roomDTO.setRoomNum(rs.getInt("room_num"));
			}
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return roomDTO;
		
		
		
	}
	
	public List<ReserveDTO> selectByRoomIdandDate(int roomId, String dateStart, String dateEnd) {
		List<ReserveDTO> list = new ArrayList<ReserveDTO>();
		ReserveDTO dto = null;
		StringBuffer sql = new StringBuffer();
		try {
			connect();
			sql.append("select * from " + ReserveDAO.table + " where (");
				sql.append("( room_id =" + roomId + ") and (");
					sql.append(makeDateCond(dateStart, dateEnd).toString());
				sql.append(")");
			sql.append(")");
			
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				dto = new ReserveDTO();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setRoomId(rs.getInt("room_id"));
				dto.setDateStart(rs.getDate("s_date"));
				dto.setDateEnd(rs.getDate("e_date"));
				dto.setDateReserve(rs.getDate("r_date"));
				list.add(dto);
			}
			
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return list;
	}
	
	public List<ReserveDTO> select() {
		List<ReserveDTO> list = new ArrayList<>();
		ReserveDTO dto = null;
		
		connect();
		try {
			sql = "select * from " + table;
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()){
				dto = new ReserveDTO();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setRoomId(rs.getInt("room_id"));
				dto.setDateStart(rs.getDate("s_date"));
				dto.setDateEnd(rs.getDate("e_date"));
				dto.setDateReserve(rs.getDate("r_date"));
				dto.setPax(rs.getInt("pax"));
				dto.setBreakfast(rs.getInt("breakfast"));
				list.add(dto);
			}				
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return list;
	}
	
	public void insert(ReserveDTO dto) throws Exception {
		try {
			connect();
			sql = "insert into " + table + " values (" + sequence + ", ?, ?, ?, ?, sysdate, ?, ?)";
			System.out.println(sql);
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getUserId());
			pst.setInt(2, dto.getRoomId());
			pst.setDate(3, dto.getDateStart());
			pst.setDate(4, dto.getDateEnd());
			pst.setInt(5, dto.getPax());
			pst.setInt(6, dto.getBreakfast());
			pst.executeUpdate();		

		} finally { 
			close();
		}
	}
	
	public void insertByNextVal(ReserveDTO dto, int reserveId) throws Exception {
		try {
			connect();
			sql = "insert into " + table + " values (" + reserveId + ", ?, ?, ?, ?, sysdate, ?, ?)";
			System.out.println(sql);
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getUserId());
			pst.setInt(2, dto.getRoomId());
			pst.setDate(3, dto.getDateStart());
			pst.setDate(4, dto.getDateEnd());
			pst.setInt(5, dto.getPax());
			pst.setInt(6, dto.getBreakfast());
			pst.executeUpdate();		

		} finally { 
			close();
		}
	}
	

	// IT'S NOT WORKING B.O. ORACLE SEQUENCE CACHE 
	public String seqNum() {
		String num = "0";
		String sequence = "jkhotel_reservation_seq";
		try {
			connect();
			sql = "select last_number from user_sequences where sequence_name=upper('" + sequence + "')";
			System.out.println(sql);
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				num = rs.getString("last_number");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally { 
			close();
		}
		return num;
	}
	
	public String selectLastId() {
		StringBuffer sql = new StringBuffer();
		String id = "0";
		try {
			connect();
			sql.append("select id from (");
				sql.append("select rownum rn, id from " + table + " order by id desc");
			sql.append(") where rn = 1");	
			System.out.println(sql);
			pst = cn.prepareStatement(sql.toString());
			rs = pst.executeQuery();
			if (rs.next()) {
				id = rs.getString("id");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally { 
			close();
		}
		return id;
	}

	public int nextVal() {
		int nextval = 0;
		try {
			connect();
			sql = "select " + sequence + " from dual";
			System.out.println(sql);
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next( ) == true) { 
				nextval = rs.getInt("nextval");
			}
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return nextval;
	}
	
	public void delete(String id) throws Exception {
		try {
			connect();
			sql = "delete from " + table + " where id = " + id;
			System.out.println(sql);
			pst = cn.prepareStatement(sql);
			pst.executeUpdate();
		} finally { 
			close();
		}
	}

	
	
	
}


