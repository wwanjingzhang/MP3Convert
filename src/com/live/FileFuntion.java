package com.live;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class FileFuntion {
	String folderByFile = "";
	public FileFuntion() {
	}

	public void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			if (myDelFile.exists()) {
				myDelFile.getAbsoluteFile().delete();
			}
			myDelFile = null;
		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * @see 新建文件
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String 文件内容
	 * @return boolean
	 */
	public void newFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			if (fileContent.length() > 0) {
				FileWriter resultFile = new FileWriter(myFilePath);
				PrintWriter myFile = new PrintWriter(resultFile);
				String strContent = fileContent;
				myFile.println(strContent);
				myFile.close();
				resultFile.close();
			}
		} catch (Exception e) {
			System.out.println("新建文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * @see 新建目录
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
				//System.out.println("新建目录操作成功");
			}
			myFilePath=null;
		} catch (Exception e) {
			//System.out.println("新建目录操作出错");
			//e.printStackTrace();
		}
	}

	public void writerAppend(String fileName, String content, boolean bl) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, bl);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void errorLog(String filePath,String content, boolean bl) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fileName = "error_"
					+ sdf.format(new Date()).replace("-", "") + ".txt";
			File myFilePath = new File(filePath+"\\"+fileName);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, bl);
			writer.write(content);
			writer.close();
			writer=null;
			myFilePath=null;
			fileName=null;
			sdf=null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long getFileLastTime(String filePath) {
		long jiange = 0;
		try {
			File file = new File(filePath);
			Date d1 = new Date(file.lastModified());
			Date d2 = new Date();
			long diff = d2.getTime() - d1.getTime();
			// long days = diff / (1000 * 60 * 60 * 24);
			jiange = diff / 1000;
		} catch (Exception er) {
			// System.out.println(er.toString());
		}
		return jiange;
	}

	/**
	 * 输出目录中的所有文件及目录名字
	 * 
	 * @param filePath
	 */
	public void readFolderByFileWave(String filePath) {
		File file = new File(filePath);
		String newfilePath = filePath;
		File[] tempFile = file.listFiles();
		
		int len=0;
		if(tempFile!=null){
			len=tempFile.length;
			for (int i = 0; i < len; i++) {
				if (tempFile[i].isDirectory()) {
					readFolderByFileWave(newfilePath.replace("//", "/")
							+ tempFile[i].getName() + "/");
				}
				if (tempFile[i].isFile()) {
					if (tempFile[i].getName().indexOf("wav") > 0) {
						folderByFile = folderByFile + ","
								+ newfilePath.replace("//", "/")
								+ tempFile[i].getName();
					}
				}
			}
		}
		
	}

	public String getFolderByFileWav() {
		return folderByFile;
	}

	/**
	 * 输出目录中的所有文件及目录名字
	 * 
	 * @param filePath
	 */
	public void readFolderByFileMp3(String filePath, HashMap<String, String> hm) {
		File file = new File(filePath);
		String newfilePath = filePath;
		// System.out.println("***************"+newfilePath+"**********");
		File[] tempFile = file.listFiles();
		
		int len=0;
		if(tempFile!=null){
			len=tempFile.length;
			for (int i = 0; i < len; i++) {
				if (tempFile[i].isFile()) {
					// System.out.println(newfilePath+"/"+tempFile[i].getName());
					if (tempFile[i].getName().equals(".mp3")
							|| tempFile[i].getName().equals(".MP3")) {
						Random random = new Random(100);// 指定种子数100
						String stg = new Date().getTime() + "" + random;
						hm.put(stg, newfilePath + "/" + tempFile[i].getName() + ";"
								+ tempFile[i].length());
					}
				}
				if (tempFile[i].isDirectory()) {
					// System.out.println("Directory : "+newfilePath+"/"+tempFile[i].getName());
					readFolderByFileMp3(newfilePath + "/" + tempFile[i].getName(),
							hm);
				}
			}
		}
		
	}

	public String utf8Togb2312(String str) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {

			char c = str.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				break;
			case '%':
				try {
					sb.append((char) Integer.parseInt(
							str.substring(i + 1, i + 3), 16));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException();
				}
				i += 2;
				break;
			default:
				sb.append(c);
				break;
			}
		}
		String result = sb.toString();
		String res = null;
		try {
			byte[] inputBytes = result.getBytes("8859_1");
			res = new String(inputBytes, "UTF-8");
		}
		catch (Exception e) {
		}
		return res;

	}

	public ArrayList<String> getRecTimeList(String fileName) {
		//System.out.println(fileName);
		ArrayList<String> list = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				//System.out.println(line);
				list.add(line);
				line = br.readLine();
			}
			br.close();
			fr.close();
			line=null;
			br=null;
			fr=null;
		} catch (Exception e) {
			System.out.println("读取文件操作出错");
			//e.printStackTrace();

		}
		return list;

	}

	public String gb2312ToUtf8(String str) {
		String urlEncode = "";
		try {
			urlEncode = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		return urlEncode;

	}
	
	public String nowTime(){
		//SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");//设置日期格式
		return dateToString(new Date(),"yy-MM-dd HH:mm:ss");
	}
	public static String dateToString(Date data, String formatType) {  
        return new SimpleDateFormat(formatType).format(data);  
    }
	public static Date yesterday(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return calendar.getTime();
    }
	public static Date tomorrow(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
    }

}
