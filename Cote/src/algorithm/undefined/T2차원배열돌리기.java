package algorithm.undefined;

/**
 * 행과 열의 수가 같은 2차원 배열(matrix)을 받아 시계방향으로 90도씩 회전시키기 (인자 r회 만큼 반복 수행)
 * @author song-jaeho
 *
 */
public class T2차원배열돌리기 {

	public static void main(String[] args) {
		
		/*
		 * test data
		 * 4 1 2
		 * 7 3 4
		 * 3 5 6
		 */
		int[][] mat = new int[][] {{4,1,2},{7,3,4},{3,5,6}};

		T2차원배열돌리기 t = new T2차원배열돌리기();
		
		int[][] result = t.solution(mat, 3);
		
		// 결과 출력용
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				System.out.print(result[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	
	public int[][] solution(int[][] matrix, int r) {

        int[][] temp = new int[matrix.length][matrix.length];
        
        // r % 4 하는 이유.. 네 번 돌면 원위치기 때문에 의미 없는 작업 -> 나머지 만큼만 수행하면 된다.
        for (int i = 0; i < (r % 4); i++) {
        	matrix = rotate(matrix.length, matrix, temp);
        }

        return matrix;
    }

	public int[][] rotate(int n, int[][] matrix, int[][] temp) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				temp[i][j] = matrix[n - 1 - j][i];
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = temp[i][j];
			}
		}
		
		/* 테스트 출력용
		System.out.println("-----------------");
		for(int i=0; i<matrix.length; i++) {
			for(int j=0; j<matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("-----------------");
		*/
		
		return matrix;
	}
	
}
