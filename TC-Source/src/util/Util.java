package util;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Util {

	// »ñÈ¡ÆÁÄ»³ß´ç
	public static Dimension getScreenDimension() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		return dimension;
	}
}
