public class Main {
    public static void main(String[] args) {
        HuffmanInterface huffmanCoding = new HuffmanCoding();

        String message = "Your Name Goes Here";

        String encoded = huffmanCoding.encode(message);
        System.out.println(encoded);

        String decoded = huffmanCoding.decode(encoded);
        System.out.println(decoded);
    }
}