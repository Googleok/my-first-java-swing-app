package 채팅프로그램final;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MultiClient implements ActionListener {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private JFrame jframe;
    private JTextField jtf;
    private JTextArea jta;
    private JTextArea jta2;
    private JLabel jlb1, jlb2;
    private JPanel jp1, jp2, jp3;
    private String ip;
    private String id;
    private JButton jbtn1;
    private JButton jbtn2;
    private JButton jbtn3;
    MyCanvas myCanvas = new MyCanvas();
    
    // [ 추가 ] 이미지 송수신용
 	DataInputStream imgReciever = null;
 	DataOutputStream imgSender = null;
 	Socket othersImg = null;

 	// th2는 저 아래에서 상대방과 연결된 후에 start() 시킴

 	Thread th2 = new Thread() {

 		// 이미지 수신
 		// Jframe을 상속받고 있어서 Runnable 을 implements 함
 		public void run() {

 			while (!othersImg.isClosed()) {
 				try {
 					BufferedImage img = ImageIO.read(imgReciever);
 					if (img != null) {
 						myCanvas.setImage(img);
 						myCanvas.repaint();
 					}
 					yield();
 				} catch (IOException e) {
 					e.printStackTrace();
 				}

 			}
 			System.out.println("SERVER : 이미지 수신 쓰레드 종료");
 		}
 	};

    public MultiClient(String argIp, String argId) {
    	JFrame.setDefaultLookAndFeelDecorated(true);
    	ip = argIp;
        id = argId;
        jframe = new JFrame("HALLOTALK");
        jtf = new JTextField(30);
        jta = new JTextArea("", 35, 30);
        jta2 = new JTextArea("", 10,5);
        jta2.add(myCanvas);
        jlb1 = new JLabel("Usage ID : [[ " + id + "]]");
        jlb2 = new JLabel("IP : " + ip);
        jbtn1 = new JButton("종료");
        jbtn2 = new JButton("이미지");
        jbtn3 = new JButton("보내기");
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        
        jlb1.setBackground(Color.yellow);
        jlb2.setBackground(Color.green);
        jta.setBackground(Color.black);
        jta.setForeground(Color.WHITE);
        jta.setFont(jta.getFont().deriveFont(12f));
        jta2.setBackground(Color.black);
        jp1.setLayout(new BorderLayout());
        jp2.setLayout(new BorderLayout());
        jp3.setLayout(new BorderLayout());
        
        jp3.add(jbtn2, BorderLayout.WEST);
        jp3.add(jbtn3, BorderLayout.EAST);
        
        jp1.add(jbtn1, BorderLayout.EAST);
        jp1.add(jp3, BorderLayout.WEST);
        jp1.add(jtf, BorderLayout.CENTER);
        jp2.add(jlb1, BorderLayout.CENTER);
        jp2.add(jlb2, BorderLayout.EAST);

        jframe.add(jp1, BorderLayout.SOUTH);
        jframe.add(jp2, BorderLayout.NORTH);
        JScrollPane jsp = new JScrollPane(jta,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jframe.add(jsp, BorderLayout.CENTER);
        jframe.add(jta2,BorderLayout.NORTH);
        jtf.addActionListener(this);
        jbtn1.addActionListener(this);
        jbtn2.addActionListener(this);
        jbtn3.addActionListener(this);
        
        jframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    oos.writeObject(id+"#exit");
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
                System.exit(0);
            }
            public void windowOpened(WindowEvent e) {
                jtf.requestFocus();
            }
        });
        jta.setEditable(false);
        jta2.setEditable(false);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        jframe.pack();
        jframe.setLocation(
                (screenWidth - jframe.getWidth()) / 2,
                (screenHeight - jframe.getHeight()) / 2);
        jframe.setResizable(false);
        jframe.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        String msg = jtf.getText();
        if (obj == jtf) {
            if (msg == null || msg.length()==0) {
                JOptionPane.showMessageDialog(jframe, 
                        "글을쓰세요", "경고",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    oos.writeObject(id+"#"+msg);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
                jtf.setText("");
            }
        } else if (obj == jbtn1) {
            try {
                oos.writeObject(id+"#exit");
            } catch (IOException ee) {
                ee.printStackTrace();
            }
            System.exit(0);
        } else if (obj == jbtn2) {
			FileDialog openDlg = new FileDialog(jframe, "파일열기", FileDialog.LOAD);
			openDlg.show();
			if (openDlg.getFile() == null)
				return;
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File(openDlg.getDirectory() + openDlg.getFile()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			// 이미지 사이즈 줄이기
			Image resizeImage=img.getScaledInstance(550, 180, Image.SCALE_SMOOTH);
			BufferedImage newImage = new BufferedImage(550, 180, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics g = newImage.getGraphics();
			g.drawImage(resizeImage,0,0,null);
			// 이미지 사이즈 줄이기
			myCanvas.setImage(newImage);
			myCanvas.repaint();
		
		} else if(obj == jbtn3) {
			if (othersImg == null) {
			} else {
				if (myCanvas.getImage() == null) {
				} else {
					try {
						ImageIO.write(myCanvas.getImage(), "png", imgSender);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
    }
    public void exit(){
        System.exit(0);
    }
    public void init() throws IOException {
        // 메시지 송수신
    	socket = new Socket(ip, 5000);
        System.out.println("connected...");
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        MultiClientThread ct = new MultiClientThread(this);
        Thread t = new Thread(ct);
        t.start();
        
//        //이미지 송수신
//        othersImg = new Socket(ip, 1001); // 이미지송수신용
//		imgReciever = new DataInputStream(othersImg.getInputStream());
//		imgSender = new DataOutputStream(othersImg.getOutputStream());
//		th2.start(); // 이미지수신작업을 멀티쓰레드로 가동한다.

    }

    public static void main(String args[]) throws IOException {
    }
    public ObjectInputStream getOis(){
        return ois;
    }
    public JTextArea getJta(){
        return jta;
    }
    public String getId(){
        return id;
    }
}
