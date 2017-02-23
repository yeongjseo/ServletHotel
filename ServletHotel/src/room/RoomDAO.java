package room;

import java.util.ArrayList;
import java.util.List;

import common.PagingDO;
import database.BaseDAO;

/*
CREATE TABLE jkhotel_room (
		id			NUMBER NOT NULL,	-- 현황 고유번호
		room_id		NUMBER NOT NULL,	-- 방정보 고유번호
		room_num	NUMBER NOT NULL,	-- 호실
		pax			NUMBER NOT NULL,	-- 입실 인원
		s_date		DATE NOT NULL,		-- 입실 일자
		e_date		DATE NOT NULL,		-- 퇴실 '예정' 일자
		CONSTRAINT jkhotel_room_state_pk PRIMARY KEY (id),
		CONSTRAINT jkhotel_room_state_uid_fk FOREIGN KEY (user_id) REFERENCES jkhotel_user (id),
		CONSTRAINT jkhotel_room_state_rid_fk FOREIGN KEY (room_id) REFERENCES jkhotel_room (id)
	);
 */
public class RoomDAO extends BaseDAO {
	public static final String table = "jkhotel_room";
	private static final String sequence = "jkhotel_room_seq.nextval";
		
	public RoomDAO() {
		super();
	}
	
	public int count(String sKey, String sVal) {
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
	
	public int count(PagingDO paging) {
		return count(paging.getSearchKey(), paging.getSearchVal());
	}
	
	public RoomDTO select(int id) {
		RoomDTO dto = null; 
		System.out.printf("id %s\n", id);
		
		
		try {
			connect();
			sql = "select * from " + table + " where id = ?";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				dto = new RoomDTO();
				dto.setId(rs.getInt("id"));
				dto.setRoomTypeId(rs.getInt("room_type_id"));
				dto.setRoomNum(rs.getInt("room_num"));
			}				
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return dto;
	}
	
	
	public List<RoomDTO> select(int rowStart, int rowEnd, String sKey, String sVal) {  
		List<RoomDTO> list = new ArrayList<RoomDTO>();
		RoomDTO dto = null;
		StringBuffer sql = new StringBuffer( );
		StringBuffer cond = new StringBuffer();
		if (sKey.length() > 0)
			cond.append(" where "+ sKey +" like '%"+ sVal +"%' ");
		
		try {
			connect();
			sql.append("select * from ( ");
				sql.append("select rownum rn, id, room_num, room_type_id from (");
					sql.append("select * from " + table + " " + cond.toString());
				sql.append(") ");	
			sql.append(") where rn between " + rowStart + " and " + rowEnd);
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				dto = new RoomDTO();
				dto.setRowNum(rs.getInt("rn"));
				dto.setRoomNum(rs.getInt("room_num"));
				dto.setRoomTypeId(rs.getInt("room_type_id"));
				list.add(dto);
			}
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
			
		return list;
	}
	
	public List<RoomDTO> select(PagingDO paging) {  
		return select(paging.getRowStart(), paging.getRowEnd(), 
						paging.getSearchKey(), paging.getSearchVal());
	}
	
	
	public List<RoomDTO> selectWithRoomTypeDTO(int rowStart, int rowEnd, String sKey, String sVal) {  
		List<RoomDTO> list = new ArrayList<RoomDTO>();
		RoomDTO dto = null;
		RoomTypeDTO roomTypeDTO = null;
		StringBuffer sql = new StringBuffer( );
		StringBuffer cond = new StringBuffer();
		if (sKey.length() > 0)
			cond.append(" where "+ sKey +" like '%"+ sVal +"%' ");
		
	
		try {
			connect();
			/*
			sql.append("select * from ( ");
				sql.append("select rownum rn, id, room_num, room_type_id from (");
					sql.append("select * from " + table + " " + cond.toString());
				sql.append(") ");	
			sql.append(") where rn between " + rowStart + " and " + rowEnd);
			*/
			
			/* TODO: Search */
			sql.append("select * from ( ");
				sql.append("select rownum rn, r.id, r.room_type_id, r.room_num, t.type room_type, t.MAX_PAX, t.COST from ");
				sql.append("jkhotel_room r, jkhotel_room_type t where r.ROOM_TYPE_ID = t.id");
			sql.append(") where rn between " + rowStart + " and " + rowEnd);
			
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				dto = new RoomDTO();
				dto.setRowNum(rs.getInt("rn"));
				dto.setId(rs.getInt("id"));
				dto.setRoomNum(rs.getInt("room_num"));
				dto.setRoomTypeId(rs.getInt("room_type_id"));
				
				roomTypeDTO = new RoomTypeDTO();
				roomTypeDTO.setType(rs.getInt("room_type"));
				roomTypeDTO.setMaxPax(rs.getInt("max_pax"));
				roomTypeDTO.setCost(rs.getInt("cost"));
				
				dto.setRoomTypeDTO(roomTypeDTO);
				list.add(dto);
			}
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
			
		return list;
	}
	
	public List<RoomDTO> selectWithRoomTypeDTO(PagingDO paging) {  
		return selectWithRoomTypeDTO(paging.getRowStart(), paging.getRowEnd(), 
						paging.getSearchKey(), paging.getSearchVal());
	}	

	public List<RoomDTO> select() {
		List<RoomDTO> list = new ArrayList<>();
		RoomDTO dto = null;
		
		
		try {
			connect();
			sql = "select * from " + table;
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				dto = new RoomDTO();
				dto.setId(rs.getInt("id"));
				list.add(dto);
			}				
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return list;
	}
	
	
	public void insert(RoomDTO dto) {
		
		try {
			connect();
			sql = "insert into " + table + " values (" + sequence + ", ?, ?, ?, ?, ?, ?)";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getId());
			pst.executeUpdate();		
			
		} catch(Exception ex) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
	}

}


