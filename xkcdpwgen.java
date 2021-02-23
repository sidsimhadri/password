import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class xkcdpwgen {

  private static int words = 4;
  private static int caps = 0;
  private static int numbers = 0;
  private static int symbols = 0;

  public static void main(String args[]) throws IOException {

    for (int i = 0; i < args.length; i++) {
      if (args[i].compareToIgnoreCase("h") == 0 || args[i].compareToIgnoreCase("help") == 0) {
        System.out.print("    -w WORDS, --words WORDS\n"
            + "                          include WORDS words in the password (default=4)\n"
            + "    -c CAPS, --caps CAPS  capitalize the first letter of CAPS random words\n"
            + "                          (default=0)\n"
            + "    -n NUMBERS, --numbers NUMBERS\n"
            + "                          insert NUMBERS random numbers in the password\n"
            + "                          (default=0)\n"
            + "    -s SYMBOLS, --symbols SYMBOLS\n"
            + "                          insert SYMBOLS random symbols in the password\n"
            + "                          (default=0)");
      }
      if (args[i].compareToIgnoreCase("c") == 0 || args[i].compareToIgnoreCase("caps") == 0) {
        caps = Integer.parseInt(args[i + 1]);
        i++;
      }
      if (args[i].compareToIgnoreCase("n") == 0 || args[i].compareToIgnoreCase("numbers") == 0) {
        numbers = Integer.parseInt(args[i + 1]);
        i++;
      }
      if (args[i].compareToIgnoreCase("s") == 0 || args[i].compareToIgnoreCase("numbers") == 0) {
        symbols = Integer.parseInt(args[i + 1]);
        i++;
      }
      System.out.println(password(words, caps, numbers, symbols));
    }
  }

  public static String password(int words, int caps, int numbers, int symbols)
      throws IOException {
    if (caps > words) {
      throw new IllegalArgumentException();
    }
    String[] strings = new String[words];
    File list = new File("corncob_lowercase.txt");
    Scanner scanner = new Scanner(list);
    int i = 0;
    Random rand = new Random();
    Long l = list.length();
    int a = l.intValue();
    int n;
    String pwd = "";
    for (int j = 0; j < words; j++) {
      n = rand.nextInt(a);
      while (scanner.hasNextLine()) {
        String s = scanner.nextLine();
        if (i == n) {
          strings[j] = s;
        }
        i++;
      }
    }
    while (caps > 0) {
      n = rand.nextInt(words);
      String s = strings[n];
      boolean hasUppercase = !s.equals(s.toLowerCase());
      if (!hasUppercase) {
        strings[n] = s.substring(0, 1).toUpperCase() + s.substring(1);
        caps--;
      }
    }
    for (String m : strings) {
      pwd.concat(m);
    }
    int e = pwd.length();
    for (int num = 0; num < numbers; num++) {
      n = rand.nextInt(e);
      pwd = pwd.substring(0, n) + rand.nextInt(10) + pwd.substring(n);
    }
    String bols = "~!@#$%^&*.:;";

    for (int sym = 0; sym < symbols; sym++) {
      n = rand.nextInt(e);
      pwd = pwd.substring(0, n) + bols.charAt(rand.nextInt(bols.length())) + pwd.substring(n);
    }

    return pwd;
  }


}
