package qi_niu;

import com.qiniu.util.Auth;

import main_content.Config;

public class Init {

	private String[] mConfig;

	public Init() {
		mConfig = Config.getConfig();
	}

	public Auth getAuth() {
		Auth auth = Auth.create(mConfig[0], mConfig[1]);
		return auth;
	}

	public String getBucketName() {
		return mConfig[2];
	}
}
