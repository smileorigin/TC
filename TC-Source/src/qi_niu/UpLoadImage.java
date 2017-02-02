package qi_niu;

import java.io.IOException;
import java.util.ArrayList;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import bean.Image;
import main_content.Config;
import util.ClipboardUtil;
import util.DateUtil;
import util.Dialog;
import util.ImageFileDispose;

public class UpLoadImage {

	// 类对象
	private static UpLoadImage mUpLoadImage;

	// 上传到七牛后保存的文件名
	private String mUpToken;
	// 上传文件的路径
	private String mFilePath;
	// 要上传的文件名
	private String mFileName;
	// 密钥配置
	private Auth mAuth;
	// 上传管理器
	private UploadManager mUploadManager;
	// 剪切板对象
	private ClipboardUtil mClipboardUtil;

	private UpLoadImage() {

	}

	public static UpLoadImage getInstance() {

		if (mUpLoadImage == null) {
			synchronized (UpLoadImage.class) {
				if (mUpLoadImage == null) {
					mUpLoadImage = new UpLoadImage();
				}
			}

		}

		return mUpLoadImage;

	}

	// 初始化
	public void init() {
		Init init = new Init();
		mAuth = init.getAuth();

		// 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
		Zone z = Zone.autoZone();
		Configuration c = new Configuration(z);
		// 创建上传对象
		mUploadManager = new UploadManager(c);
		// 简单上传，使用默认策略，只需要设置上传的空间名就可以了
		mUpToken = mAuth.uploadToken(init.getBucketName());

	}

	public void upload(ArrayList<Image> uploadList) throws IOException {

		if (mUploadManager == null || mUpToken == null) {
			init();
		}

		mClipboardUtil = ClipboardUtil.getInstance();

		new Thread() {
			public void run() {

				for (int i = 0; i < uploadList.size(); i++) {
					Image image = uploadList.get(i);

					mFileName = DateUtil.getDate() + image.getType();
					mFilePath = image.getPath();

					try {
						// 调用put方法上传
						mUploadManager.put(mFilePath, mFileName, mUpToken);
						// 如果上传成功将连接转换成MarkDown格式的图片连接，然后复制到剪切板
						String url = "![](" + "http://" + Config.getConfig()[3] + "/" + mFileName + ")\n";

						mClipboardUtil.append(url);

						if (!image.getIsLocation()) {
							ImageFileDispose.deleteImageFile(image.getPath());
						}

						System.out.println(url);

					} catch (QiniuException e) {
						// 如果上传失败，则将剪切板清空以便重新监听
						mClipboardUtil.appendClear();
						Response r = e.response;
						try {
							// 请求失败时打印的异常的信息
							// 响应的文本信息
							Dialog.showMessageDialog(r.toString() + "\n" + r.bodyString());
							System.out.println(r.bodyString());
						} catch (QiniuException e1) {
							e1.printStackTrace();
						}
					}
				}

				mClipboardUtil.appendComplete();
			}
		}.start();
	}

}
