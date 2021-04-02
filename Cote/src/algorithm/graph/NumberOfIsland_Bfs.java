package algorithm.graph;

import java.util.*;

public class NumberOfIsland_Bfs {

	public static void main(String[] args) {
		char[][] grid= {
				{'1','1','0','0','1'},
				{'1','1','0','0','0'},
				{'0','0','0','0','0'},
				{'1','0','0','1','1'}
			   };
		NumberOfIsland_Bfs a = new NumberOfIsland_Bfs();
		System.out.println(a.solve(grid));
	}
	
	int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1 ,0}};
	
	int solve(char[][] grid) {
		int result = 0;
		
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid[0].length; j++) {
				if (grid[i][j] == '1') {
					result++;
					bfs(grid, i, j);
				}
			}
		}
		
		return result;
	}
	
	void bfs(char[][] grid, int i, int j) {
		Queue<int[]> que = new LinkedList<int[]>();
		grid[i][j] = '0';
		que.offer(new int[] {i, j});
		
		while(!que.isEmpty()) {
			int[] p = que.poll();
			for(int[] dir : dirs) {
				int dx = p[0] + dir[0];
				int dy = p[1] + dir[1];
				if (dx >= 0 && dx < grid.length && dy >= 0 && dy < grid[0].length && grid[dx][dy] == '1') {
					grid[dx][dy] = '0';
					que.offer(new int[] {dx, dy});
				}
			}
		}
	}
}
