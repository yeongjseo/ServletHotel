package board;

import java.util.ArrayList;
import java.util.List;

import common.PagingDO;
import database.BaseDAO;

/*
CREATE TABLE jkhotel_board_file (
	id			NUMBER NOT NULL,		-- 첨부파일 고유번호
	board_id	NUMBER NOT NULL,		-- 게시물 고유번호
	filename	VARCHAR2(256) NOT NULL,	-- 파일명 (SHA-256 반환값 + 확장자 . 포함 총 4자 = 70)
	saved_filename		VARCHAR2(256) NOT NULL,	-- 파일명 (SHA-256 반환값 + 확장자 . 포함 총 4자 = 70)
	filesize	NUMBER NOT NULL,		-- 첨부파일 크기
	CONSTRAINT jkhotel_board_file_pk PRIMARY KEY (id),
	CONSTRAINT jkhotel_board_file_aid_fk FOREIGN KEY (board_id) REFERENCES jkhotel_board (id)
);
*/

public class BoardFileDAO extends BaseDAO {
	static final private String table = "jkhotel_board_file";
	static final private String sequence = "jkhotel_board_file_seq.nextval";
		
	public BoardFileDAO() {
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
	
	public BoardFileDTO select(int id) {
		BoardFileDTO dto = null; 
		System.out.printf("id %s\n", id);
		
		
		try {
			connect();
			sql = "select * from " + table + " where id = ?";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				dto = new BoardFileDTO();
				dto.setId(rs.getInt("id"));
				dto.setBoardId(rs.getInt("board_id"));
				dto.setFilename(rs.getString("filename"));
				dto.setSavedFilename(rs.getString("saved_filename"));
				dto.setFilesize(rs.getInt("filesize"));
			}				
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return dto;
	}
	
	
	public List<BoardFileDTO> selectByBoardId(int boardId) {  
		List<BoardFileDTO> list = new ArrayList<BoardFileDTO>();
		BoardFileDTO dto = null;
		try {
			connect();
			sql = "select * from " + table + " where board_id = " + boardId;
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				dto = new BoardFileDTO();
				dto.setId(rs.getInt("id"));
				dto.setBoardId(rs.getInt("board_id"));
				dto.setFilename(rs.getString("filename"));
				dto.setSavedFilename(rs.getString("saved_filename"));
				dto.setFilesize(rs.getInt("filesize"));
				list.add(dto);
			}
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
			
		return list;
	}
	

	public List<BoardFileDTO> select() {
		List<BoardFileDTO> list = new ArrayList<>();
		BoardFileDTO dto = null;
		
		try {
			connect();
			sql = "select * from " + table;
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				dto = new BoardFileDTO();
				dto.setId(rs.getInt("id"));
				dto.setBoardId(rs.getInt("board_id"));
				dto.setFilename(rs.getString("finename"));
				dto.setSavedFilename(rs.getString("saved_finename"));
				dto.setFilesize(rs.getInt("filesize"));
				list.add(dto);
			}				
		} catch (Exception ex ) { 
			System.out.println(ex.toString());
		} finally { 
			close();
		}
		return list;
	}
	
	public void insert(BoardFileDTO dto) {
		
		try {
			connect();
			sql = "insert into " + table + " values (" + sequence + ", ?, ?, ?, ?)";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getBoardId());
			pst.setString(2,  dto.getFilename());
			pst.setString(3, dto.getSavedFilename());
			pst.setInt(4, dto.getFilesize());
			pst.executeUpdate();
			
		} catch(Exception ex) {
			System.out.println(ex.toString());
		} finally { 
			close();
		}
	}
	
	public void delete(String id) {
		try {
			connect();
			sql = "delete from " + table + " where id = " + id;
			System.out.println(sql);
			pst = cn.prepareStatement(sql);
			pst.executeUpdate();
		} catch(Exception ex) {
			System.out.println(ex.toString());
		} finally { 
			close();
		}
	}


}


