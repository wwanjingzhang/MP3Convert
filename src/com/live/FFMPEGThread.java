package com.live;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FFMPEGThread extends Thread {
	public Boolean isStopThread = false;
	//private Boolean isLive = false; // �Ƿ�����ֱ����
	//public int isLiving = 0;// ��ǰ�Ƿ�����ֱ�������У��ֲ���Ϊ0;
	private Boolean isLoopRunning = false; // �ֲ��Ƿ�����������
	//private Boolean isLoop=false;
	// private Boolean isBeginLive=false;
	MainClass mainClass;
	// public Boolean
	Process pFFMPEG = null;
	private ArrayList<String> listfile = new ArrayList<String>();


	// public ArrayList<String> liststates;
	// private String mediaPath = System.getProperty("user.dir") + "\\mp4";
	BufferedReader br = null;

	// StatusClass statueclass;
	public FFMPEGThread() {
		// TODO Auto-generated constructor stub
	}

	public FFMPEGThread(MainClass mainClass, String name) {
		super(name);
		this.mainClass = mainClass;
	}

	public Boolean getIsLoopRunning() {
		return isLoopRunning;
	}

	//public Boolean getIsLoop() {
		//return isLoop;
	//}

	//public void setIsLoop(Boolean isLoop) {
		//this.isLoop = isLoop;
	//}

	public void setIsLoopRunning(Boolean isLoopRunning) {
		this.isLoopRunning = isLoopRunning;
	}

	//public Boolean getIsLive() {
		//return isLive;
	//}

	public FFMPEGThread(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
	}

	public FFMPEGThread(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void setIsStopThread(Boolean isStopThread) {
		this.isStopThread = isStopThread;
		// System.out.println(this.isRunning);
	}

	public FFMPEGThread(ThreadGroup group, Runnable target) {
		super(group, target);
		// TODO Auto-generated constructor stub
	}

	public FFMPEGThread(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}

	public FFMPEGThread(Runnable target, String name) {
		super(target, name);
		// TODO Auto-generated constructor stub
	}

	public FFMPEGThread(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}

	public FFMPEGThread(ThreadGroup group, Runnable target, String name,
			long stackSize) {
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}

	public void destroyPress() {
		try{
			if (pFFMPEG != null) {
				pFFMPEG.getErrorStream().close();
				pFFMPEG.getInputStream().close();
				pFFMPEG.getOutputStream().close();
				pFFMPEG.destroy();
				pFFMPEG = null;
				killFFMPEG();
				mainClass.showStates(FileFuntion.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss")+"  �رձ������������");
				//System.out.println("destroyPress end ");
				
				
				
				
			}
			
		}catch(IOException e){
			System.out.println("destroyPress->e: "+e);
			//e=null;
		}
	}
	public void stopFFMPEGEnCode(){
		try{
			if (pFFMPEG != null) {
				//isStopThread=true;
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(pFFMPEG.getOutputStream()));
				bw.write("q");
				bw.flush();
				bw.close();
				//mainClass.showStates(FileFuntion.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss")+"  ���͹ر�ָ�q");
				//System.out.println("stopFFMPEGEnCode->to 'q' ");
				//stopThread();
				
				//pFFMPEG.getOutputStream().close();
			}
			//System.out.println("stopFFMPEGEnCode end ");
		}catch(IOException e){
			System.out.println("stopFFMPEGEnCode->e: "+e);
			//e=null;
		}
	}
	

	public void stopThread() {
		isStopThread = true;
		destroyPress();
	}

	public void ffmpegLive() {
		mainClass.showStates("ת������Դ��" + ConfigClass.getMainUrl());
		//String filename="d:\\mp3\\"+FileFuntion.dateToString(new Date(),"yyyyMMddHHmmss")+".mp3";
		ffmpegLive(ConfigClass.getMainUrl(), ConfigClass.getServerUrl(), "ffmpeg.exe");
		//ffmpegLive(ConfigClass.getMainUrl(), filename, "ffmpeg.exe");
	}
	public void run() {
		while (!isStopThread) {
			try {
				//System.out.println(FileFuntion.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss")+"  Thread run...");
				killFFMPEG();
				ffmpegLive();
				
				sleep(5000);
			} catch (Exception e) {
				//ConfigClass.saveLogError("FFMPEGThread->run->e: "+e.toString());
				e=null;
			}
		}
		destroyPress();
		//System.out.println("end run");
	}
	private void ffmpegLive(String rtmp_oldPath, String rtmp_newPath,
			String ffmpeg_path) {
		List<String> commands = new java.util.ArrayList<String>();
		String[] liveParameter=ConfigClass.getLiveParameter().split(" ");
		commands.add(ffmpeg_path);
		//commands.add("-loglevel");
		//commands.add("quiet");
		commands.add("-i");
		commands.add(rtmp_oldPath);
		if(liveParameter.length>1){
			for(int i=0;i<liveParameter.length;i++){
				commands.add(liveParameter[i]);
			}
		}else{
			commands.add("-acodec");
			commands.add("aac");
			commands.add("-ar");
			commands.add("44100");
			commands.add("-ab");
			commands.add("64k");
			commands.add("-c:v");
			commands.add("libx264");
			commands.add("-f");
			commands.add("25");
			commands.add("-preset");
			commands.add("fast");
			commands.add("-f");
			commands.add("flv");
		}
		commands.add(rtmp_newPath);	
		liveParameter=null;
		try {
			ProcessBuilder builder = new ProcessBuilder();
			//System.out.println(commands);
			builder.command(commands);
			commands = null;
			pFFMPEG = builder.start();
			doWaitPro(pFFMPEG);
			destroyPress();
			builder=null;
		} catch (Exception e) {
			ConfigClass.saveLogError("FFMPEGThread->ffmpeg->e: "+e.toString());
			e=null;
		}
	}
	// �ȴ��̴߳������
	
	public void doWaitPro(Process p) {
		try {
			String errorMsg = readInputStreamErr(p.getErrorStream(), "error");
			//readInputStream(p.getInputStream());
			String outputMsg = readInputStreamErr(p.getInputStream(), "out");
			//System.out.println("doWaitPro->errorMsg:" + errorMsg);
			//System.out.println("doWaitPro->outputMsg:" + outputMsg);
			int c = p.waitFor();
			if (c != 0) {// �����������ڵȴ�
				 System.out.println("����ʧ�ܣ�errorMsg=" + errorMsg);
			} else {
				 //System.out.println("�������:outputMsg="+outputMsg);
			}
		} catch (IOException e) {
			// e.printStackTrace();
		} catch (InterruptedException e) {
		}
	}
	/**
	 * 
	 * @Title: readInputStream
	 * @Description: ��ɽ��Ȱٷֱ�
	 * @param
	 * @return String
	 * @throws
	 */
	
	private String readInputStreamErr(InputStream is, String f) throws IOException {
		// �����̵��������װ�ɻ�����߶���
		//System.out.println("readInputStreamErr run..");
		long t=Long.parseLong(FileFuntion.dateToString(tomorrow(new Date()),"yyyyMMdd")+"000010");//��ȡ����ʱ���賿
		long _t=0;
		System.out.println("t= "+t);
		if (br != null)
			br.close();
		br = null;
		br = new BufferedReader(new InputStreamReader(is),2048);
		int showflag = 0;
		int positionframe,positionclienterror,positionServerError,positionServerError2,positionServerError3,positionServerError4 = -1;
		
		// �Ի�����߶������ÿ��ѭ��
		String line=null;
		while((line = br.readLine()) != null){
			positionframe = line.indexOf("frame=");
			if (positionframe >= 0) {// �����ǰ������"frame="
				
				if(showflag==0){
					mainClass.showStates(FileFuntion.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss")+"  ->������ʼ������");
				}
				if (showflag > 20) {
					//mainClass.showStates(line);
					_t=longTime();
					if(_t>t){
						
						System.out.println("_t= "+_t);
						stopFFMPEGEnCode();
						break;
					}
					_t=0;
					showflag = 1;
					
				}
				showflag++;
			}else{
				positionclienterror = line.indexOf("Unknown error");
				positionServerError = line.indexOf("server sent error");
				positionServerError2 = line.indexOf("failed to read");
				positionServerError3 = line.indexOf("error status");
				positionServerError4 = line.indexOf("unexpected packet");
				if (positionclienterror >= 0) {// �����ǰ������"Unknown error"
					mainClass.showStates("error=ֱ��Դ���ڴ���");
					break;
				}
				if (positionServerError >= 0) {// �����ǰ������"server sent error"
					mainClass.showStates("error=����˴��ڴ���");
					break;
				}
				if (positionServerError2 >= 0) {// �����ǰ������"server sent error"
					mainClass.showStates("error=ֱ��Դ������");
					break;
				}
				if (positionServerError3 >= 0) {// �����ǰ������"server sent error"
					mainClass.showStates("error=��ȡ����");
					break;
				}
				if (positionServerError4 >= 0) {// �����ǰ������"server sent error"
					mainClass.showStates("error=unexpected packet");
					break;
				}
			}
			line=null;
		}//end while
		br.close();// �رս��̵������
		br=null;
		if(showflag>0){
			mainClass.showStates(FileFuntion.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss")+"  input err  ������");
		}
		
		return f;// lines.toString();
	}
	public void killFFMPEG() {
		try {
			String[] cmd = { "tasklist" };
			Process proc = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			String string_Temp = in.readLine();
			while (string_Temp != null) {
				if (string_Temp.indexOf("ffmpeg.exe") != -1) {
					Runtime.getRuntime().exec("taskkill /im ffmpeg.exe /f");
				}
				// Runtime.getRuntime().exec("ntsd -c q -pn ffmpeg.exe");
				string_Temp = in.readLine();
			}
			string_Temp = null;
			cmd = null;
			proc.notify();
			proc = null;
			in.close();
			in = null;
		} catch (Exception e) {
		}
	}
	public long longTime(){
		Date date=new Date();
		SimpleDateFormat s=new SimpleDateFormat("yyyyMMddHHmmss");
		String _time=s.format(date);
		date=null;
		s=null;
		return Long.parseLong(_time);
	}
	public Date tomorrow(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
    }

	
		
}
