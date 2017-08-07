import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by lifesaver on 04/08/2017.
 */
public class TernaryTrie {

    private char blank = 2017;

    private TTNode root;

    public TernaryTrie() {
        root = new TTNode();
    }

    public void insert(String terms, double weight) {
        root.insert(terms, weight);
    }

    public boolean find(String terms) {
        return root.find(terms);
    }

    public double checkWeightOf(String terms) {
        return root.check(terms);
    }

    public List<String> topMatches(String prefix, int k) {
        List<String> found = new ArrayList<String>();
        List<String> reverseFound = new ArrayList<String>();
        TTNode rootNode = root;
        if (prefix.length() != 0) {
            rootNode = root.findNode(prefix, 0);
        }
        if (rootNode == null) {
            return found;
        }
        if (rootNode.middle == null) {
            if (rootNode.nodeWeight != -1) {
                found.add(prefix);
                return found;
            }
            return found;
        }
        PriorityQueue<TTNode> pqTops = new PriorityQueue<TTNode>(10, new LeastNodeComparator());
        PriorityQueue<TTNode> pqPointer = new PriorityQueue<TTNode>(10, new BestSubComparator());
        if (prefix.length() != 0) {
            if (rootNode.nodeWeight != -1) {
                pqTops.add(rootNode);
            }
            pqPointer.add(rootNode.middle);
        } else {
            pqPointer.add(rootNode);
        }
        while (pqPointer.size() != 0) {
            TTNode tmp = pqPointer.poll();
            if (pqTops.size() != 0) {
                if (pqTops.peek().nodeWeight > tmp.maxSubTries) {
                    if (pqTops.size() >= k) {
                        break;
                    }
                }
            }
            if (tmp.wholeTerm != null) {
                pqTops.add(tmp);
                if (pqTops.size() > k) {
                    pqTops.poll();
                }
            }
            if (tmp.left != null) {
                pqPointer.add(tmp.left);
            }
            if (tmp.middle != null) {
                pqPointer.add(tmp.middle);
            }
            if (tmp.right != null) {
                pqPointer.add(tmp.right);
            }
        }
        while (pqTops.size() != 0) {
            reverseFound.add(pqTops.poll().wholeTerm);
        }
        for (int i = 0; i < reverseFound.size() && i < k; i++) {
            int count = reverseFound.size() - 1 - i;
            found.add(reverseFound.get(count));
        }
        return found;
    }


    class BestSubComparator implements Comparator<TTNode> {
        public int compare(TTNode e1, TTNode e2) {
            return (int) (e2.maxSubTries - e1.maxSubTries);
        }
    }

    class LeastNodeComparator implements Comparator<TTNode> {
        public int compare(TTNode e1, TTNode e2) {
            return (int) -(e2.nodeWeight - e1.nodeWeight);
        }
    }

    class TTNode {
        private TTNode left;
        private TTNode middle;
        private TTNode right;
        private char key = blank;
        private double nodeWeight = -1;
        private double maxSubTries = -1;
        private String wholeTerm = null;

        TTNode() {
            left = null;
            middle = null;
            right = null;
            key = blank;
        }

        public void insert(String terms, double weight) {
            insertHelper(terms, weight, 0);
        }

        public void insertHelper(String terms, double weight, int digit) {
            char curr = terms.charAt(digit);
            if (key == blank) {
                key = curr;
            }
            if (maxSubTries < weight) {
                maxSubTries = weight;
            }
            if (key == curr) {
                digit = digit + 1;
                if (terms.length() == digit) {
                    if (nodeWeight == -1) {
                        nodeWeight = weight;
                        wholeTerm = terms;
                        return;
                    } else {
                        throw new IllegalArgumentException("There are duplicate input terms.");
                    }
                } else if (middle == null) {
                    middle = new TTNode();
                }
                middle.insertHelper(terms, weight, digit);
            } else if (key > curr) {
                if (left == null) {
                    left = new TTNode();
                }
                left.insertHelper(terms, weight, digit);
            } else {
                if (right == null) {
                    right = new TTNode();
                }
                right.insertHelper(terms, weight, digit);
            }
        }

        public boolean find(String terms) {
            return findHelper(terms, 0);
        }

        public boolean findHelper(String terms, int digit) {
            char curr = terms.charAt(digit);
            if (terms.length() == digit + 1) {
                if (key != blank && key == curr && nodeWeight != -1) {
                    return true;
                } else if (key != blank && key > curr && left != null) {
                    return left.findHelper(terms, digit);
                } else if (key != blank && key < curr && right != null) {
                    return right.findHelper(terms, digit);
                }

                return false;
            } else if (key != blank) {
                if (key == curr) {
                    if (middle != null) {
                        return middle.findHelper(terms, digit + 1);
                    }
                    return false;
                } else if (key > curr) {
                    if (left != null) {
                        return left.findHelper(terms, digit);
                    }
                    return false;
                } else {
                    if (right != null) {
                        return right.findHelper(terms, digit);
                    }
                    return false;
                }
            } else {
                return false;
            }
        }

        public double check(String terms) {
            return checkHelper(terms, 0);
        }

        public double checkHelper(String terms, int digit) {
            char curr = terms.charAt(digit);
            if (terms.length() == digit + 1) {
                if (curr == key) {
                    return nodeWeight;
                } else if (key > curr) {
                    return left.checkHelper(terms, digit);
                } else {
                    return right.checkHelper(terms, digit);
                }
            } else if (key == curr) {
                return middle.checkHelper(terms, digit + 1);
            } else if (key > curr) {
                return left.checkHelper(terms, digit);
            } else {
                return right.checkHelper(terms, digit);
            }
        }

        public TTNode findNode(String prefix, int digit) {
            char curr = prefix.charAt(digit);
            if (prefix.length() == digit + 1) {
                if (key != blank && key == curr) {
                    return this;
                } else if (key > curr) {
                    return left.findNode(prefix, digit);
                } else {
                    return right.findNode(prefix, digit);
                }
            } else if (key != blank) {
                if (key == curr) {
                    if (middle != null) {
                        return middle.findNode(prefix, digit + 1);
                    }
                    return null;
                } else if (key > curr) {
                    if (left != null) {
                        return left.findNode(prefix, digit);
                    }
                    return null;
                } else {
                    if (right != null) {
                        return right.findNode(prefix, digit);
                    }
                    return null;
                }
            } else {
                return null;
            }
        }
    }
}

