package com.live;

import java.awt.Container;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;

import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;



public class MainClass extends JFrame implements WindowListener,ActionListener,ItemListener {
	public   String appName="RTMP流直播应用";
	
	//界面大小
	int width=600;
	int height=400;
	TextArea textStates=new TextArea();
	JButton btnBegin=new JButton("RTMP 启动");
	JButton btnQuit=new JButton("退出程序");

	public Boolean isRunning=false;
	FFMPEGThread ffmpegThread=null;
	String[] arrayStr=null;
	static MainClass main;
	public MainClass(){
		try {  
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//当前系统风格     
		} catch (Exception e) {  
			e.printStackTrace();  
		}
		ConfigClass.ini();
		this.setTitle(ConfigClass.getAppName());
		this.setSize(width, height);
		int windowswidth=Toolkit.getDefaultToolkit().getScreenSize().width;   
	    int windowsheight=Toolkit.getDefaultToolkit().getScreenSize().height;  

	    this.setLocation((windowswidth-width)/2,(windowsheight-height)/2); 
	    this.addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent e){
        		stopThread();
        		System.exit(0);
        	}
        });
	    setBordLayOut();
	    this.setVisible(true);      
	}
	public void setBordLayOut(){
		Container container = getContentPane();
		container.setLayout(null);

		container.add(textStates);
		textStates.setBounds(160, 5, 400, 260);
		
		btnBegin.setActionCommand("btnBegin");
		btnBegin.addActionListener(this);
        container.add(btnBegin);
        btnBegin.setBounds(10, 110, 120, 40);
        
        btnQuit.setActionCommand("btnQuit");
        btnQuit.addActionListener(this);
        container.add(btnQuit);
        btnQuit.setBounds(10, 210, 120, 40);
        
      
	}
	public void showStates(String msg){
		arrayStr=textStates.getText().split("\\n");
		if(arrayStr!=null){
			if(arrayStr.length>18){
				textStates.setText("");
				for(int i=1;i<arrayStr.length;i++){
					textStates.append(arrayStr[i]+"\n");
				}
			}
		}
		textStates.append(msg+"\n");
		arrayStr=null;
		msg=null;
	}
	public void windowActivated(WindowEvent arg0) {
		// TODO 自动生成方法存根

	}

	public void windowClosed(WindowEvent arg0) {
		// TODO 自动生成方法存根

	}

	public void windowClosing(WindowEvent arg0) {
		// TODO 自动生成方法存根

	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO 自动生成方法存根

	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO 自动生成方法存根

	}

	public void windowIconified(WindowEvent arg0) {
		// TODO 自动生成方法存根

	}

	public void windowOpened(WindowEvent arg0) {
		// TODO 自动生成方法存根

	}
	
	public void stopThread(){
		try{
			if(ffmpegThread!=null){
				ffmpegThread.stopThread();
				//ffmpegThread.wait(500);
			}
			ffmpegThread=null;
		}catch(Exception e){
			e=null;
		}
		
	}
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成方法存根
		String cmd=e.getActionCommand();
		//
		
		if(cmd.equals("btnBegin"))
		{
			//System.out.println("isRunning="+isRunning);
			try{
				if(btnBegin.getText().indexOf("启动")>=0){
					ConfigClass.ini();
					ffmpegThread=new FFMPEGThread(this,"ffmpegThread");
					ffmpegThread.start();
					btnBegin.setText("RTMP 停止");
				}else{
					//ffmpegThread.stopFFMPEGEnCode();
					stopThread();
					btnBegin.setText("RTMP 启动");
					
				}
			}catch(Exception er){
				er=null;
			}
		}
			
		
		if(cmd.equals("btnQuit"))
		{
			stopThread();
			System.exit(0);
		}
		cmd=null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		main=new MainClass();
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

}
