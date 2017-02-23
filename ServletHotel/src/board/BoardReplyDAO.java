package board;

import java.util.ArrayList;
import java.util.List;

import common.PagingDO;
import database.BaseDAO;
import member.MemberDTO;

/*
CREATE TABLE jkhotel_board (
	id			NUMBER NOT NULL,		-- 게시물 고유번호
	user_id		NUMBER NOT NULL,		-- 작성자 고유번호
	type		NUMBER NOT NULL,		-- 게시물 종류 (0:잘못된값/1:공지사항/2:이벤트/3:미정)
	title		VARCHAR2(40) NOT NULL,	-- 제목
	content		VARCHAR2(400) NOT NULL,	-- 내용
	write_date	DATE NOT NULL,			-- 작성일
	read_count	NUMBER NOT NULL,		-- 조회수
	
	CONSTRAINT jkhotel_board_pk PRIMARY KEY (id),
	CONSTRAINT jkhotel_board_user_id_fk FOREIGN KEY (user_id) REFERENCES jkhotel_user (id)
);
*/

public class BoardReplyDAO extends BaseDAO {
	static final private String table = "jkhotel_board_reply";
	static final private String sequence = "jkhotel_board_reply_seq.nextval";
		
	public BoardReplyDAO() {
		super();
	}
	
	public int count(String boardId, String sKey, String sVal) throws Exception {
		int cnt = 0;
		StringBuffer cond = new StringBuffer();
		cond.append(" where board_id = " + boardId);
		if (sKey.length() > 0)
			cond.append(" and " + sKey +" like '%"+ sVal +"%' ");

		try {
			connect();
			
			sql = "select count(*) as cnt from " + table + cond.toString();
			System.out.println(sql);
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next( ) == true) { 
				cnt = rs.getInt("cnt");
			}
		} finally { 
			close();
		}
		return cnt;
	}
	
	public int count(String boardId, PagingDO paging) throws Exception {
		return count(boardId, paging.getSearchKey(), paging.getSearchVal());
	}
	
	public BoardReplyDTO select(String id) throws Exception {
		BoardReplyDTO dto = null; 
		System.out.printf("id %s\n", id);
		
		try {
			connect();
			sql = "select b.*, " + toChar("b.write_date", "write_time") + " from " + table + " b "+ 
					" where id = " + id;
			System.out.println(sql);
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()){
				dto = new BoardReplyDTO();
				dto.setId(rs.getInt("id"));
				dto.setBoardId(rs.getInt("board_id"));
				dto.setUserId(rs.getInt("user_id"));			
				dto.setContent(rs.getString("content"));
				dto.setWriteDate(toDate(rs.getString("write_time")));
			}				
		} finally {
			close();
		}
		return dto;
	}
	
	
	public List<BoardReplyDTO> select(String boardId, int rowStart, int rowEnd, String sKey, String sVal) throws Exception {  
		List<BoardReplyDTO> list = new ArrayList<BoardReplyDTO>();
		BoardReplyDTO dto = null;
		StringBuffer sql = new StringBuffer( );
		StringBuffer cond = new StringBuffer();
		MemberDTO memberDTO;
		cond.append(" where board_id = " + boardId + " order by id desc ");
		if (sKey.length() > 0)
			cond.append(" where "+ sKey +" like '%"+ sVal +"%' ");
		
		/*
		select b.*, to_char(b.write_date, 'yyyy/mm/dd hh24:mi:ss') write_time from ( 
			select rownum rn, id, board_id, user_id, content, write_date from (
				select * from jkhotel_board_reply  where board_id = 193 order by id desc
			)
		) b where rn between 1 and 10;
		
		*/
		
		/*
		select b.*, to_char(b.write_date, 'yyyy/mm/dd hh24:mi:ss') write_time, m.* from ( 
				  select * from (
							select rownum rn, t.* from (
								select * from jkhotel_board_reply  where board_id = 193 order by id desc
							) t
						) where rn between 1 and 10 order by id desc
				  ) b, jkhotel_user m where b.user_id = m.id;
		*/
		try {
			connect();
			sql.append("select b.*, " + toChar("b.write_date", "write_time") + ", m.* from ( ");
				sql.append("select * from (");
					sql.append("select rownum rn, t.* from (");
						sql.append("select * from " + table + " " + cond.toString());
					sql.append(") t");
				sql.append(") where rn between " + rowStart + " and " + rowEnd + "order by id desc ");	
			sql.append(") b, jkhotel_user m where b.user_id = m.id");
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				dto = new BoardReplyDTO();
				dto.setRowNum(rs.getInt("rn"));
				dto.setId(rs.getInt("id"));
				dto.setBoardId(rs.getInt("board_id"));
				dto.setUserId(rs.getInt("user_id"));			
				dto.setContent(rs.getString("content"));
				//dto.setWriteDate(rs.getDate("write_date"));
				dto.setWriteDate(toDate(rs.getString("write_time")));
				
				memberDTO = new MemberDTO();
				memberDTO.setAccount(rs.getString("account"));
				dto.setMemberDTO(memberDTO);
				list.add(dto);
			}
		} finally { 
			close();
		}
			
		return list;
	}
	
	public List<BoardReplyDTO> select(String boardId, PagingDO paging) throws Exception {  
		return select(boardId, paging.getRowStart(), paging.getRowEnd(), 
						paging.getSearchKey(), paging.getSearchVal());
	}

	public List<BoardReplyDTO> selectAll() throws Exception {
		List<BoardReplyDTO> list = new ArrayList<>();
		BoardReplyDTO dto = null;
		
		
		try {
			connect();
			sql = "select * from " + table;
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()){
				dto = new BoardReplyDTO();
				dto.setId(rs.getInt("id"));
				dto.setContent(rs.getString("content"));
				dto.setWriteDate(rs.getDate("write_date"));
				list.add(dto);
			}				
		} finally { 
			close();
		}
		return list;
	}
	
	public void insert(BoardReplyDTO dto) throws Exception {
		
		try {
			connect();
			sql = "insert into " + table + " values (" + sequence + ", ?, ?, ?, sysdate)";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getBoardId());
			pst.setInt(2, dto.getUserId());
			pst.setString(3,  dto.getContent());
			System.out.println(sql);
			pst.executeUpdate();
		} finally { 
			close();
		}
	}
	
	
	public void update(BoardReplyDTO dto) throws Exception {
		try {
			connect();
			sql = "update " + table + " set content=? where id=?"; 
			pst = cn.prepareStatement(sql);
			pst.setString(1,  dto.getContent());
			pst.setInt(2,  dto.getId());
			System.out.println(sql);
			pst.executeUpdate();		
		} finally { 
			close();
		}
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
	
	public int nextVal() throws Exception {
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
		} finally { 
			close();
		}
		return nextval;
	}
	

}



