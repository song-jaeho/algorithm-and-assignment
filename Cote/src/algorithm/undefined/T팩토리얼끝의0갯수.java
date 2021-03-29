package algorithm.undefined;
import java.math.BigDecimal;

/*
 * 주어진 수의 팩토리얼을 계산한 뒤에, 결과값 끝에 연속해서 나오는 0의 개수를 반환하시오.
 *
 * input sample
 * 1. 5
 * 2. 10 
 * 
 * output sample
 * 1. [1, 2, 0] --> 1
 * 2. [3, 6, 2, 8, 8, 0, 0] --> 2
 * 
 */
public class T팩토리얼끝의0갯수 {

	public static void main(String[] args) {
		T팩토리얼끝의0갯수 test = new T팩토리얼끝의0갯수();
		test.solution1(25);
		test.solution2(25);
	}
	
	/*
	 * 첫번째 방법. (알고 봤을땐 효율적이지 않다)
	 * 
	 * 큰 수를 다루기 때문에 BigDecimal을 이용해서 팩토리얼 값을 계산하고,
	 * 마지막 인덱스부터 탐색하며 0의 개수만큼 카운팅한다.
	 * 
	 */
	public void solution1(int n) {
		BigDecimal fact = factorial(new BigDecimal(n));
		String result = fact.toString();
		
		// char arr 끝에서부터 0인지 보고, 인덱스를 하나씩 낮춘다. 0이 아닌걸 찾을떄까지.
		char[] arr = result.toCharArray();
		int lastIndex = arr.length - 1;
		int count = 0;
		
		while(true) {
			if (lastIndex < 0) break;
			
			if (Integer.valueOf(arr[lastIndex]) == 48) {
				count++;
			} else {
				break;
			}
			lastIndex--;
		}
		System.out.println(count);
	}
	public BigDecimal factorial(BigDecimal n) {
		if (n.longValue() <= 1) {
			return n;
		}
		else {
			return n.multiply(factorial(n.subtract(new BigDecimal(1))));			
		}
	}
	
	/*
	 * 두 번째 방법.
	 * 
	 * 팩토리얼 뒷자리가 0이 나오는 경우는 2와 5가 곱해져 있을 때다.
	 * = 소인수분해 해서 2와 5가 존재할 경우 0으로 끝난다.
	 * 
	 * 뒷자리가 0이 n개 있다는 의미는 2와 5가 n개씩 짝지어 존재한다는 것이다.
	 * 둘을 짝지을 수 있는 개수는 적은 쪽에 따른다.
	 * 2는 5보다 작은 수이기 때문에 자연스레 2의 개수가 5의 개수보다 많다.
	 * 
	 * 이는 즉 5의 개수에 따라 값이 변한다고 볼 수 있으며, 여기에 5의 n승에 따라 카운트 값을 추가하면 된다.
	 * 단순히 5로 나누고 끝내지 않고 누적합을 해주면 된다.
	 * 
	 */
	public void solution2(int n) {
		int count = 0;
		while (n >= 5) {
			count += n / 5;
			n /= 5;
		}
		System.out.println(count);
	}
}