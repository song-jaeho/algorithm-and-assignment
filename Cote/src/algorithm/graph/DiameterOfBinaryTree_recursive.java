package algorithm.graph;

import algorithm.model.TreeNode;

/*
 * 이진 트리의 최대 높이를 구하기 (재귀 방식으로 풀이)
 */
public class DiameterOfBinaryTree_recursive {

	public static void main(String[] args) {
		DiameterOfBinaryTree_recursive a = new DiameterOfBinaryTree_recursive();
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.left.left.left = new TreeNode(7);
		System.out.println(a.maxDepth(root));
	}

	public int maxDepth(TreeNode root) {
		if (root == null)
			return 0;
		int left = maxDepth(root.left);
		int right = maxDepth(root.right);
		return Math.max(left, right) + 1;
	}

}