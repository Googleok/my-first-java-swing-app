package 채팅프로그램final;


import java.awt.*;
import java.awt.image.BufferedImage;

public class MyCanvas extends Canvas{

	private BufferedImage img;
	
	public MyCanvas() {
		this.img = null;
		//가로 크기만 확보
		setPreferredSize(new Dimension(300,0));
	}
	
	public MyCanvas(BufferedImage img) {
		this.img = img;
		// Dimension의 객체를 받아서 컴포넌트의 크기를 지정할 수 있다.
		setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
	public void setImage(BufferedImage img) {
		this.img = img;
		setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
		setSize(img.getWidth(), img.getHeight());
	}
	
	public void paint(Graphics g) {
		if(img != null) g.drawImage(img, 0, 0, null);
	}
	

}
