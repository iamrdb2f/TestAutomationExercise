package org.lafed.testautomationexercise.handlers;

public abstract class AbstractHandler {
	protected String environment;
	
	protected String browserType;
	
	protected String downloadPath;
	
	protected String url;
	
	protected String userName;
	
	protected String password;

	public String getEnvironment() {
		return environment;
	}

	public String getBrowserType() {
		return browserType;
	}
	
	public String getDownloadPath() {
		return downloadPath;
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
	public AbstractHandler(String environment, String browserType, String downloadPath, String url, String userName, String password) {
		this.environment = environment;
		this.browserType = browserType;
		this.downloadPath = downloadPath;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

}
