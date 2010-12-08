package minides;

/**
 * @author Orest Hrycyna
 */
public class Main {
    /**
     * plik z tekstem jawnym
     */
    private static final String FILE_PLAIN = "plain.txt";
    /**
     * plik z tekstem zaszyfrowanym
     */
    private static final String FILE_CRYPT = "crypto.txt";
    /**
     * plik z tekstem rozszyfrowanym
     */
    private static final String FILE_DECRYPT = "decrypto.txt";
    /**
     * plik zawierający klucz
     */
    private static final String FILE_KEY = "key.txt";
    /**
     *
     */
    private static final String COMMANDS = "Możliwe wywołania:\n java - jar " +
	    "minides.jar -[e|d|a]\n\n" +
	    " \t-e\tszyfrowanie\n" +
	    " \t-d\tprzygotowanie tekstu jawnego do szyfrowania\n" +
	    " \t-a\tszyfrowanie/odszyfrowywanie\n ";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (1 > args.length) {
	    System.out.println("Brak parametru wejsciowego.\n" + COMMANDS);
	    return;
	}

	String optionFunction = args[0];
	if (optionFunction.equals("-e")) {
	    System.out.println("Szyfrowanie tekstu do pliku - " + FILE_PLAIN);
	    FileUtils.clearFile(FILE_CRYPT);
	    FileUtils.writeFile(FILE_CRYPT, encrypt(FileUtils.readFile(FILE_PLAIN).get(0)), true);
	} /*else if (optionFunction.equals("-d")) {
	    System.out.println("Kodowanie tekstu do pliku - " + FILE_CRYPT);
            getRotorsAndSets();
	    clearFile(FILE_CRYPT);
	    for (String line : readFile(FILE_PLAIN)) {
		writeFile(FILE_CRYPT, encode(line) + "\n", true);
            }
	} else if (optionFunction.equals("-a")) {
	    System.out.println("Przygotowywanie trojek do pliku - " + FILE_CRYPT);
	    clearFile(FILE_CRYPT);
	    for (String line : readFile(FILE_PLAIN)) {
		getRotorsAndSets();
		writeFile(FILE_CRYPT, encode(line) + "\n", true);
            }
	}*/
    }

    public static String encrypt(String plain) {
	byte[] line = plain.getBytes();
	printBytes(line, plain);
	return "";
    }

    private static void printBytes(byte[] data, String name) {
	System.out.println("");
	System.out.println(name+":");
	for (int i=0; i<data.length; i++) {
	    System.out.print(byteToBits(data[i])+" ");
	}
	System.out.println();
    }

    private static String byteToBits(byte b) {
	StringBuffer buf = new StringBuffer();
	for (int i = 0; i<8; i++) {
	    buf.append((int)(b>>(8-(i+1)) & 0x0001));
	}
	return buf.toString();
    }


}
