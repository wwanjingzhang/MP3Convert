package com.live;

import java.util.ArrayList;

public class ConfigClass {
	private static String appName="";
	private static String mainUrl="";
	private static  String serverUrl="";
	//public static long offsetTime=0;//录音时间延时，单位：s
	
	private static String logErrorPath="";
	private static int living=0;
	private static String liveParameter="";//live ffmpeg parameter
	private static String loopParameter="";//loop ffmpeg parameter
	private static String initFileName = System.getProperty("user.dir") + "\\configLive.ini";
	public  static void createPath(String filePath){
		FileFuntion fileFunction=new FileFuntion();
		String[] arrayStr=filePath.split("\\\\");
		if(arrayStr.length>0){
			String newPath=arrayStr[0];
			String tmp="";
			for(int i=1;i<arrayStr.length;i++){
				if(arrayStr[i].length()>4){
					tmp=arrayStr[i].substring(arrayStr[i].length()-4);
					if(i==(arrayStr.length-1)){
						if((!".txt".equals(tmp))&&(!".flv".equals(tmp))&&(!".mp4".equals(tmp))&&(!".mp3".equals(tmp))){
							newPath=newPath+"\\"+arrayStr[i];
							fileFunction.newFolder(newPath);
						}
					}
				}else{
					newPath=newPath+"\\"+arrayStr[i];
					fileFunction.newFolder(newPath);
				}
			}
			newPath=null;
			tmp=null;
		}
		 arrayStr=null;
		fileFunction=null;
	}
	public static void saveLogError(String content){
		FileFuntion filefunction=new FileFuntion();
		filefunction.errorLog(logErrorPath, content, true);
		filefunction=null;
	}
	
	public static  void ini(){
		ArrayList<String> list = null;
		FileFuntion fileFunction=new FileFuntion();
		list=fileFunction.getRecTimeList(initFileName);
		String line="";
		for(int i=0;i<list.size();i++){
			line=list.get(i);
			if(line.indexOf("channle=")==0){
				appName=line.substring(8, line.length()).trim();
			}
			if(line.indexOf("mainUrl=")==0){
				mainUrl=line.substring(8, line.length()).trim();
				//System.out.println("class.offsetTime->"+offsetTime);
			}
			if(line.indexOf("serverUrl=")==0){
				serverUrl=line.substring(10, line.length()).trim();
				//System.out.println("class.offsetTime->"+offsetTime);
			}
			
			if(line.indexOf("logErrorPath=")==0){
				logErrorPath=System.getProperty("user.dir") + line.substring(13, line.length()).trim();
			}
			
			if(line.indexOf("liveParameter=")==0){
				liveParameter=line.substring(14, line.length()).trim();
			}	
		}
		line=null;
		
		createPath(logErrorPath);
		fileFunction=null;
		line=null;
		list = null;
		
	}
	public static String getMainUrl() {
		return mainUrl;
	}
	public static void setMainUrl(String mainUrl) {
		ConfigClass.mainUrl = mainUrl;
	}
	public static String getServerUrl() {
		return serverUrl;
	}
	public static String getAppName() {
		return appName;
	}
	public static void setAppName(String appName) {
		ConfigClass.appName = appName;
	}
	public static void setServerUrl(String serverUrl) {
		ConfigClass.serverUrl = serverUrl;
	}
	
	
	public static String getLogErrorPath() {
		return logErrorPath;
	}
	public static void setLogErrorPath(String logErrorPath) {
		ConfigClass.logErrorPath = logErrorPath;
	}
	public static int getLiving() {
		return living;
	}
	public static void setLiving(int living) {
		ConfigClass.living = living;
	}
	public static String getLiveParameter() {
		return liveParameter;
	}
	public static void setLiveParameter(String liveParameter) {
		ConfigClass.liveParameter = liveParameter;
	}
	public static String getLoopParameter() {
		return loopParameter;
	}
	public static void setLoopParameter(String loopParameter) {
		ConfigClass.loopParameter = loopParameter;
	}
	public static String getInitFileName() {
		return initFileName;
	}
	public static void setInitFileName(String initFileName) {
		ConfigClass.initFileName = initFileName;
	}
	
	
}
