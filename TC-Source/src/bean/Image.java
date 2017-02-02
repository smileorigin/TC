package bean;

public class Image {

	// 三个变量
	// 路径
	public String path;
	// 后缀
	public String type;
	// 是否本地文件
	public boolean isLocation;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String end) {
		this.type = end;
	}

	public boolean getIsLocation() {
		return isLocation;
	}

	public void setIsLocation(boolean isLocation) {
		this.isLocation = isLocation;
	}

}
