package auto_click;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Robot;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.awt.event.ActionEvent;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.W32APIOptions;
import javax.swing.JList;
public class Auto_click extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private boolean doing=false;
	private Thread t1=null;
	private User32 dicProcess=null;
	private DefaultListModel<String> lisModel;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Auto_click frame = new Auto_click();
					frame.setVisible(true);								        
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Auto_click() {
		if (dicProcess==null) {
			dicProcess = User32.instance;  
		}
		if (t1==null) {
			t1=new Thread(new Runnable() {
	            public void run() {
	                try {
	                	for(;;) {
	                		Thread.sleep(4000);
	                		if (!doing) {
	                			continue;
	                		}
	                		lisModel.add(0, java.time.LocalTime.now()+ " setFore 有道单词本...");
	                	    HWND hWnd = dicProcess.FindWindow(null, "有道单词本"); 
	            	        dicProcess.ShowWindow(hWnd, User32.SW_SHOW);  
	            	        dicProcess.SetForegroundWindow(hWnd);  
	            	        lisModel.add(0, java.time.LocalTime.now()+" keydown...");
	                		Robot robot = new Robot();
							robot.keyPress(KeyEvent.VK_RIGHT);
							robot.delay(100);
							Thread.sleep(2000);
							robot.keyPress(KeyEvent.VK_SPACE);
							robot.delay(100);
							System.out.println("Thread");
	                	}
	                } 
	                catch(Exception e) { 
	                    e.printStackTrace(); 
	                } 
	            }
	        });
			t1.start();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 308, 205);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (doing==true) {
						doing=false;
						btnStart.setText("Stop");
						lisModel.add(0, java.time.LocalTime.now()+" Stop...");
					}else {
						doing=true;
						btnStart.setText("Processing");
						lisModel.add(0, java.time.LocalTime.now()+" Processing...");
					}	
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnStart, BorderLayout.SOUTH);
		lisModel = new DefaultListModel<String>();
		JList<String> lisLog = new JList<String>(lisModel);
		contentPane.add(lisLog, BorderLayout.CENTER);
		
	}
	public interface User32 extends W32APIOptions {
	    User32 instance = (User32) Native.loadLibrary("user32", User32.class,
	            DEFAULT_OPTIONS);
	
	    boolean ShowWindow(HWND hWnd, int nCmdShow);
	    boolean SetForegroundWindow(HWND hWnd);
	
	    HWND FindWindow(String winClass, String title);
	    int SW_SHOW = 1;
	}
}

