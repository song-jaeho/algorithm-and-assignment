package assignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import assignment.model.DateUtil;
import assignment.model.WebLog;
import assignment.model.WebLogOutputWrapper;

public class WebLogService {

	private final String LEFT_DELIMITER = "\\[";
	private final String RIGHT_DELIMITER = "\\]";
	
	private final String INPUT_LOG_PATH = "D:\\input.log";
	private final String OUTPUT_LOG_PATH = "D:\\output.log";
	
	public static void main(String[] args) {
		
		WebLogService service = new WebLogService();
		
		List<WebLog> webLogList = service.readLog(service.INPUT_LOG_PATH);
		WebLogOutputWrapper wrapper = service.makeStatistics(webLogList);
		boolean result = service.writeLog(wrapper, service.OUTPUT_LOG_PATH);
		
		if(result) {
			System.out.println(service.OUTPUT_LOG_PATH + "에 정상적으로 파일 작성을 완료했습니다.");
		}
	}
	
	// 데이터를 온전하게 저장하는 책임
	public List<WebLog> readLog(String inputLogFilePath) {
		List<WebLog> webLogList = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(inputLogFilePath))) {
			
			String logLine = null;
			while((logLine = reader.readLine()) != null) {
				
				// 1. 구분자를 "]"로 지정할 것이기 때문에 원 문자열에서 필요없는 "["를 모두 지워버린다.
				logLine = logLine.replaceAll(LEFT_DELIMITER, "");
				
				String[] logArray = logLine.split(RIGHT_DELIMITER);
				
				// 2. 구분자로 잘 나뉘지 않아 정확하지 않은 데이터를 거름
				if (logArray.length != 4) {
					continue;
				}
				
				// 3. 데이터 담을 WebLog 인스턴스 생성
				WebLog webLog = new WebLog(
						Integer.parseInt(logArray[0]),
						logArray[1],
						logArray[2],
						DateUtil.getFormattedDate(logArray[3])
				);
				
				// 4. 쿼리파라미터와 서비스 아이디를 세팅하며 정상적인 url인지 판단
				// (apiKey나 q가 파라미터에 없는 경우는 예시에서 이미 걸러 상태코드를 10으로 set 한 상태)
				if (webLog.saveQueryParams() && webLog.saveServiceId()) {
					webLogList.add(webLog);
				}
			}
			
		} catch (IOException e) {
			printExceptionMessage(e);
			
		} catch (ParseException e) {
			printExceptionMessage(e);
			
		} catch (NumberFormatException e) {
			printExceptionMessage(e);
			
		} catch (Exception e) {
			printExceptionStackTrace(e);
		}
		
		return webLogList;
	}
	
	// 데이터로 통계를 만드는 책임
	public WebLogOutputWrapper makeStatistics(List<WebLog> webLogList) {
		
		//webLogList.stream().forEach(System.out::println);
		
		// output.log에 사용될 변수 세팅
		WebLogOutputWrapper wrapper = new WebLogOutputWrapper();
		String maxApiKey = null;
		List<String> topThreeResultStr;
		List<String> browsersResultStr;
		
		// 1. 최다 호출 API KEY 구하기 ============================================
		// 1.1. 상태코드 200인 WebLog만 찾아 apiKey기준으로 묶기
		Map<String, List<WebLog>> apiKeyMap = webLogList.stream()
			.filter(webLog -> webLog.getStatusCode() == 200)
			.collect(Collectors.groupingBy(WebLog::getApiKey));

		// 1.2. apiKey기준으로 묶인 맵에서 value의 size로 내림차순 정렬한 뒤 첫번째 것(가장 많이나온) 가져오기
		maxApiKey = apiKeyMap.keySet().stream()
			.sorted((key1, key2) -> {
				return apiKeyMap.get(key2).size() - apiKeyMap.get(key1).size();
			})
			.findFirst()
			.get();
		
		
		// 2. 상위 3개의 API ServiceID와 각각의 요청 수 구하기 ============================================
		// 2.1. 상태코드 200인 WebLog만 찾아 serviceId기준으로 묶기
		Map<String, List<WebLog>> serviceIdMap = webLogList.stream()
			.filter(webLog -> webLog.getStatusCode() == 200)
			.collect(Collectors.groupingBy(WebLog::getServiceId));
		
		// 2.2. serviceId기준으로 묶인 맵에서 value의 size로 내림차순 정렬한 뒤 리스트로 가져오기
		List<String> sortedServiceIdKeyList = serviceIdMap.keySet().stream()
			.sorted((key1, key2) -> {
				return serviceIdMap.get(key2).size() - serviceIdMap.get(key1).size();
			})
			.collect(Collectors.toList());
		
		// 2.3. 리스트 순회하며 serviceId와 그에 맞는 요청 수 구하기
		topThreeResultStr = new ArrayList<>();
		int topThreeListSize = 3;
		if (sortedServiceIdKeyList.size() < 3) {
			topThreeListSize = sortedServiceIdKeyList.size();
		}
		for (int i=0; i<topThreeListSize; i++) {
			String key = sortedServiceIdKeyList.get(i);
			int value = serviceIdMap.get(key).size();
			
			topThreeResultStr.add(key + " : " + value);
		}
		
		// 3. 웹브라우저별 사용 비율 구하기 ============================================
		// 3.1. 상태코드 200인 WebLog만 찾아 browserName기준으로 묶기
		Map<String, List<WebLog>> broswerMap = webLogList.stream()
				.filter(webLog -> webLog.getStatusCode() == 200)
				.collect(Collectors.groupingBy(WebLog::getBrowserName));
		
		// 3.2. 상태코드 200인 전체 요청의 갯수 구하기 (퍼센테이지 계산을 위해)
		long totalCallCount = webLogList.stream()
				.filter(webLog -> webLog.getStatusCode() == 200)
				.count();
		
		// 3.3. 퍼선테이지 계산해서 출력문구 만든 뒤 변수에 세팅해주기
		browsersResultStr = broswerMap.keySet().stream()
			.map(key -> {
				long percent = Math.round((double)broswerMap.get(key).size() / (double)totalCallCount * 100.0);
				return key + " : " + percent + "%";
			})
			.collect(Collectors.toList());
		
		// output.log에 사용될 변수 저장
		wrapper.setMaxApiKey(maxApiKey);
		wrapper.setBrowsersResultStr(browsersResultStr);
		wrapper.setTopThreeResultStr(topThreeResultStr);
		
		return wrapper;
		
	}
	
	// 데이터를 온전하게 출력하는 책임
	public boolean writeLog(WebLogOutputWrapper wrapper, String outputLogFilePath) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputLogFilePath))) {
			
			// 1. 최다호출 APIKEY
			writer.write("최다호출 API KEY");
			writer.newLine();
			writer.write(wrapper.getMaxApiKey());
			writer.newLine();
			writer.newLine();
			
			// 2. 상위 3개의 API ServiceID와 각각의 요청 수
			writer.write("상위 3개의 API ServiceID와 각각의 요청 수");
			writer.newLine();
			for (String str : wrapper.getTopThreeResultStr()) {
				writer.write(str);
				writer.newLine();
			}
			writer.newLine();
			
			// 3. 웹블하우저별 사용 비율
			writer.write("웹브라우저별 사용 비율");
			writer.newLine();
			for (String str : wrapper.getBrowsersResultStr()) {
				writer.write(str);
				writer.newLine();
			}
			
			// 버퍼 내용 쓰기
			writer.flush();
			
		} catch (IOException e) {
			printExceptionMessage(e);
			return false;
			
		} catch (Exception e) {
			printExceptionStackTrace(e);
			return false;
		}
		return true;
	}
	
	// 예상한 Exception을 시간과 함께 간단하게 출력
	public void printExceptionMessage(Exception e) {
		System.out.println("[" + DateUtil.getFormattedDateString() + "] " + e.getMessage());
	}
	
	// 예상하지 못한 Exception을 stackTrace
	public void printExceptionStackTrace(Exception e) {
		System.out.print("[" + DateUtil.getFormattedDateString() + "] ");
		e.printStackTrace();
	}
}
