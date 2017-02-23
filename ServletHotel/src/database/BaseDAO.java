package database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BaseDAO {
	protected Connection cn;
	protected Statement st; 
	protected PreparedStatement pst; 
	protected ResultSet rs;
	protected String sql;
	static boolean useDBCP = false;
	protected boolean autoCommit = true;
	
	public boolean isAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	public static boolean isUseDBCP() {
		return useDBCP;
	}

	public static void setUseDBCP(boolean useDBCP) {
		BaseDAO.useDBCP = useDBCP;
	}

	public BaseDAO() {

	}
	
	public void connect() {
		try {
			if (isUseDBCP()) {
				if (isAutoCommit() || (! isAutoCommit() && cn == null))
					cn = DriverManager.getConnection("jdbc:apache:commons:dbcp:shotel");
				
				cn.setAutoCommit(autoCommit);
			}
			else {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
				
				if (! isAutoCommit() || (isAutoCommit() && cn == null))
					cn = DriverManager.getConnection(url, "system", "oracle");
				
				cn.setAutoCommit(autoCommit);

			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
	
	public void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pst != null) { 
				pst.close();
				pst = null;
			}
			
			if (! isAutoCommit())
				return;
			
			if (cn != null) {
				cn.close();
				cn = null;
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void commit() {
		if (cn != null) {
			try {
				cn.commit();
				cn.close();
				cn = null;
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	public void rollback() {
		if (cn != null) {
			try {
				cn.rollback();
				cn.close();
				cn = null;
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public String toChar(String cname) {   
		return " to_char (" + cname + ",  'yyyy/mm/dd hh24:mi:ss') as " + cname + " ";
	}
	
	public String toChar(String cname, String alias) {   
		return " to_char (" + cname + ",  'yyyy/mm/dd hh24:mi:ss') as " + alias + " ";
	}
	
	public Date toDate(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		java.util.Date tmp= sdf.parse(str);
		return new Date(tmp.getTime());
	}
	
}

