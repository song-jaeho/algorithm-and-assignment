package algorithm.model;

// 여러 군데서 쓸 것이기 때문에 빼놓음
// 심플하게 사용하기 위해 캡슐화 하지 않음, 모든 접근제한자 public으로 
public class TreeNode {
	public int val;
	public TreeNode left, right;

	public TreeNode(int x) {
		this.val = x;
	}
}
