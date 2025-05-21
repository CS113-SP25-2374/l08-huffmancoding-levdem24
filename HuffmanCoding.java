import java.util.PriorityQueue;

public class HuffmanCoding implements HuffmanInterface {

    private Node root;
    private String[] codes;  // Array indexed by ASCII code to store bitstring for each char

    private class Node implements Comparable<Node> {
        char ch;
        int freq;
        Node left, right;

        Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        Node(Node left, Node right) {
            this.ch = '\0'; // Internal node
            this.freq = left.freq + right.freq;
            this.left = left;
            this.right = right;
        }

        boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node other) {
            return this.freq - other.freq;
        }
    }

    // Build frequency table for characters in message
    private int[] buildFrequency(String message) {
        int[] freq = new int[128]; // ASCII range
        for (char c : message.toCharArray()) {
            freq[c]++;
        }
        return freq;
    }

    // Build Huffman tree using frequencies
    private Node buildTree(int[] freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (char i = 0; i < freq.length; i++) {
            if (freq[i] > 0) {
                pq.add(new Node(i, freq[i]));
            }
        }

        // Edge case: if only one character, add dummy node so tree isn't just one leaf
        if (pq.size() == 1) {
            pq.add(new Node('\0', 0));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            pq.add(new Node(left, right));
        }
        return pq.poll();
    }

    // Recursive method to assign codes to each character in tree
    private void buildCodes(Node node, String code) {
        if (node.isLeaf()) {
            codes[node.ch] = code.length() > 0 ? code : "0"; // Single char edge case
            return;
        }
        buildCodes(node.left, code + "0");
        buildCodes(node.right, code + "1");
    }

    @Override
    public String encode(String message) {
        int[] freq = buildFrequency(message);
        root = buildTree(freq);
        codes = new String[128];
        buildCodes(root, "");

        StringBuilder encoded = new StringBuilder();
        for (char c : message.toCharArray()) {
            encoded.append(codes[c]);
        }
        return encoded.toString();
    }

    @Override
    public String decode(String codedMessage) {
        StringBuilder decoded = new StringBuilder();
        Node current = root;
        for (int i = 0; i < codedMessage.length(); i++) {
            char bit = codedMessage.charAt(i);
            if (bit == '0') {
                current = current.left;
            } else if (bit == '1') {
                current = current.right;
            } else {
                throw new IllegalArgumentException("Encoded message contains invalid characters");
            }
            if (current.isLeaf()) {
                decoded.append(current.ch);
                current = root;
            }
        }
        return decoded.toString();
    }
}