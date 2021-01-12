import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * LeetCode 1382. Balance a Binary Search Tree
 * https://leetcode.com/problems/balance-a-binary-search-tree/
 */
public class BalanceABinarySearchTree {


    // **** ****
    static BST avl = null;


    /**
     * Inorder BST traversal.
     * Recursive call.
     */
    static void copyToAVL(TreeNode node) {
        if (node != null) {

            // **** traverse left tree ****
            copyToAVL(node.left);

            // **** insert node into AVL tree ****
            avl.root = avl.insertAVL(avl.root, node.val);

            // **** traverse right tree ****
            copyToAVL(node.right);
        }
    }


    /**
     * Given a binary search tree (BST), return a balanced BST 
     * with the same node values.
     * 
     * Traverse nodes in inorder and one by one insert into the AVL tree.
     * Time complexity of this solution is O(n Log n).
     */
    static TreeNode balanceBST(TreeNode root) {

        // **** sanity check(s) ****
        if (root == null)
            return null;

        if (root.left == null && root.right == null)
            return root;

        // **** initialization ****
        avl = new BST();

        // **** traverse inorder BST inserting nodes in AVL tree ****
        copyToAVL(root);

        // **** returned balanced BST ****
        return avl.root;
    }


    /**
     * Fill the array list with the ordered node values.
     * Recursive method.
     */
    static void fillALInOrder(TreeNode root, List<TreeNode> al) {

        // **** base condition ****
        if (root == null)
            return;

        // **** visit left tree ****
        fillALInOrder(root.left, al);

        // **** insert value into array list ****
        al.add(root);

        // **** visit right tree ****
        fillALInOrder(root.right, al);
    }


    /**
     * Populate balanced BST using int[] arr
     * Recursive call. O(n)
     */
    static TreeNode populateBST(List<TreeNode> al, int left, int right) {

        // **** base condition ****
        if (left > right)
            return null;

        // **** initialization ****
        int mid = (left + right) / 2;

        // **** ****
        TreeNode root = al.get(mid);

        // **** process left side ****
        root.left = populateBST(al, left, mid - 1);

        // **** process right side ****
        root.right = populateBST(al, mid + 1, right);

        // **** return root of balanced BST ****
        return root;
    }


    /**
     * Given a binary search tree (BST), return a balanced BST 
     * with the same node values.
     * 
     * Runtime: 2 ms, faster than 99.69% of Java online submissions.
     * Memory Usage: 41.2 MB, less than 88.60% of Java online submissions.
     */
    static TreeNode balanceBST2(TreeNode root) {

        // **** initialization ****
        List<TreeNode> al = new ArrayList<>();

        // **** populate array list by performing an in order traversal of the BST O(n) ****
        fillALInOrder(root, al);

        // ???? ????
        // System.out.println("balanceBST2 <<<  al: " + al.toString());

        // // **** declare and fill array with results of in order traversal O(n) ****
        // int[] arr = al.stream().mapToInt(Integer::valueOf).toArray();

        // ???? ????
        // System.out.println("balanceBST2 <<< arr: " + Arrays.toString(arr));

        // **** populate and return balanced BST O(n) ****
        return populateBST(al, 0, al.size() - 1);
    }


    /**
     * Test scaffolding
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // **** read input line and generate String[] ****
        String[] strArr = br.readLine().trim().split(",");

        // **** close buffered reader ****
        br.close();

        // **** generate Integer[] array from the strArr[] ****
        Integer[] arr = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals("null"))
                arr[i] = null;
            else
                arr[i] = Integer.parseInt(strArr[i]);
        }

        // ???? ????
        System.out.println("main <<< arr:" + Arrays.toString(arr));

        // **** create and populate a BST ****
        BST bst = new BST();
        bst.root = bst.populate(arr);

        // ???? ????
        System.out.println("main <<< bst levelOrder:");
        System.out.println(bst.levelOrder());


        // **** balance this BST ****
        BST avl = new BST();
        avl.root = balanceBST(bst.root);

        // **** display the avl BST ****
        System.out.println("main <<< avl levelOrder:");
        System.out.println(avl.levelOrder());

        // **** in order traversal of avl BST ****
        System.out.print("main <<< avl inOrder: ");
        avl.inOrder(avl.root);
        System.out.println();


        // **** balance this BST ****
        BST balanced = new BST();
        balanced.root = balanceBST2(bst.root);

        // **** display level order traversal for balanced tree  ****
        System.out.println("main <<< balanced levelOrder:");
        System.out.println(balanced.levelOrder());

        // **** in order balanced tree ****
        System.out.print("main <<< balanced inOrder: ");
        balanced.inOrder(balanced.root);
        System.out.println();
    }
}