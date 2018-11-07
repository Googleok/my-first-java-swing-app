package ä�����α׷�final;


import java.awt.*;
import java.awt.image.BufferedImage;

public class MyCanvas extends Canvas{

	private BufferedImage img;
	
	public MyCanvas() {
		this.img = null;
		//���� ũ�⸸ Ȯ��
		setPreferredSize(new Dimension(300,0));
	}
	
	public MyCanvas(BufferedImage img) {
		this.img = img;
		// Dimension�� ��ü�� �޾Ƽ� ������Ʈ�� ũ�⸦ ������ �� �ִ�.
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
