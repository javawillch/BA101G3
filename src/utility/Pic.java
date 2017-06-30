package utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

public class Pic {

	// �ϥ�byte[]�覡
	public static byte[] getPictureByteArray(Part part) throws IOException {

		InputStream in = part.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = in.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		in.close();

		return baos.toByteArray();
	}
		
	// ���X�W�Ǫ��ɮצW�� (�]��API������method,�ҥH�����ۦ漶�g)
	private static String getFileNameFromPart(Part part) {

		String header = part.getHeader("content-disposition");
		//System.out.println("header=" + header); // ���ե�
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		//System.out.println("filename=" + filename); // ���ե�
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
	
	public static boolean noFileSelected(Part part){
		
		if (getFileNameFromPart(part) == null || part.getContentType() == null) {
			return true;
		}
		return false;
	}
	
}
