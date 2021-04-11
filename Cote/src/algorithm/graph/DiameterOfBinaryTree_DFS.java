package algorithm.graph;

import java.util.*;

import algorithm.model.TreeNode;
import algorithm.model.TreeNodeWithDepth;

/*
 * 이진 트리의 최대 높이를 구하기 (DFS 방식 : 스택 으로 풀이)
 */
public class DiameterOfBinaryTree_DFS {

	public static void main(String[] args) {
		DiameterOfBinaryTree_DFS a = new DiameterOfBinaryTree_DFS();
		TreeNode node = new TreeNode(1);
		node.left = new TreeNode(2);
		node.right = new TreeNode(3);
		node.left.left = new TreeNode(4);
		node.left.right = new TreeNode(5);
		node.left.left.left = new TreeNode(6);
		
		System.out.println("val dfs: "+a.dfs(node));
		System.out.println("val dfs2: "+a.dfs2(node));
	}
	
	public int dfs(TreeNode root) {
		if (root == null)
			return 0;

		Stack<TreeNode> stack = new Stack<>();
		Stack<Integer> value = new Stack<>();
		stack.push(root);
		value.push(1);
		int max=0;
		
		while(!stack.isEmpty()) {
			TreeNode node = stack.pop();
			System.out.println("node: " + node.val);
			int count = value.pop();
			max = Math.max(max, count);
			if (node.left != null) {
				stack.push(node.left);
				value.push(count + 1);
			}
			if (node.right != null) {
				stack.push(node.right);
				value.push(count + 1);
			}
		}
		return max;
	}
	
	public int dfs2(TreeNode root) {
		if (root == null) {
			return 0;			
		}

		Stack<TreeNodeWithDepth> stack = new Stack<TreeNodeWithDepth>();
		stack.push(new TreeNodeWithDepth(root, 1));
		
		int max = 0;
		
		while(!stack.empty()) {
			TreeNodeWithDepth node = stack.pop();
			max = Math.max(node.depth, max);
			
			if(node.left != null) {
				stack.push(new TreeNodeWithDepth(node.left, node.depth + 1));
			}
			if(node.right != null) {
				stack.push(new TreeNodeWithDepth(node.right, node.depth + 1));
			}
		}
		
		return max;
	}
	
	
}