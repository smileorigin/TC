package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main_content.Config;

public class ImageFileDispose {

	public static String createImageFile(BufferedImage image) {
		String path = "";
		try {
			path = Config.NOWPATH + File.separator + DateUtil.getDate() + ".png";
			// 将图片写入本地
			ImageIO.write(image, "png", new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

	public static boolean deleteImageFile(String path) {
		File file = new File(path);
		return file.delete();
	}
}
