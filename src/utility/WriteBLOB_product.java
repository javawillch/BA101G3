package utility;

import java.sql.*;
import java.io.*;

public class WriteBLOB_product {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ba101g3";
	private static final String PASSWORD = "baby";
	private static final String SQL = "UPDATE product SET pro_photo = ? WHERE pro_no = ?";
	
	
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL);
			 
			for(int i = 1 ; i < 9; i++){
				if(i < 10){
					byte[] pic = getPictureByteArray("WebContent/InsertPic/Shopping/PRO0000"+i+"_babycar/PRO0000"+i+"_babycar01.jpg");
					pstmt.setBytes(1, pic);
					pstmt.setString(2, "PRO0000"+i);
					pstmt.executeUpdate();
				}else{
					byte[] pic = getPictureByteArray("WebContent/InsertPic/Shopping/PRO000"+i+"_babycar/PRO000"+i+"_babycar01.jpg");
					pstmt.setBytes(1, pic);
					pstmt.setString(2, "PRO000"+i);
					pstmt.executeUpdate();					
				}
			}
			
			System.out.println("°õ¦æ§¹¦¨Åo");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}


	public static byte[] getPictureByteArray(String path) throws IOException{
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while((i=fis.read(buffer))!=-1){
			baos.write(buffer,0,i);
		}
		baos.close();
		fis.close();
		
		return baos.toByteArray();
	}

}
