package utility;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



public class DBPicReader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;    

    public DBPicReader() {
        super();
    }

   
    
	private void nopic(ServletOutputStream out) throws IOException{
		
		InputStream in = getServletContext().getResourceAsStream("/pic/nopic.png");
		byte[] buf = new byte[in.available()];
		in.read(buf);
		out.write(buf);
		in.close();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		
		res.setContentType("image/gif");
		req.setCharacterEncoding("big5"); //先
		ServletOutputStream out = res.getOutputStream();
		
		String action = req.getParameter("action");      //自己加的
		if(action!=null){
			try {
				String id_no = req.getParameter("id_no"); //後
				String pk = new String (id_no.getBytes("ISO-8859-1"), "big5");//再
				
				String sql;
				switch(action.toLowerCase()){
					case "product":
						sql = "SELECT pro_photo FROM product WHERE pro_no ='" + pk + "'";
						break;
					//以下未用到
					case "spndcoffee":
						sql = "SELECT spnd_img FROM spndcoffee WHERE spnd_id = '" + pk + "'";
						break;
					case "news":
						sql = "SELECT news_img FROM news WHERE news_id = '" + pk + "'";
						break;
					case "advertisment":
						sql = "SELECT ad_img FROM advertisment WHERE ad_id = '" + pk + "'";
						break;
					case "activity":
						sql = "SELECT activ_img FROM activity WHERE activ_id = '" + pk + "'";
						break;
					case "photo_store":
						sql = "SELECT photo FROM photo_store WHERE photo_id = '" + pk + "'";
						break;
					case "admin":
						sql = "SELECT admin_img FROM admin WHERE admin_id = '" + pk + "'";
						break;
					default:
						sql = "";
						break;
				}
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				if (rs.next()) {
					BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream(1));
					byte[] buf = new byte[4 * 1024]; // 4K buffer
					int len;
					while ((len = in.read(buf)) != -1) {
						out.write(buf, 0, len);
					}
					in.close();
				} else {
					nopic(out);
	//				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
				rs.close();
				stmt.close();
			} catch (Exception e) {
				nopic(out);
			}			
		
		}

	}

		public void init() throws ServletException {
			
			try {
				Context ctx = new javax.naming.InitialContext();
				DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
				con = ds.getConnection();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

		public void destroy() {
			try {
				if (con != null) con.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}

}
