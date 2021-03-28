package algorithm.undefined;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 문자열을 입력 받아 영문자(대소문자 구분X)의 출현 횟수를 카운트하여 출력한다.
 * 
 * input sample
 * apple-banana maSIDDA goodgood
 * 
 * output sample
 * a : 6
 * b : 1
 * c : 0
 * d : 4
 * e : 1
 * f : 0
 * g : 2
 * h : 0
 * i : 1
 * j : 0
 * k : 0
 * l : 1
 * m : 1
 * n : 2
 * o : 4
 * p : 2
 * q : 0
 * r : 0
 * s : 1
 * t : 0
 * u : 0
 * v : 0
 * w : 0
 * x : 0
 * y : 0
 * z : 0
 * 
 * @author song-jaeho
 *
 */
public class T알파벳출현횟수 {

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		
		solution(input);
	}

	public static void solution(String input) {
		if (input != null) {
			input = input.toLowerCase();
		}
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		
		char alphaStart = 97; // 소문자의 시작 'a' ... 대문자인 경우 65 = 'A'부터 시작
		while(true) {
			
			// 1. 소문자만 출력하는 경우
			map.put(alphaStart++, 0);
			if (alphaStart > 122) {
				break;
			}
			/* 2. 대소문자 모두 출력하는 경우
			// '[', '\', ']', '^', '_', '`' 패스를 위해 91~96 건너뜀
			if (alphaStart == 91) {
				alphaStart = 97;
			}
			
			map.put(alphaStart++, 0);
			if (alphaStart > 122) {
				break;
			}
			*/
		}
		
		for (char c : input.toCharArray()) {
			// (소문자) || (대문자)
			if ((c >= 0x61 && c <= 0x7A) || (c >=0x41 && c <= 0x5A)) {
				map.put(c, map.getOrDefault(c, 0) + 1);
			}
		}
		
		Iterator<Character> it =  map.keySet().iterator();
		while(it.hasNext()) {
			char n = it.next();
			System.out.println(n + " : " + map.get(n));
		}
	}
}
