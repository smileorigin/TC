package main_content;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

	private final static String CFG = "Config.cfg";
	private final static String DIR = "Config";
	private final static String SEP = File.separator;
	private final static String PATH = SEP + DIR + SEP + CFG;
	public final static String NOWPATH = System.getProperty("user.dir");

	/*
	 * 检查配置文件是否存在
	 */
	protected static boolean checkConfig() {
		boolean exit = false;
		File config = new File(NOWPATH + PATH);
		exit = config.exists();
		return exit;
	}

	/*
	 * 新建配置文件
	 */
	protected static boolean createConfig(String ak, String sk, String bucketName, String domain) {
		boolean result = true;
		File dir = new File(NOWPATH + SEP + DIR);
		if (!dir.exists()) {
			result = dir.mkdir();
		}
		if (!checkConfig()) {
			try {
				File config = new File(NOWPATH + PATH);
				result = config.createNewFile();
				FileWriter writer = new FileWriter(config);
				BufferedWriter bufferedWriter = new BufferedWriter(writer);
				bufferedWriter.write(ak);
				// win下的换行符是"\r\n"
				bufferedWriter.newLine();
				bufferedWriter.write(sk);
				bufferedWriter.newLine();
				bufferedWriter.write(bucketName);
				bufferedWriter.newLine();
				bufferedWriter.write(domain);
				bufferedWriter.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * 获取ak和sk
	 */
	public static String[] getConfig() {
		String[] result = new String[4];
		if (checkConfig()) {
			try {
				File config = new File(NOWPATH + PATH);
				FileReader reader = new FileReader(config);
				BufferedReader bufferedReader = new BufferedReader(reader);
				result[0] = bufferedReader.readLine();
				result[1] = bufferedReader.readLine();
				result[2] = bufferedReader.readLine();
				result[3] = bufferedReader.readLine();
				bufferedReader.close();
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * 删除配置文件
	 */
	protected static boolean removedConfig() {
		boolean result = false;
		if (checkConfig()) {
			File file = new File(NOWPATH + PATH);
			result = file.delete();
		}
		return result;
	}
}
