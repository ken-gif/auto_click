package auto_click;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
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
	private JList<String> lisLog;
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
	                	Point p;
	                	HWND hWnd;
	                	int midXWin,midYWin, cardButX,cardButY;
	                	int[] rect = {0, 0, 0, 0};
	                	Boolean startRun=true;
	                	for(;;) {
	                		if (startRun==false) {
	                			Thread.sleep(6000);
	                		}
	                		if (doing==false) {
	                			Thread.sleep(1000);
	                			startRun=true;
	                			continue;
	                		}
	                		Robot robot = new Robot();
	                		lisModel.add(0, java.time.LocalTime.now()+ " setFore 有道单词本 and keydown...");
	                		
	                	    hWnd = dicProcess.FindWindow(null, "有道单词本"); 
	            	        dicProcess.ShowWindow(hWnd, User32.SW_SHOW);  
	            	        dicProcess.SetForegroundWindow(hWnd);  
	            	        p=MouseInfo.getPointerInfo().getLocation();
	            	        System.out.println(p.x);
	            	        System.out.println(p.y);
	            	        System.out.println(startRun);
							User32.instance.GetWindowRect(hWnd, rect);
							if (startRun==true) {
								cardButX=(int) (rect[2]*0.98);
								cardButY=(int) (rect[1]*1.21);
 								robot.mouseMove(cardButX, cardButY);
 								robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
 								robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
 								startRun=false;
							}
							
							midXWin=(rect[2]+rect[0])/2;
  							midYWin=(rect[3]+rect[1])/2;
							robot.mouseMove(midXWin, midYWin);
								
							robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
							robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
							robot.mouseMove(p.x, p.y);
							robot.keyPress(KeyEvent.VK_RIGHT);
  							robot.keyPress(KeyEvent.VK_SPACE);
							System.out.println("0:"+rect[0]+",1:"+rect[1]+",2:"+rect[2]+",3:"+rect[3]);
	            	        
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
		lisLog = new JList<String>(lisModel);
		contentPane.add(lisLog, BorderLayout.CENTER);
		
	}
	public interface User32 extends W32APIOptions {
	    User32 instance = (User32) Native.loadLibrary("user32", User32.class,
	            DEFAULT_OPTIONS);
	
	    boolean ShowWindow(HWND hWnd, int nCmdShow);
	    boolean SetForegroundWindow(HWND hWnd);
	
	    HWND FindWindow(String winClass, String title);
	    int SW_SHOW = 1;
	    int GetWindowRect(HWND handle, int[] rect);
	}


}

