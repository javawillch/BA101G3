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
 * Servlet 產生圖片驗證碼
 */
public class IdentityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N' , 'P', 
			'Q', 'R' , 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	 											//不使用0 O 1 I 等難辨識字元
	
	public static Random random = new Random();
	
	public static String getRandomString(){
		StringBuffer buffer = new StringBuffer();
		for(int i=0 ; i<6 ; i++){
			buffer.append(CHARS[random.nextInt(CHARS.length)]);  //每次隨機取一個字元
		}
		return buffer.toString();
	}
	
	public static Color getRandomColor(){
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}
	
	public static Color getReverseColor(Color c){ //得到反色
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}

    public IdentityServlet() {
        super();
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		res.setContentType("image/jpeg");
		
		String randomString = getRandomString();
		req.getSession().setAttribute("randomString", randomString);                    //放進Session中
		
		int width = 100;
		int height = 30;
	
		Color color = getRandomColor();
		Color reverse = getReverseColor(color);
		
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //建立一個彩色圖片
		Graphics2D g = bi.createGraphics();
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));                             //設定字體
		g.setColor(color);
		g.fillRect(0, 0, width, height);        //之後看是不是可以改用fill3DRect
		g.setColor(reverse);
		g.drawString(randomString, 18, 20);
		for(int i=0 , n= random.nextInt(100); i < n ; i++){
			g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);             //畫最多100個噪音點
		}
		
		ServletOutputStream out = res.getOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);                      //轉成JPEG格式
		encoder.encode(bi);
		out.flush();
		
		
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
