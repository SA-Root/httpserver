package log;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.io.FileOutputStream;

public class Log {

	private static FileOutputStream outputStream = null;

	// 创建一个日志文件，注意目录和Windows的不一样
	public static void createFile(String webroot) throws IOException {
		String logName = webroot + "/log/" + LocalDateTime.now().toString() + ".log";
		try {
			File fp = new File(logName);
			if (fp.createNewFile()) {
				System.out.println("Log file created Successfully!");
			} else {
				System.out.println("Log file already exists!");
			}
			outputStream = new FileOutputStream(fp);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
	}

	public static synchronized void writeLog(LogDetail logDetail, String webroot) throws IOException {
		if (outputStream == null) {
			createFile(webroot);
		}
		try {
			// 写入字节流
			outputStream.write(logDetail.toString().getBytes());
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
	}

}