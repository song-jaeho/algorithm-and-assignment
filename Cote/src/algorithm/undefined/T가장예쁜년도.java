package algorithm.undefined;

/**
 * 입력받는 연도 이후로 처음으로 오는 "가장 아름다운 연도"를 찾아내서 출력
 * "가장 아름답다"는 것은 중복되는 숫자가 가장 적은 것을 의미한다.
 * 
 * (ex) 2000을 입력 받았을 때, 2222년도는 가장 아름답지 않고 2013년도는 가장 아름답다. 
 * 
 * @author song-jaeho
 *
 */
public class T가장예쁜년도 {

	public static void main(String[] args) {
		T가장예쁜년도 e = new T가장예쁜년도();
		System.out.println(e.solution(1987));
	}

	int solution(int p) {
		int answer = p;
		answer++;
		
		while(true) {
			int cnt = 0;			
			char[] charArr = String.valueOf(answer).toCharArray();
			
			for (char o1 : charArr) {
				for (char o2 : charArr) {
					// 2중 for문이기 때문에 하나는 무조건 겹친다. 추가로 cnt가 증가하면 아름답지 않아진다.
					if ( o1 == o2 ) cnt++;
				}
			}
			
			// 2중 for문에서 하나는 무조건 겹쳤기 때문에 cnt와 charArr.length가 같다는 것은
			// 중복이 최소화된 가장 아름다운 연도를 의미. break
			if (cnt != charArr.length) {
				answer++;
			} else {
				break;
			}
		}
		return answer;
	}
}
