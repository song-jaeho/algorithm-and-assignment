package algorithm.undefined;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 배열 크기, 선택정렬을 진행하려는 횟수, 정렬 대상 배열을 입력받아
 * 배열에 대해 진행횟수 만큼 선택정렬을 수행한 결과를 공백 구분으로 출력한다.
 * 
 * input sample
 * 5 3
 * 1 5 3 2 4
 * 
 * output sample
 * 1 2 3 5 4
 * 
 * @author song-jaeho
 *
 */
public class T선택정렬 {

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = 0, s = 0;
		int[] arr = {};
		
		String line1;
		if ((line1 = br.readLine()) != null) {
			n = Integer.parseInt(line1.split(" ")[0]);
			s = Integer.parseInt(line1.split(" ")[1]);
		}
		String line2;
		if ((line2 = br.readLine()) != null) {
			arr = new int[n];
			String[] tempArr = line2.split(" ");
			for(int i = 0; i < tempArr.length; i++) {
				arr[i] = Integer.parseInt(tempArr[i]);
			}
		}
		solution(n, s, arr);
	}

	public static void solution(int n, int s, int[] arr) {
		// n = 수열의 개수
		// s = 진행 단계
		// arr = 정렬 대상 배열
		int minIndex, temp;
		for (int i = 0; i < s; i++) {
			minIndex = i;
			for (int j = i + 1; j < n; j++) {
				if (arr[j] < arr[minIndex]) {
					minIndex = j;
				}
			}
			
			temp = arr[minIndex];
			arr[minIndex] = arr[i];
			arr[i] = temp;
		}
		
		print(arr);
	}
	
	public static void print(int[] arr) {
		for(int a : arr) {
			System.out.print(a + " ");
		}
	}
}
