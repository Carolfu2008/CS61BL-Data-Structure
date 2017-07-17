import java.util.*;

/**
 * A BinaryTree is a tree with nodes that have up to two children.
 */
public class BinaryTree {

    /**
     * root is the root of this BinaryTree
     */
    private TreeNode root;

    private ArrayList alreadySeen = new ArrayList();

    /**
     * The BinaryTree constructor
     */
    public BinaryTree() {
        root = null;
    }

    public BinaryTree(TreeNode t) {
        root = t;
    }

    public TreeNode getRoot() {
        return root;
    }

    /**
     * Print the values in the tree in preorder: root value first, then values
     * in the left subtree (in preorder), then values in the right subtree
     * (in preorder).
     */
    public void printPreorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            root.printPreorder();
            System.out.println();
        }
    }

    /**
     * Print the values in the tree in inorder: values in the left subtree
     * first (in inorder), then the root value, then values in the first
     * subtree (in inorder).
     */
    public void printInorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            root.printInorder();
            System.out.println();
        }
    }

    /**
     * Fills this BinaryTree with values a, b, and c.
     * DO NOT MODIFY THIS METHOD.
     */
    public void fillSampleTree1() {
        root = new TreeNode("a", new TreeNode("b"), new TreeNode("c"));
    }

    /**
     * Fills this BinaryTree with values a, b, and c, d, e, f.
     * DO NOT MODIFY THIS METHOD.
     */
    public void fillSampleTree2() {
        root = new TreeNode("a", new TreeNode("b", new TreeNode("d",
                new TreeNode("e"), new TreeNode("f")), null), new TreeNode("c"));
    }

    /**
     * Fills this BinaryTree with the values a, b, c, d, e, f in the way that the spec pictures.
     */
    public void fillSampleTree3() {
        root = new TreeNode("a",
                new TreeNode("b"),
                new TreeNode("c",
                        new TreeNode("d",
                                new TreeNode("e"),
                                new TreeNode("f")),
                        null));
    }

    /**
     * Fills this BinaryTree with the same leaf TreeNode.
     * DO NOT MODIFY THIS METHOD.
     */
    public void fillSampleTree4() {
        TreeNode leafNode = new TreeNode("c");
        root = new TreeNode("a", new TreeNode("b", leafNode, leafNode), new TreeNode("d", leafNode, leafNode));
    }

    /**
     * Like the Amoeba class, returns the height of the deepest node.
     **/
    public int height() {
        if (root != null) {
            return root.height();
        }
        return 0;
    }

    public boolean isCompletelyBalanced() {
        if (root == null||root.height()==1) {
            return true;
        }
        return root.left.height() == root.right.height();
    }

    public boolean check() {
        try {
            isOK(root);
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    private void isOK(TreeNode t) throws IllegalStateException {
        if (alreadySeen.contains(t)) {
            throw new IllegalStateException("Error");
        }
        alreadySeen.add(t);
        if (t.left != null) {
            isOK(t.left);
        }
        if (t.right != null) {
            isOK(t.right);
        }
    }

    public static BinaryTree fibTree(int n) {
        BinaryTree result = new BinaryTree();
        if (n <= 1) {
            result.root = new TreeNode(n);
        } else {
            TreeNode left = fibTree(n - 1).root;
            TreeNode right = fibTree(n - 2).root;
            result.root = new TreeNode((int)left.item + (int)right.item, left, right);
        }
        return result;
    }

    public static BinaryTree exprTree(String s) {
        BinaryTree result = new BinaryTree();
        result.root = result.exprTreeHelper(s);
        return result;
    }

    private TreeNode exprTreeHelper(String expr) {
        if (expr.charAt(0) != '(') {
            if (Character.isLetter(expr.charAt(0))) {
                return new TreeNode(expr.charAt(0));
            } else {
                return new TreeNode(new Integer(Character.getNumericValue(expr.charAt(0))));
            }// you fill this in
        } else {
            // expr is a parenthesized expression.
            // Strip off the beginning and ending parentheses,
            // find the main operator (an occurrence of + or * not nested
            // in parentheses, and construct the two subtrees.
            int nesting = 0;
            int opPos = 0;
            for (int k = 1; k < expr.length() - 1; k++) {
                if (expr.charAt(k) == '(') {
                    nesting++;
                } else if (expr.charAt(k) == ')') {
                    nesting--;
                } else if (nesting == 0 && (expr.charAt(k) == '+'||expr.charAt(k) == '*')) {
                    opPos = k;
                }
            }
            String opnd1 = expr.substring(1, opPos);
            String opnd2 = expr.substring(opPos + 1, expr.length() - 1);
            String op = expr.substring(opPos, opPos + 1);
            System.out.println("expression = " + expr);
            System.out.println("operand 1  = " + opnd1);
            System.out.println("operator   = " + op);
            System.out.println("operand 2  = " + opnd2);
            System.out.println();
            return new TreeNode(op, exprTreeHelper(opnd1), exprTreeHelper(opnd2));
        }
    }

    public void optimize() {
        optimizeHelper(root);
    }

    private void optimizeHelper(TreeNode node) {
        if (node.left != null) {
            optimizeHelper(node.left);
        } if (node.right != null) {
            optimizeHelper(node.right);
        } if (node.item instanceof String) {
            if (node.left.item instanceof Integer && node.right.item instanceof Integer) {
                String op = (String) node.item;
                Integer left_comp = (Integer)node.left.item;
                Integer right_comp = (Integer)node.right.item;
                if (op.charAt(0) == '+') {
                    node.item = new Integer(left_comp+right_comp).toString();
                } else if (op.charAt(0) == '*') {
                    node.item = new Integer(left_comp*right_comp).toString();
                }
                node.right = null;
                node.left = null;
                return;
            }
        }
    }

    /**
     * Creates two BinaryTrees and prints them out in inorder
     */
    public static void main(String[] args) {
        BinaryTree t;
        t = new BinaryTree();
        print(t, "the empty tree");
        t.fillSampleTree1();
        print(t, "sample tree 1");
        t.fillSampleTree2();
        print(t, "sample tree 2");
    }

    /**
     * Prints out the contents of a BinaryTree with a description in both
     * preorder and inorder
     *
     * @param t           the BinaryTree to print out
     * @param description a String describing the BinaryTree t
     */
    private static void print(BinaryTree t, String description) {
        System.out.println(description + " in preorder");
        t.printPreorder();
        System.out.println(description + " in inorder");
        t.printInorder();
        System.out.println();
    }

    /**
     * A TreeNode is a Node this BinaryTree
     * Note: this class is public in this lab for testing purposes.
     * However, in professional settings as well as the rest of
     * your labs and projects, we recommend that you keep your
     * classes private.
     */
    public static class TreeNode {

        /**
         * item is the item that is contained in this TreeNode
         * left is the left child of this TreeNode
         * right is the right child of this TreeNode
         */
        public Object item;
        public TreeNode left;
        public TreeNode right;

        /**
         * A TreeNode constructor that creates a node with obj as its item
         *
         * @param obj the item to be contained in this TreeNode
         */
        TreeNode(Object obj) {
            item = obj;
            left = null;
            right = null;
        }

        /**
         * A TreeNode constructor that creates a node with obj as its item and
         * left and right as its children
         *
         * @param obj   the item to be contained in this TreeNode
         * @param left  the left child of this TreeNode
         * @param right the right child of this TreeNode
         */
        TreeNode(Object obj, TreeNode left, TreeNode right) {
            item = obj;
            this.left = left;
            this.right = right;
        }

        public Object getItem() {
            return item;
        }

        public TreeNode getLeft() {
            return left;
        }

        public TreeNode getRight() {
            return right;
        }

        /**
         * Prints this TreeNode and the subtree rooted at it in preorder
         */
        private void printPreorder() {
            System.out.print(item + " ");
            if (left != null) {
                left.printPreorder();
            }
            if (right != null) {
                right.printPreorder();
            }
        }

        /**
         * Prints this TreeNode and the subtree rooted at it in inorder
         */
        private void printInorder() {
            if (left != null) {
                left.printInorder();
            }
            System.out.print(item + " ");
            if (right != null) {
                right.printInorder();
            }
        }

        public int height() {
            if (this.left == null & this.right == null) {
                return 1;
            } else if (this.left == null) {
                return 1 + right.height();
            } else if (this.right == null) {
                return 1 + left.height();
            } else {
                return 1 + Math.max(left.height(), right.height());
            }
        }
    }
}
