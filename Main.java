public class Main {
    public static void main(String[] args) {
        HuffmanInterface huffmanCoding = new HuffmanCoding();

        String message = "Levon";

        String encoded = huffmanCoding.encode(message);
        System.out.println("Encoded:\n" + encoded);

        String decoded = huffmanCoding.decode(encoded);
        System.out.println("Decoded:\n" + decoded);
    }
}
