package algorithm.undefined;

/**
 * 행과 열의 수가 같은 2차원 배열이 주어진다.
 * 배열에서 값이 1인 곳은 사람이 있는 곳이고 0은 사람이 없는 곳이다.
 * 
 * 인자 k는 온풍기가 따듯하게 할 수 있는 최대 범위다.
 * 
 * 2차원 배열과 인자 k가 주어졌을 때, 온풍기가 따듯하게 만들 수 있는 최대 사람 수를 구한다.
 * (ex 2행 2열인 배열의 경우 k가 2라면 배열 안의 모든 1을 카운팅한 수가 따듯하게 할 수 있는 범위가 된다.)
 * 
 * @author song-jaeho
 *
 */
public class T따듯하게할수있는범위 {
	
	public static void main(String[] args) {
		T따듯하게할수있는범위 s = new T따듯하게할수있는범위();
		
		int result = s.solution(new int[][] {{1,0,0,0},{0,0,0,1},{0,0,1,0},{0,1,1,0}}, 2);
		//int result = s.solution(new int[][] {{1,0,0},{0,0,1},{1,0,0}}, 2);
		
		System.out.println(result);
	}
    public int solution(int[][] office, int k) {
    	int answer = 0;
        
        for(int i=0; i<office.length; i++) {
			for(int j=0; j<office[0].length; j++) {
				// 시작 포인트를 정하고, 거기서부터 k로 계산된 max값을 구해온다.
				// 결과값이 임시 저장된 answer와 비교하여 더 큰 경우 갈아치운다.
				answer = Math.max(calculate(office, i, j, k), answer);
			}
		}
        return answer;
    }
    
    int calculate(int[][] office, int i, int j, int k) {
        int result = 0;
        
        int x = office.length - 1;
        int y = office[0].length - 1;

        for (int a = 0; a < k; a++) {
        	for (int b = 0; b < k; b++) {
        		if (i+a > x || j+b > y) {
        			continue;
        		}
        		 
        		if (office[i+a][j+b] == 1) {
        			result++;
        		}
        	}
        }
        return result;
    }
}