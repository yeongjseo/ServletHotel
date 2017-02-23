package member;

import java.util.ArrayList;
import java.util.List;

import common.PagingDO;
import database.BaseDAO;

public class MemberDAO extends BaseDAO {
	public static final String table = "jkhotel_user";
	private static final String sequence = "jkhotel_user_seq.nextval";
		
	public MemberDAO() {
		super();
	}

	public int count(int type, String sKey, String sVal) throws Exception {
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
		} finally { 
			close();
		}
		return cnt;
	}
	
	public int count(int type, PagingDO paging) throws Exception  {
		return count(type, paging.getSearchKey(), paging.getSearchVal());
	}
	
	public MemberDTO selectByAccount(String account) throws Exception {
		MemberDTO dto = null; 
		System.out.printf("account %s\n", account);
		connect();
		try {
			sql = "select * from " + table + " where account = ?";
			pst = cn.prepareStatement(sql);
			pst.setString(1, account);
			rs = pst.executeQuery();
			if (rs.next()){
				dto = new MemberDTO();
				dto.setId(rs.getInt("id"));
				dto.setAccount(rs.getString("account"));
				dto.setPassword(rs.getString("password"));
				dto.setNickname(rs.getString("nickname"));
				dto.setBirthday(rs.getDate("birthday"));
				dto.setZipcode(rs.getString("zipcode"));
				dto.setAddress1(rs.getString("address1"));
				dto.setAddress2(rs.getString("address2"));
				dto.setEmail(rs.getString("email"));
				dto.setEmailConfirm(rs.getInt("e_confirm"));
				dto.setTel(rs.getString("tel"));
			}				
		} finally { 
			close();
		}
		return dto;
	}
	
	public MemberDTO select(int id) throws Exception  {
		MemberDTO dto = null; 
		System.out.printf("id %s\n", id);
		connect();
		try {
			sql = "select * from " + table + " where id = ?";
			pst = cn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			System.out.println(sql);
			if (rs.next()) {
				dto = new MemberDTO();
				dto.setId(rs.getInt("id"));
				dto.setAccount(rs.getString("account"));
				dto.setPassword(rs.getString("password"));
				dto.setNickname(rs.getString("nickname"));
				dto.setBirthday(rs.getDate("birthday"));
				dto.setZipcode(rs.getString("zipcode"));
				dto.setAddress1(rs.getString("address1"));
				dto.setAddress2(rs.getString("address2"));
				dto.setEmail(rs.getString("email"));
				dto.setEmailConfirm(rs.getInt("e_confirm"));
				dto.setTel(rs.getString("tel"));
			}
		} finally { 
			close();
		}
		return dto;
	}
	
	
	public List<MemberDTO> select(int rowStart, int rowEnd, String sKey, String sVal) throws Exception {  
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		MemberDTO dto = null;
		StringBuffer sql = new StringBuffer( );
		StringBuffer cond = new StringBuffer();
		if (sKey.length() > 0)
			cond.append(" where "+ sKey +" like '%"+ sVal +"%' ");
		
		
		try {
			connect();
			sql.append("select * from ( ");
			sql.append("select rownum rn, id, account, password, nickname, birthday, zipcode, address1, "
						+ "	address2, email, e_confirm, tel from ");
			sql.append(" (select * from " + table + " " + cond.toString() + ")");
			sql.append(") where rn between " + rowStart + " and " + rowEnd);
			System.out.println(sql.toString());
			st = cn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				dto = new MemberDTO();
				dto.setRowNum(rs.getInt("rn"));
				dto.setId(rs.getInt("id"));
				dto.setAccount(rs.getString("account"));
				dto.setPassword(rs.getString("password"));
				dto.setNickname(rs.getString("nickname"));
				dto.setBirthday(rs.getDate("birthday"));
				dto.setZipcode(rs.getString("zipcode"));
				dto.setAddress1(rs.getString("address1"));
				dto.setAddress2(rs.getString("address2"));
				dto.setEmail(rs.getString("email"));
				dto.setEmailConfirm(rs.getInt("e_confirm"));
				dto.setTel(rs.getString("tel"));
				list.add(dto);
			}
		} finally { 
			close();
		}
			
		return list;
	}
	
	public List<MemberDTO> select(PagingDO paging) throws Exception {  
		return select(paging.getRowStart(), paging.getRowEnd(), 
						paging.getSearchKey(), paging.getSearchVal());
	}
	
	public void insert(MemberDTO dto) throws Exception {
		
		try {
			connect();
			sql = "insert into " + table + " values (" + sequence + ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			System.out.println(sql);
			pst = cn.prepareStatement(sql);
			pst.setString(1, dto.getAccount());
			pst.setString(2, dto.getPassword());
			pst.setString(3, dto.getNickname());
			pst.setDate(4, dto.getBirthday());
			pst.setString(5, dto.getZipcode());
			pst.setString(6, dto.getAddress1());
			pst.setString(7, dto.getAddress2());
			pst.setString(8, dto.getEmail());
			pst.setInt(9, dto.getEmailConfirm());
			pst.setString(10, dto.getTel());
			pst.executeUpdate();
		} finally { 
			close();
		}
	}
	
	public void update(MemberDTO dto) throws Exception {
		try {

			connect();
			
			sql = "update " + table + " set account=?, password=?, nickname=?, birthday=?, zipcode=?," + 
						"address1=?, address2=?, email=?, e_confirm=?, tel=? where id=?";
			System.out.println(sql);
			pst = cn.prepareStatement(sql);
			pst.setString(1, dto.getAccount());
			pst.setString(2, dto.getPassword());
			pst.setString(3, dto.getNickname());
			pst.setDate(4, dto.getBirthday());
			pst.setString(5, dto.getZipcode());
			pst.setString(6, dto.getAddress1());
			pst.setString(7, dto.getAddress2());
			pst.setString(8, dto.getEmail());
			pst.setInt(9, dto.getEmailConfirm());
			pst.setString(10, dto.getTel());
			pst.setInt(11, dto.getId());
			pst.executeUpdate();
			
		} finally { 
			close();
		}
	}
	
	
	public void delete(int id) throws Exception {
		try {
			connect();
			sql = "delete from " + table + " where id = " + id;
			System.out.println(sql.toString());
			st = cn.createStatement();
			st.executeUpdate(sql);
		} finally { 
			close();
		}
	}

}


