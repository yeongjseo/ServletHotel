package room;

import java.util.ArrayList;
import java.util.List;

import database.BaseDAO;

/*
CREATE TABLE jkhotel_room_file (
		id			NUMBER NOT NULL,		-- 방 샘플 이미지 파일 고유번호
		room_type_id		NUMBER NOT NULL,		-- 방 고유번호
		filename	VARCHAR2(70) NOT NULL,	-- 파일명 (SHA-256 반환값 + 확장자 . 포함 총 4자 = 70)
		CONSTRAINT jkhotel_room_file_pk PRIMARY KEY (id),
		CONSTRAINT jkhotel_room_file_room_id_fk FOREIGN KEY (room_id) REFERENCES jkhotel_room (id)
	);
*/

public class RoomFileDAO extends BaseDAO {
	public static final String table = "jkhotel_room_file";
	private static final String sequence = "jkhotel_room_file_seq.nextval";
		
	public RoomFileDAO() {
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
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
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
	
	public List<RoomFileDTO> selectAll() {
		List<RoomFileDTO> list = new ArrayList<>();
		RoomFileDTO dto = null;
		
		connect();
		try {
			sql = "select * from " + table;
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				dto = new RoomFileDTO();
				dto.setId(rs.getInt("id"));
				dto.setRoomId(rs.getInt("room_type_id"));
				dto.setFilename(rs.getString("filename"));
				list.add(dto);
			}				
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return list;
	}

	public List<RoomFileDTO> select(int roomId) {
		List<RoomFileDTO> list = new ArrayList<>();
		RoomFileDTO dto = null;
		
		connect();
		try {
			sql = "select * from " + table + " where room_type_id = " + roomId;
			pst = cn.prepareStatement(sql);
			System.out.println(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				dto = new RoomFileDTO();
				dto.setId(rs.getInt("id"));
				dto.setRoomId(rs.getInt("room_type_id"));
				dto.setFilename(rs.getString("filename"));
				list.add(dto);
			}				
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return list;
	}
	
	
	
	
	public void insert(RoomFileDTO dto) {
		
		try {
			connect();
			sql = "insert into " + table + " values (" + sequence + ", ?, ?)";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getRoomTypeId());
			pst.setString(2, dto.getFilename());
			pst.executeUpdate();		
			
		} catch(Exception ex) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
	}
	
	
	
	
	
}
