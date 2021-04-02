package algorithm.undefined;
import java.util.*;

/*
 * 아이스크림 추출구 개수 N, 각 아이스크림을 만드는 데 걸리는 시간이 주문번호 순서대로 담긴 배열 icecream_times
 * 아이스크림이 완성되는 순서대로 주문번호를 배열에 담아 return 하시오.
 * 
 * 아이스크림 주문이 추출구에 배정되는데 걸리는 시간은 없다고 가정, 아이스크림 추출이 동시에 완료되는 경우 작은 주문번호가 앞에 오도록 하시오.
 * 
 * input/output sample
 * 1. 
 * 	3 [4, 2, 2, 5, 3]		--> [2, 3, 1, 5, 4]
 * 2.
 * 	1 [100, 1, 50, 1, 1] 	--> [1, 2, 3, 4, 5]
 * 
 */ 
public class T아이스크림머신 {

public static void main(String[] args) {
		
		int[] result = solution(3, new int[] {4, 2, 2, 5, 3});
		//int[] result = solution(1, new int[] {100, 1, 50, 1, 1});
		
		System.out.println(Arrays.toString(result));
	}

	public static int[] solution(int n, int[] icecream_times) {
		// n = 아이스크림 추출구 수
		// icecream_times = 아이스크림별 제조시간
		List<IceCream> list = new ArrayList<IceCream>();
		
		int index = 0;
		for (; index < n; index++) {
			// 실제 출력 순서는 1번째부터 시작하므로 인덱스에 1 더한다.
			list.add(new IceCream(index + 1, icecream_times[index]));
		}
		n = 0;
		List<Integer> answerList = new ArrayList<>();
		
		while(answerList.size() < icecream_times.length) {
			Iterator<IceCream> iter = list.iterator();
			while(iter.hasNext()) {
				IceCream coffe = iter.next();
				
				if (coffe.getLeftTime() == 0) {
					answerList.add(coffe.getIndex());
					
					iter.remove();
					n++;
				} else {
					coffe.setLeftTime(coffe.getLeftTime() -1);
				}
			}
			
			if (index < icecream_times.length) {
				while(n > 0) {
					// 실제 출력 순서는 1번째부터 시작하므로 인덱스에 1 더한다.
					list.add(new IceCream(index + 1, icecream_times[index]));
					index++;
					n--;
				}
			}
		}
		
		
		// return 값용 타입변환처리
		int[] result = new int[icecream_times.length];
		for (int i = 0; i < answerList.size(); i++) {
			result[i] = answerList.get(i);
		}
		return result;
	}

}

class IceCream {
	int index;
	int leftTime;
	public IceCream(int index, int leftTime) {
		super();
		this.index = index;
		this.leftTime = leftTime;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getLeftTime() {
		return leftTime;
	}
	public void setLeftTime(int leftTime) {
		this.leftTime = leftTime;
	}
	@Override
	public String toString() {
		return "IceCream [index=" + index + ", leftTime=" + leftTime + "]";
	}
	
}
