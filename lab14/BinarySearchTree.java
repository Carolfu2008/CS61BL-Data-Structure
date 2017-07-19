
/**
 * A class implementing a BST.
 *
 * @author CS 61BL Staff.
 */
public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /**
     * Constructs an empty BST.
     */
    public BinarySearchTree() {
        super();
    }

    /**
     * BST Constructor.
     *
     * @param root node to use for constructing new BST.
     */
    public BinarySearchTree(TreeNode root) {
        super(root);
    }

    /**
     * Method for checking if BST has a given key.
     *
     * @param key to search for
     * @return true if this BST contains key
     * false if this BST does not contain key
     */
    public boolean contains(T key) {
        return contains_helper(root, key);
    }

    public boolean contains_helper(TreeNode t, T key) {
        if (t == null) {
            return false;
        } else if (t.item.equals(key)) {
            return true;
        } else if (key.compareTo(t.item) < 0) {
            return contains_helper(t.left, key);
        } else {
            return contains_helper(t.right, key);
        }
    }


    /**
     * Adds a node for KEY iff it isn't in the BST already.
     *
     * @param key to be added
     */
    public void add(T key) {
        if (!contains(key)) {
            if (root != null) {
                add_helper(root, key);
            } else {
                root = new TreeNode(key);
            }
        }
    }

    public void add_helper(TreeNode t, T key) {
        if (t.item.compareTo(key) < 0) {
            if (t.right==null){
                t.right=new TreeNode(key);
            }else{
                add_helper(t.right,key);
            }
        }else{
            if (t.left==null){
                t.left=new TreeNode(key);
            }else{
                add_helper(t.left,key);
            }
        }
    }

    /**
     * Deletes a node from the BST.
     *
     * @param key to be deleted
     * @return key that was deleted
     */
    public T delete(T key) {
        TreeNode parent = null;
        TreeNode curr = root;
        TreeNode delNode = null;
        TreeNode replacement = null;
        boolean rightSide = false;

        while (curr != null && !curr.item.equals(key)) {
            if (((Comparable<T>) curr.item).compareTo(key) > 0) {
                parent = curr;
                curr = curr.left;
                rightSide = false;
            } else {
                parent = curr;
                curr = curr.right;
                rightSide = true;
            }
        }
        delNode = curr;
        if (curr == null) {
            return null;
        }

        if (delNode.right == null) {
            if (root == delNode) {
                root = root.left;
            } else {
                if (rightSide) {
                    parent.right = delNode.left;
                } else {
                    parent.left = delNode.left;
                }
            }
        } else {
            curr = delNode.right;
            replacement = curr.left;
            if (replacement == null) {
                replacement = curr;
            } else {
                while (replacement.left != null) {
                    curr = replacement;
                    replacement = replacement.left;
                }
                curr.left = replacement.right;
                replacement.right = delNode.right;
            }
            replacement.left = delNode.left;
            if (root == delNode) {
                root = replacement;
            } else {
                if (rightSide) {
                    parent.right = replacement;
                } else {
                    parent.left = replacement;
                }
            }
        }
        return delNode.item;
    }
}
