package assignment;

import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.opencsv.CSVReader;

/**
 * 아래와 같은 형태의 CSV파일을 파싱하여 DESIRED_PERIOD 구간 별 계산 합계 (PeriodData) 정보를 JSON으로 리턴한다.
 * 
 * timestamp, price, size 
 * 1378189897	160000	0.1
 * 1378224414	150000	0.2
 * 1378254765	180000	2.94
 * 1378271222	180000	0.5
 * 
 * @author song-jaeho
 *
 */
public class CandlestickChartData {

	private static final String CSV_FILE_PATH = "D:\\testData.csv";
	private static final int DESIRED_PERIOD = 30000; // seconds.. greater than or equal to 30 seconds and less than or equal to 86400 seconds
	
	public static void main(String[] args) {
		String result = makeJsonData(CSV_FILE_PATH, DESIRED_PERIOD);
		
		// The KRW and BTC rounding criterion used Math.round() and RoundingMode.HALF_UP
		System.out.println("[CandlestickChartData makeJsonData result (" + new Date() + ")] ------------");
		System.out.println(result);
	} 
		
	@SuppressWarnings("unchecked")
	public static String makeJsonData(String filePath, int desiredPeriod) {
		
		JSONArray jsonArr = new JSONArray();
		List<LineData> dataList = new ArrayList<LineData>();
		
		try(CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
			
			int index = 0;
			
			String[] line;
			while((line = csvReader.readNext()) != null) {
				/* 
				 * 데이터 순서가 꼬인경우 작동하지않음, 추가 후 Collections.sort로 대체
				 *  
				if ( dataList.size() == 0 ) {
					dataList.add(new LineData(line[0], line[1], line[2]));
				} else {
					for (index = dataList.size() - 1; index >= 0; index--) {
						// Add item with sort
						if ( (dataList.get(index).getTimestamp().compareTo(line[0])) <= 0 ) {
							dataList.add(index + 1, new LineData(line[0], line[1], line[2]));
							break;
						}
					}
				}
				*/
				dataList.add(new LineData(line[0], line[1], line[2]));	
			}
			Collections.sort(dataList, (l1, l2) -> l1.getIntTimeStamp() - l2.getIntTimeStamp());
			
			int start = dataList.get(0).getIntTimeStamp();
			int end = start + (desiredPeriod - 1);
			
			int lastEnd = dataList.get(dataList.size() - 1).getIntTimeStamp() + (desiredPeriod - 1);
			
			List<PeriodData> periodDataList = new ArrayList<PeriodData>();
			int lastCreatedEnd = start;
			
			while(lastCreatedEnd <= lastEnd) {
				periodDataList.add(new PeriodData(start, end));
				start = end + 1;
				end = start + (desiredPeriod - 1);
				lastCreatedEnd = end;
			};
			
			int i = 0;
			for (PeriodData periodData : periodDataList) {
				for (index = i; index < dataList.size(); index++) {
					if ( (periodData.getStart() <= dataList.get(index).getIntTimeStamp())
							&& (periodData.getEnd() >= dataList.get(index).getIntTimeStamp()) ) {
						periodData.addData(dataList.get(index));
					} else {
						i = index; // increase the start index
						break;
					}
				}
				periodData.setCalculateValues();
				jsonArr.add(periodData.toJSONObject());
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArr.toJSONString();
	}
}

class LineData {
	private String timestamp;
	private String price;
	private String size;
	
	public LineData(String timestamp, String price, String size) {
		super();
		this.timestamp = timestamp;
		this.price = price;
		this.size = size;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	public int getIntTimeStamp() {
		return Util.getIntValue(this.timestamp);
	}
	public int getIntPrice() {
		return (int) Math.round(Double.valueOf(this.price));
	}
	public double getDoubleSize() {
		return Double.valueOf(this.size);
	}
}

class PeriodData {
	private int start; 		// integer Start time (in UNIX timestamp) of the period.
	private int end; 		// integer End time (in UNIX timestamp) of the period.
	
	private String open;	// string Price (in KRW) at the start of the period.
	private String close;	// string Price (in KRW) at the end of the period.
	
	private String high;		// string Highest price (in KRW) during the period.
	private String low;		// string Lowest price (in KRW) during the period.
	
	private String average;	// string Average price per trade (in KRW) during the period.
	private String weighted_avergage; // string Weighted average price (in KRW) during the period.
	
	private String volume;	// string Total volume traded during the period (in BTC).
	
	private double weight;
	private long total;
	
	private List<LineData> datas;
	
	public PeriodData(int start, int end) {
		super();
		this.start = start;
		this.end = end;
		this.datas = new ArrayList<LineData>();
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put("start", start);
		obj.put("end" , end);
		obj.put("open" , open);
		obj.put("close" , close);
		obj.put("high" , high);
		obj.put("low" , low);                  
		obj.put("average" , average);
		obj.put("weighted_avergage" , weighted_avergage);
		obj.put("volume" , volume);
		return obj;
	}
	
	public void addData(LineData data) {
		this.datas.add(data);
	}
	public void setCalculateValues() {
		if (datas.size() == 0) {
			this.volume = "0.00000000";
		} else {
			//----- start initiating
			this.high = "0";
			this.low = String.valueOf(Integer.MAX_VALUE);
			this.volume = "0";
			
			//----- start calculating
			for (LineData data : datas) {
				if ( data.getIntPrice()  > Util.getIntValue(getHigh()) ) {
					this.high = String.valueOf(data.getIntPrice());
				}
				if ( data.getIntPrice() < Util.getIntValue(getLow()) ) {
					this.low = String.valueOf(data.getIntPrice());
				}
				
				this.total += data.getIntPrice();
				this.volume = String.valueOf(Util.getDoubleValue(getVolume()) + data.getDoubleSize());
				this.weight += data.getIntPrice() * data.getDoubleSize();
			}
			
			this.open = String.valueOf(datas.get(0).getIntPrice());
			this.close = String.valueOf(datas.get(datas.size() - 1).getIntPrice());
			this.average = String.valueOf(Math.round((double)(getTotal() / datas.size())));
			
			this.weighted_avergage = String.valueOf(Math.round(getWeight() / Double.valueOf(getVolume())));
			
			//----- start formatting 
			this.volume = String.valueOf(new BigDecimal(getVolume()).setScale(8, RoundingMode.HALF_UP)); // for units of BTC round to the nearest Satoshi
			// remove this comment for use formatting with comma
			// this.weighted_avergage = NumberFormat.getInstance().format(weighted_avergage); 
		}
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getAverage() {
		return average;
	}
	public void setAverage(String average) {
		this.average = average;
	}
	public String getWeighted_avergage() {
		return weighted_avergage;
	}
	public void setWeighted_avergage(String weighted_avergage) {
		this.weighted_avergage = weighted_avergage;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
}

class Util {
	public static int getIntValue(String val) {
		int returnValue = 0;
		if ( val == null ) {
			return 0;
		}
		
		String val2 = val.replaceAll("[^0-9]", "");
		
		try {
			returnValue = Integer.valueOf(val2);
		} catch(Exception e1) {
			e1.printStackTrace();
			try {
				returnValue = (int) Math.round(Double.valueOf(val2));
			} catch (Exception e2) {
				e2.printStackTrace();
				return 0;
			}
		}
		return returnValue;
	}
	public static int getIntValue(double val) {
		return (int) val;
	}
	public static double getDoubleValue(String val) {
		if ( val == null ) {
			return 0.0;
		}
		return Double.valueOf(val);
	}
	public static double getDoubleValue(int val) {
		return Double.valueOf(val);
	}
}