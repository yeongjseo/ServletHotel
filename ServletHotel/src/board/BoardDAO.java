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

public class BoardDAO extends BaseDAO {
	static final private String table = "jkhotel_board";
	static final private String sequence = "jkhotel_board_seq.nextval";
		
	public BoardDAO() {
		super();
	}
	
	public int count(String type, String sKey, String sVal) throws Exception {
		int cnt = 0;
		StringBuffer cond = new StringBuffer();
		cond.append(" where type = " + BoardDTO.getBoardType(type));
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
	
	public int count(String type, PagingDO paging) throws Exception {
		return count(type, paging.getSearchKey(), paging.getSearchVal());
	}
	
	public BoardDTO select(String type, String id) throws Exception {
		BoardDTO dto = null; 
		System.out.printf("id %s\n", id);
	
		/*
		select b.*, to_char(b.write_date , 'yyyy/mm/dd hh24:mi:ss') as write_time from 
			jkhotel_board b where id = 10 and type = 1;
		*/
		
		try {
			connect();
			// sql = "select * from " + table + " where id = ? and type = " + BoardDTO.getBoardType(type);
			sql = "select b.*, " + toChar("b.write_date", "write_time") + " from " + table + " b "+ 
					" where id = ? and type = " + BoardDTO.getBoardType(type);
			System.out.println(sql);
			pst = cn.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			if (rs.next()){
				dto = new BoardDTO();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setType(rs.getInt("type"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				//dto.setWriteDate(rs.getDate("write_date"));
				dto.setWriteDate(toDate(rs.getString("write_time")));
				dto.setReadCount(rs.getInt("read_count"));
			}				
		} finally { 
			close();
		}
		return dto;
	}
	
	
	public List<BoardDTO> select(String type, int rowStart, int rowEnd, String sKey, String sVal) throws Exception {  
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		BoardDTO dto = null;
		MemberDTO memberDTO = null;
		StringBuffer sql = new StringBuffer( );
		StringBuffer cond = new StringBuffer();
		
		cond.append(" where type = " + BoardDTO.getBoardType(type));
		if (sKey.length() > 0)
			cond.append(" and "+ sKey +" like '%"+ sVal +"%' ");
		
		/*
		select b.*, u.* from (
			select * from (
				select rownum rn, t.* from (
					select * from jkhotel_board where title like '%9%' order by id desc
				) t  
			) where rn between 1 and 10
		) b, jkhotel_user u  where b.user_id = u.id;
		*/
		
		/*
		select b.*, to_char(b.write_date, 'yyyy/mm/dd hh24:mi:ss') write_time, m.* from (
			select * from ( 
				select rownum rn, t.* from (
					select * from jkhotel_board  where type = 3 order by id desc
				) t 
			) where rn between 11 and 20 order by id desc
		) b, jkhotel_user m where b.user_id = m.id;
		*/
		try {
			connect();
			/*
			sql.append("select b.*, m.* from (");
				sql.append("select * from ( ");
					sql.append("select rownum rn, t.* from (");
						sql.append("select * from " + table + " " + cond.toString() + " order by id desc");
					sql.append(") t ");
				sql.append(") where rn between " + rowStart + " and " + rowEnd + " order by id desc");
			sql.append(") b, jkhotel_user m where b.user_id = m.id");
			*/
			
			sql.append("select b.*, " + toChar("b.write_date", "write_time") + ", m.* from (");
				sql.append("select * from ( ");
					sql.append("select rownum rn, t.* from (");
						sql.append("select * from " + table + " " + cond.toString() + " order by id desc");
					sql.append(") t ");
				sql.append(") where rn between " + rowStart + " and " + rowEnd + " order by id desc");
			sql.append(") b, jkhotel_user m where b.user_id = m.id");
			
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				dto = new BoardDTO();
				dto.setRowNum(rs.getInt("rn"));
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setType(rs.getInt("type"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				// dto.setWriteDate(rs.getDate("write_date"));
				dto.setWriteDate(toDate(rs.getString("write_time")));
				dto.setReadCount(rs.getInt("read_count"));
				memberDTO = new MemberDTO();
				memberDTO.setAccount(rs.getString("account"));
				memberDTO.setPassword(rs.getString("password"));
				memberDTO.setNickname(rs.getString("nickname"));
				memberDTO.setZipcode(rs.getString("zipcode"));
				memberDTO.setAddress1(rs.getString("address1"));
				memberDTO.setAddress2(rs.getString("address2"));
				memberDTO.setEmail(rs.getString("email"));
				memberDTO.setTel(rs.getString("tel"));
				
				
				dto.setMemberDTO(memberDTO);
				list.add(dto);
			}
		} finally { 
			close();
		}
			
		return list;
	}
	
	public List<BoardDTO> select(String type, PagingDO paging) throws Exception {  
		return select(type, paging.getRowStart(), paging.getRowEnd(), 
						paging.getSearchKey(), paging.getSearchVal());
	}

	public List<BoardDTO> selectAll() throws Exception {
		List<BoardDTO> list = new ArrayList<>();
		BoardDTO dto = null;
		
		
		try {
			connect();
			sql = "select * from " + table;
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()){
				dto = new BoardDTO();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setType(rs.getInt("type"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriteDate(rs.getDate("write_date"));
				dto.setReadCount(rs.getInt("read_count"));
				list.add(dto);
			}				
		} finally { 
			close();
		}
		return list;
	}
	
	public void insert(String type, BoardDTO dto) throws Exception {
		
		try {
			connect();
			sql = "insert into " + table + " values (" + sequence + ", ?, ?, ?, ?, sysdate, 0)";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getUserId());
			pst.setInt(2, dto.getType());
			pst.setString(3,  dto.getTitle());
			pst.setString(4,  dto.getContent());
			System.out.println(pst);
			pst.executeUpdate();
			
		} finally { 
			close();
		}
	}
	
	public void insertByNextVal(String type, BoardDTO dto, int nextval) throws Exception {
		
		try {
			connect();
			sql = "insert into " + table + " values (" + nextval + ", ?, ?, ?, ?, sysdate, 0)";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getUserId());
			pst.setInt(2, dto.getType());
			pst.setString(3,  dto.getTitle());
			pst.setString(4,  dto.getContent());
			System.out.println(pst);
			pst.executeUpdate();
			
		} finally { 
			close();
		}
	}
	
	public void update(String type, BoardDTO dto) throws Exception {
		try {
			System.out.printf("type %s, id %d", type, dto.getId());
			connect();
			sql = "update " + table + " set user_id=?, type=?, title=?, content=? where id=?"; 
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getUserId());
			pst.setInt(2, dto.getType());
			pst.setString(3,  dto.getTitle());
			pst.setString(4,  dto.getContent());
			pst.setInt(5,  dto.getId());
			System.out.println(pst);
			pst.executeUpdate();		
		} finally { 
			close();
		}
	}

	public void increaseReadCount(String type, String boardId) throws Exception {
		try {
			System.out.printf("increaseReadCount: type %s, id %s\n", type, boardId);
			connect();
			sql = "update " + table + " set read_count=read_count+1 where id=? and type=" + BoardDTO.getBoardType(type); 
			pst = cn.prepareStatement(sql);
			pst.setString(1, boardId);
			System.out.println(pst);
			pst.executeUpdate();		
		} finally { 
			close();
		}
	}
	
	public void delete(String boardType, String id) throws Exception {
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
	
	public BoardDTO selectLast() throws Exception {
		BoardDTO dto = null;
		StringBuffer sql = new StringBuffer();
		
		/*
		select * from (
			select rownum rn, b.* from jkhotel_board b order by id desc
		) where  rn = 1;
		*/
		try {
			connect();
			sql.append("select * from (");
				sql.append("select rownum rn, b.* from " + table + " b order by id desc");
			sql.append(") where rn = 1");	
			System.out.println(sql);
			pst = cn.prepareStatement(sql.toString());
			rs = pst.executeQuery();
			if (rs.next()) {
				dto = new BoardDTO();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setType(rs.getInt("type"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriteDate(rs.getDate("write_date"));
				dto.setReadCount(rs.getInt("read_count"));
			}
		} finally { 
			close();
		}
		return dto;
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


