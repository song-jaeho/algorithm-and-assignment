package algorithm.graph;

public class NumberOfIsland_DFS {

	public static void main(String[] args) {
		char[][] grid= {
						{'1','1','0','0','0'},
						{'1','1','0','0','0'},
						{'0','0','1','0','0'},
						{'0','0','0','1','1'}
					   };
		
		NumberOfIsland_DFS a = new NumberOfIsland_DFS();
		System.out.println(a.numsIslands(grid));
	}
	
	int numsIslands(char[][] grid) {
		int result = 0;
		
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid[0].length; j++) {
				if (grid[i][j] == '1') {
					result++;
					dfs(grid, i, j);
				}
			}
		}
		
		return result;
	}
	
	void dfs(char[][] grid, int i, int j) {
		if (i<0 || i>=grid.length || j<0 || j>=grid[0].length || grid[i][j] != '1') {
			return;
		}
		grid[i][j] = 'X';
		
		dfs(grid, i+1, j);
		dfs(grid, i-1, j);
		dfs(grid, i, j+1);
		dfs(grid, i, j-1);
	}

}
