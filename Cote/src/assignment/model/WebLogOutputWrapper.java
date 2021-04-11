package assignment.model;

import java.util.List;

// output.log를 만들기 위해 데이터를 담아두는 wrapper 클래스
public class WebLogOutputWrapper {
	
	private String maxApiKey = null;
	private List<String> topThreeResultStr;
	private List<String> browsersResultStr;
	
	public String getMaxApiKey() {
		return maxApiKey;
	}
	public void setMaxApiKey(String maxApiKey) {
		this.maxApiKey = maxApiKey;
	}
	public List<String> getTopThreeResultStr() {
		return topThreeResultStr;
	}
	public void setTopThreeResultStr(List<String> topThreeResultStr) {
		this.topThreeResultStr = topThreeResultStr;
	}
	public List<String> getBrowsersResultStr() {
		return browsersResultStr;
	}
	public void setBrowsersResultStr(List<String> browsersResultStr) {
		this.browsersResultStr = browsersResultStr;
	}
}
