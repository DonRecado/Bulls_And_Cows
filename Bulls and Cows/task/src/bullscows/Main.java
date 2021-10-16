package bullscows;

import java.util.*;

public class Main {
    final static Scanner scanner = new Scanner(System.in);
    static boolean gameOver = false;
    final static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    static int length, symbols;

    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        String input = scanner.nextLine();
        if (setLength(input)) {
            System.out.println("Input the number of possible symbols in the code:");
            String inputSymbolsNumber = scanner.nextLine();

            if (setSymbols(inputSymbolsNumber)) {
                if (validateInput()) {
                    String secretCode = createSecretCode(length, symbols);
                    String text;
                    if (symbols <= 10) {
                        text = "The secret is prepared: " + "*".repeat(length) + "(0-9).";
                    } else if (symbols == 11) {
                        text = "The secret is prepared: " + "*".repeat(length) + "(0-9, a).";
                    } else {
                        char lastChar = alphabet[symbols - 11];
                        text = "The secret is prepared: " + "*".repeat(length) + "(0-9, a - " + lastChar + ").";
                    }
                    System.out.println(text);
                    System.out.println("Okay, let's start a game!");
                    while (!gameOver) {
                        String guess = scanner.nextLine();
                        if (guess.length() == length) {
                            System.out.println(processGuess(guess, secretCode));
                        }
                    }
                }
            }
        }
    }


    private static boolean setLength(String input) {
        try {
            length = Integer.parseInt(input);
            if(length > 0) {
                return true;
            } else {
                System.out.println("Error: \"" + input + "\" is not a valid number.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + input + "\" is not a valid number.");
            return false;
        }
    }

    private static boolean setSymbols(String input) {
        try {
            symbols = Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + input + "\" is not a valid number.");
            return false;
        }
    }

    private static boolean validateInput() {
        if (length > symbols) {
            System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " + symbols + " unique symbols");
            return false;
        }
        if (symbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return false;
        }
        return true;
    }


    private static String processGuess(String guess, String secret) {
        if (guess.equals(secret)) {
            gameOver = true;
            String name = guess.length() > 1 ? "bulls" : "bull";
            return "Grade: " + guess.length() + " " + name + "\nCongratulations! You guessed the secret code.";
        } else {
            int bulls = 0;
            int cows = 0;
            for (int i = 0; i < guess.length(); i++) {
                if (guess.charAt(i) == secret.charAt(i)) {
                    bulls++;
                }
                for (int j = 0; j < guess.length(); j++) {
                    if (j != i) {
                        if (guess.charAt(i) == secret.charAt(j)) {
                            cows++;
                        }
                    }
                }
            }
            String bullsName = bulls > 1 ? "bulls" : "bull";
            String cowsName = cows > 1 ? "cows" : "cow";


            if (bulls > 0 && cows > 0) {
                return "Grade: " + bulls + " " + bullsName + " and " + cows + " " + cowsName;
            } else if (bulls > 0) {
                return "Grade: " + bulls + " " + bullsName;
            } else if (cows > 0) {
                return "Grade: " + cows + " " + cowsName;
            } else {
                return "Grade: None";
            }


        }


    }

    private static String createSecretCode(int length, int symbols) {
        List<Integer> secretCode = new ArrayList<>();
        while (secretCode.size() < length) {
            Random random = new Random();
            int num = random.nextInt(symbols - 1);


            if (secretCode.size() == 0) {
                if (num != 0) {
                    secretCode.add(num);
                }
            } else {
                if (!secretCode.contains(num)) {
                    secretCode.add(num);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int num : secretCode) {
            if (num >= 9) {
                sb.append(alphabet[num - 10]);
            } else {
                sb.append(num);
            }
        }

        return sb.toString();
    }
}
