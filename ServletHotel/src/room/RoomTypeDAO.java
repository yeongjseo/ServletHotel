package room;

import java.util.ArrayList;
import java.util.List;

import common.PagingDO;
import database.BaseDAO;
import reserve.ReserveDO;

/*
CREATE TABLE jkhotel_room (
		id			NUMBER NOT NULL,		-- 방정보 고유번호
		type		NUMBER NOT NULL,		-- 방 유형 (1:standard/2:deluxe/3:twin-deluxe/4:superior/5:luxury)
		max_pax		NUMBER NOT NULL,		-- 수용 가능 인원
		cost		NUMBER NOT NULL,		-- 1일 사용요금
		CONSTRAINT jkhotel_room_pk PRIMARY KEY (id)
	);
*/

public class RoomTypeDAO extends BaseDAO {
	public static final String table = "jkhotel_room_type";
	private static final String sequence = "jkhotel_room_type_seq.nextval";
		
	public RoomTypeDAO() {
		super();
	}
	
	public int count(String sKey, String sVal) {
		int cnt = 0;
		StringBuffer cond = new StringBuffer();
		if (sKey.length() > 0)
			cond.append(" where "+ sKey +" like '%"+ sVal +"%' ");
		
		connect();
		try {
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
	
	public RoomTypeDTO select(int id) {
		RoomTypeDTO dto = null; 
		System.out.printf("id %s\n", id);
		connect();
		try {
			sql = "select * from " + table + " where id = ?";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				dto = new RoomTypeDTO();
				dto.setId(rs.getInt("id"));
				dto.setType(rs.getInt("type"));
				dto.setMaxPax(rs.getInt("max_pax"));
				dto.setCost(rs.getInt("cost"));
			}				
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return dto;
	}
	
	public List<RoomTypeDTO> select(int rowStart, int rowEnd, String sKey, String sVal) {  
		List<RoomTypeDTO> list = new ArrayList<RoomTypeDTO>();
		RoomTypeDTO dto = null;
		StringBuffer sql = new StringBuffer( );
		StringBuffer cond = new StringBuffer();
		if (sKey.length() > 0)
			cond.append(" where "+ sKey +" like '%"+ sVal +"%' ");
		
		
		try {
			connect();
			sql.append("select * from ( ");
			sql.append("select rownum rn, id, type, max_pax, cost from ");
			sql.append(" (select * from " + table + " " + cond.toString() + ")");
			sql.append(") where rn between " + rowStart + " and " + rowEnd);
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				dto = new RoomTypeDTO();
				dto.setRowNum(rs.getInt("rn"));
				dto.setId(rs.getInt("id"));
				dto.setType(rs.getInt("type"));
				dto.setMaxPax(rs.getInt("max_pax"));
				dto.setCost(rs.getInt("cost"));
				list.add(dto);
			}
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
			
		return list;
	} 
	public List<RoomTypeDTO> select(PagingDO paging) {  
		return select(paging.getRowStart(), paging.getRowEnd(), 
						paging.getSearchKey(), paging.getSearchVal());
	}
	
	public List<RoomTypeDTO> select() {
		List<RoomTypeDTO> list = new ArrayList<>();
		RoomTypeDTO dto = null;
		
		try {
			connect();
			sql = "select * from " + table;
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				dto = new RoomTypeDTO();
				dto.setId(rs.getInt("id"));
				dto.setType(rs.getInt("type"));
				dto.setMaxPax(rs.getInt("max_pax"));
				dto.setCost(rs.getInt("cost"));
				list.add(dto);
			}				
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return list;
	}
	
	public List<RoomTypeDTO> selectRoomsExcept(List<ReserveDO> dos) {
		List<RoomTypeDTO> list = new ArrayList<>();
		RoomTypeDTO dto = null;
		ReserveDO o = null;
		StringBuffer sql = new StringBuffer( );
		
		try {
			connect();
			sql.append("select rownum rn, id, type, max_pax, cost from " + table);
			for (int i = 0; i < dos.size(); i++) {
				o = dos.get(i);
				if (i == 0) {
					sql.append(" where id != " + o.getRoomId()); 	
				} else {
					sql.append(" and id != " + o.getRoomId());
				}
			}
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				dto = new RoomTypeDTO();
				dto.setRowNum(rs.getInt("rn"));
				dto.setId(rs.getInt("id"));
				dto.setType(rs.getInt("type"));
				dto.setMaxPax(rs.getInt("max_pax"));
				dto.setCost(rs.getInt("cost"));
				list.add(dto);
			}
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}

		return list;
	}
	
	
	public void insert(RoomTypeDTO dto) {
		
		try {
			connect();
			sql = "insert into " + table + " values (" + sequence + ", ?, ?, ?)";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getType());
			pst.setInt(2, dto.getMaxPax());
			pst.setInt(3,  dto.getCost());
			pst.executeUpdate();		
			
		} catch(Exception ex) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
	}
	
	
	
	
	
}
