package utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Servlet ���͹Ϥ����ҽX
 */
public class IdentityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N' , 'P', 
			'Q', 'R' , 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	 											//���ϥ�0 O 1 I �������Ѧr��
	
	public static Random random = new Random();
	
	public static String getRandomString(){
		StringBuffer buffer = new StringBuffer();
		for(int i=0 ; i<6 ; i++){
			buffer.append(CHARS[random.nextInt(CHARS.length)]);  //�C���H�����@�Ӧr��
		}
		return buffer.toString();
	}
	
	public static Color getRandomColor(){
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}
	
	public static Color getReverseColor(Color c){ //�o��Ϧ�
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}

    public IdentityServlet() {
        super();
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		res.setContentType("image/jpeg");
		
		String randomString = getRandomString();
		req.getSession().setAttribute("randomString", randomString);                    //��iSession��
		
		int width = 100;
		int height = 30;
	
		Color color = getRandomColor();
		Color reverse = getReverseColor(color);
		
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //�إߤ@�ӱm��Ϥ�
		Graphics2D g = bi.createGraphics();
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));                             //�]�w�r��
		g.setColor(color);
		g.fillRect(0, 0, width, height);        //����ݬO���O�i�H���fill3DRect
		g.setColor(reverse);
		g.drawString(randomString, 18, 20);
		for(int i=0 , n= random.nextInt(100); i < n ; i++){
			g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);             //�e�̦h100�Ӿ����I
		}
		
		ServletOutputStream out = res.getOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);                      //�নJPEG�榡
		encoder.encode(bi);
		out.flush();
		
		
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
