package assignment.model;

import java.util.Date;

// input.log의 한 줄을 저장할 수 있는 모델 클래스
public class WebLog {

	// fields
	private int statusCode;
	private String url;
	private String browserName;
	private Date callTime;
	
	private String serviceId;
	private String apiKey;
	private String q;
	
	// constructor
	public WebLog() {
		super();
	}
	public WebLog(int statusCode, String url, String browserName, Date callTime) {
		super();
		this.statusCode = statusCode;
		this.url = url;
		this.browserName = browserName;
		this.callTime = callTime;
	}

	// custom methods
	public String getStringCallTime() {
		return DateUtil.getFormattedDateString(callTime);
	}
	public boolean saveQueryParams() {
		try {
			int index = url.indexOf("?");
			if (index == -1) return true;
			
			String[] params = url.substring(index + 1).split("&");
			for (String param : params) {
				String[] keyAndValue = param.split("=");
				
				// 쿼리 파라미터는 apiKey와 q만 있다고 가정, 나머지는 유효한 데이터 아님
				if (keyAndValue[0].equals("apikey")) {
					this.apiKey = keyAndValue[1];
				} else if (keyAndValue[0].equals("q")) {
					this.q = keyAndValue[1];
				}
			}
			
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	public boolean saveServiceId() {
		try {
			int index = url.lastIndexOf("/");
			if (index == -1) {
				return false;
			}
			
			// 필요하다면 여기서 blog || book || image || knowledge || news || vclip 중 하나인지 확인
			
			if (url.contains("?")) {
				this.serviceId = url.substring(index + 1, url.indexOf("?"));
			} else {
				this.serviceId = url.substring(index + 1);
			}
			
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	// default getters and setters
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBrowserName() {
		return browserName;
	}
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	public Date getCallTime() {
		return callTime;
	}
	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	
	@Override
	public String toString() {
		return "WebLog [statusCode=" + statusCode + ", url=" + url + ", browserName=" + browserName + ", callTime="
				+ callTime + ", serviceId=" + serviceId + ", apiKey=" + apiKey + ", q=" + q + "]";
	}
	
}
