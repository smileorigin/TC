package util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import Constant.Constant;
import bean.Image;
import qi_niu.UpLoadImage;

public class ClipboardUtil {

	private FlavorListener mFlavorListener;
	private Clipboard mClipboard;

	private static ClipboardUtil mClipboardUtil;

	private StringBuffer temp = new StringBuffer();

	private ClipboardUtil() {

	}

	public static ClipboardUtil getInstance() {

		if (mClipboardUtil == null) {
			synchronized (ClipboardUtil.class) {
				if (mClipboardUtil == null) {
					mClipboardUtil = new ClipboardUtil();
				}
			}

		}

		return mClipboardUtil;

	}

	public void init() {
		mClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		mFlavorListener = new FlavorListener() {

			@Override
			public void flavorsChanged(FlavorEvent e) {
				String imagePath = getImageClipboardPath();
				if (imagePath != null) {
					// ÉÏ´«Í¼Æ¬
					UpLoadImage upLoadImage = UpLoadImage.getInstance();
					try {
						Image image = new Image();
						image.setPath(imagePath);
						image.setType(Constant.PNG);
						image.setIsLocation(false);
						ArrayList<Image> images = new ArrayList<>();
						images.add(image);
						upLoadImage.upload(images);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
	}

	public void addListener() {

		mClipboard.addFlavorListener(mFlavorListener);
	}

	public void removedListener() {

		mClipboard.removeFlavorListener(mFlavorListener);
	}

	public void add(String content) {
		Transferable transferable = new StringSelection(content);
		mClipboard.setContents(transferable, null);
	}

	public void append(String imagePath) {
		temp.append(imagePath);
	}

	public void appendClear() {
		temp.delete(0, temp.length());
	}

	public void appendComplete() {
		Transferable transferable = new StringSelection(temp.toString());
		mClipboard.setContents(transferable, null);
		mClipboardUtil.appendClear();
	}

	public String getImageClipboardPath() {

		Transferable t = mClipboard.getContents(null);

		try {
			if (null != t && t.isDataFlavorSupported(DataFlavor.imageFlavor)) {
				BufferedImage image = (BufferedImage) t.getTransferData(DataFlavor.imageFlavor);
				return ImageFileDispose.createImageFile(image);
			}
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
}
