package database;

import java.util.ArrayList;
import java.util.List;

import board.BoardDTO;
import common.PagingDO;

/*

*/

public class TemplateDAO extends BaseDAO {
	static final private String table = "XXX";
	static final private String sequence = "XXX.nextval";
		
	public TemplateDAO() {
		super();
	}
	
	public int count (int type, String sKey, String sVal) throws Exception {
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
		} finally { 
			close();
		}
		return cnt;
	}
	
	public int count(int type, PagingDO paging) throws Exception {
		return count(type, paging.getSearchKey(), paging.getSearchVal());
	}
	
	public TemplateDTO select(int id) throws Exception {
		TemplateDTO dto = null; 
		System.out.printf("id %s\n", id);
		
		
		try {
			connect();
			sql = "select * from " + table + " where id = ?";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				dto = new TemplateDTO();
				dto.setId(rs.getInt("id"));
			}				
		} finally { 
			close();
		}
		return dto;
	}
	
	
	public List<TemplateDTO> select(int type, int rowStart, int rowEnd, String sKey, String sVal) throws Exception {  
		List<TemplateDTO> list = new ArrayList<TemplateDTO>();
		TemplateDTO dto = null;
		StringBuffer sql = new StringBuffer( );
		StringBuffer cond = new StringBuffer();
		if (sKey.length() > 0)
			cond.append(" where "+ sKey +" like '%"+ sVal +"%' ");
		
		
		try {
			connect();
			sql.append("select * from ( ");
				sql.append("select rownum rn, id, user_id, type, title, content, write_date, read_count from (");
					sql.append("select * from " + table + " " + cond.toString());
				sql.append(")");
			sql.append(") where rn between " + rowStart + " and " + rowEnd);
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				dto = new TemplateDTO();
				dto.setRowNum(rs.getInt("rn"));
				dto.setId(rs.getInt("id"));
				list.add(dto);
			}
		} finally { 
			close();
		}
			
		return list;
	}
	
	public List<TemplateDTO> select(int type, PagingDO paging) throws Exception {  
		return select(type, paging.getRowStart(), paging.getRowEnd(), 
						paging.getSearchKey(), paging.getSearchVal());
	}

	public List<TemplateDTO> select() throws Exception {
		List<TemplateDTO> list = new ArrayList<>();
		TemplateDTO dto = null;
		
		
		try {
			connect();
			sql = "select * from " + table;
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				dto = new TemplateDTO();
				dto.setId(rs.getInt("id"));
				list.add(dto);
			}				
		} finally { 
			close();
		}
		return list;
	}
	
	public void insert(TemplateDTO dto) throws Exception {
		
		try {
			connect();
			sql = "insert into " + table + " values (" + sequence + ", ?, ?, ?, ?, ?, ?)";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getId());
			pst.executeUpdate();		
			
		} finally {
			close();
		}
	}
	
	public void update(TemplateDTO dto) throws Exception {
		try {
			System.out.printf("id %d", dto.getId());
			connect();
			sql = "update " + table + " set user_id=?, type=?, title=?, content=? where id=?"; 
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getId());
			System.out.println(pst);
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


