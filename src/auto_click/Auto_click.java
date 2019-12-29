package auto_click;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Robot;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class Auto_click extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private boolean doing=false;
	private Thread t1=null;

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

	/**
	 * Create the frame.
	 */
	public Auto_click() {
		if (t1==null) {
			t1=new Thread(new Runnable() {
	            public void run() {
	                try {
	                	for(;;) {
	                		Thread.sleep(5000);
	                		if (!doing) {
	                			continue;
	                		}
	                		
	                		Robot robot = new Robot();
							robot.keyPress(KeyEvent.VK_RIGHT);
							robot.delay(100);
							Thread.sleep(1000);
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
		setBounds(100, 100, 450, 136);
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
					}else {
						doing=true;
						btnStart.setText("Doing...");
					}	
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnStart, BorderLayout.SOUTH);
		
	}

}

