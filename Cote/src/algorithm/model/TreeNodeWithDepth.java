package algorithm.model;

//심플하게 사용하기 위해 캡슐화 하지 않음, 모든 접근제한자 public으로 
public class TreeNodeWithDepth extends TreeNode {

	public int depth;
	public TreeNodeWithDepth(TreeNode node, int depth) {
		super(node.val);
		this.left = node.left;
		this.right = node.right;
		this.depth = depth;
	}
	
}
