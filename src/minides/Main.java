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
     * plik zawierający analize
     */
    private static final String FILE_ANALYZE = "analyze.txt";
    /**
     *
     */
    private static final String COMMANDS = "Możliwe wywołania:\n java - jar " +
	    "minides.jar -[e|d|a]\n\n" +
	    " \t-e\tszyfrowanie\n" +
	    " \t-d\todszyfrowywanie\n" +
	    " \t-a\tanaliza dzialania programu\n ";

    private static final String PERMROZ = "12434356";
    private static String KEY;

    private static String[] S1 = {"101", "010", "001", "110", "011", "100", 
    "111", "000", "001", "100", "110", "010", "000", "111", "101", "011"};
    private static String[] S2 = {"100", "000", "110", "101", "111", "001",
    "011", "010", "101", "011", "000", "111", "110", "010", "001", "100"};
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (1 > args.length) {
	    System.out.println("Brak parametru wejsciowego.\n" + COMMANDS);
	    return;
	}

	String optionFunction = args[0];
	KEY = FileUtils.readFile(FILE_KEY).get(0);
	if (optionFunction.equals("-e")) {
	    System.out.println("Szyfrowanie tekstu do pliku - " + FILE_CRYPT);
	    FileUtils.clearFile(FILE_CRYPT);
	    FileUtils.writeFile(FILE_CRYPT, encrypt(FileUtils.readFile(FILE_PLAIN).get(0)), true);
	} else if (optionFunction.equals("-d")) {
	    System.out.println("Rozszyfrowywanie tekstu do pliku - " + FILE_DECRYPT);
	    FileUtils.clearFile(FILE_DECRYPT);
	    FileUtils.writeFile(FILE_DECRYPT, decrypt(FileUtils.readFile(FILE_CRYPT).get(0)), true);
	} else if (optionFunction.equals("-a")) {
	    System.out.println("Przygotowywanie analizy do pliku - " + FILE_ANALYZE);
	    FileUtils.clearFile(FILE_ANALYZE);
	    FileUtils.writeFile(FILE_ANALYZE, analyze(FileUtils.readFile(FILE_PLAIN).get(0)), true);
	}
    }

    public static String analyze(String plain) {

	StringBuilder result = new StringBuilder();
	String[] inStrings = plain.split(" ");
	result.append(inStrings[0] + "\t" + inStrings[1] + "\t" + xorBits(inStrings[0], inStrings[1]) + "\n");

	String[] L1 = new String[9];
	String[] R1 = new String[9];
	L1[0] = inStrings[0].substring(0, inStrings[0].length()/2);
	R1[0] = inStrings[0].substring(inStrings[0].length()/2, inStrings[0].length());


	String[] L2 = new String[9];
	String[] R2 = new String[9];
	L2[0] = inStrings[1].substring(0, inStrings[1].length()/2);
	R2[0] = inStrings[1].substring(inStrings[1].length()/2, inStrings[1].length());

	for (int i = 1; i <= 8; i++) {
	    L1[i] = R1[i-1];
	    R1[i] = xorBits(L1[i-1], fFunction(xorBits(eFunction(R1[i-1]), rotKey(KEY, i))));

	    L2[i] = R2[i-1];
	    R2[i] = xorBits(L2[i-1], fFunction(xorBits(eFunction(R2[i-1]), rotKey(KEY, i))));

	    result.append(L1[i]+R1[i] + "\t" + L2[i]+R2[i] + "\t" + xorBits(L1[i]+R1[i], L2[i]+R2[i]) + "\n");
	}

	return result.toString();
    }

    public static String decrypt(String plain) {

	String[] L = new String[9];
	String[] R = new String[9];
	L[0] = plain.substring(0, plain.length()/2);
	R[0] = plain.substring(plain.length()/2, plain.length());

	for (int i = 1; i <= 8; i++) {
	    L[i] = R[i-1];
	    R[i] = xorBits(L[i-1], fFunction(xorBits(eFunction(R[i-1]), rotKey(KEY, 8-(i-1)))));
	}

	return R[8]+L[8];
    }

    public static String encrypt(String plain) {
	plain = plain.split(" ")[0];
	String[] L = new String[9];
	String[] R = new String[9];
	L[0] = plain.substring(0, plain.length()/2);
	R[0] = plain.substring(plain.length()/2, plain.length());

	for (int i = 1; i <= 8; i++) {
	    L[i] = R[i-1];
	    R[i] = xorBits(L[i-1], fFunction(xorBits(eFunction(R[i-1]), rotKey(KEY, i))));
	}

	return R[8]+L[8];
    }

    private static String fFunction(String in) {
	int s1Index =  Integer.parseInt(in.substring(0, in.length()/2), 2);
	int s2Index =  Integer.parseInt(in.substring(in.length()/2, in.length()), 2);
	return S1[s1Index] + S2[s2Index];
    }

    private static String rotKey(String key, int i) {
	StringBuilder result = new StringBuilder();
	result.append(key.substring(i, key.length()));
	result.append(key.substring(0, i));
	return result.toString();
    }

    private static String eFunction(String in) {
	StringBuilder result = new StringBuilder();
	for (int i = 0; i < PERMROZ.length(); i++) {
	    result.append(in.charAt(Integer.parseInt(PERMROZ.substring(i, i+1))-1));
	}
	return result.toString();
    }

    private static String xorBits(String a, String b) {
	StringBuilder result = new StringBuilder();
	for (int i = 0; i < a.length(); i++) {
	    result.append((int) a.charAt(i) ^ (int) b.charAt(i));
	}
	return result.toString();
    }

    private static String byteToBits(byte b) {
	System.out.println((int)b);
	StringBuffer buf = new StringBuffer();
	for (int i = 0; i<8; i++) {
	    buf.append((int)(b>>(8-(i+1)) & 0x0001));
	}
	return buf.toString();
    }


}
