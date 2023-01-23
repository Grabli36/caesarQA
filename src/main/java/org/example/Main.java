package org.example;

public class Main {
    public static void main(String[] args) {

        runProgram(args);

    }

    public static String encrypt(String message, int offset) {
        char[] chars = message.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (!Character.isLetter(c)) {
                continue;
            }

            int base = 'A';
            if (Character.UnicodeBlock.of(c).equals(Character.UnicodeBlock.CYRILLIC)) {
                base = 'А';
            }

            if (!Character.isUpperCase(c)) {
                base += 32;
            }
            c = (char) (((c - base + offset) % 26) + base);

            chars[i] = c;
        }
        return new String(chars);
    }

    public static String decrypt(String message, int offset) {
        return encrypt(message, 26 - (offset % 26));
    }

    public static void runProgram(String[] args) {

        String operation = args[0];
        String message = args[1];
        String key = args[3];

        if (!isValidArguments(args)) {
            return;
        }

        int keyValue = Integer.parseInt(key);

        switch (operation) {
            case "-e":
                System.out.println(encrypt(message, keyValue));
                break;
            case "-d":
                System.out.println(decrypt(message, keyValue));
                break;
            default:
                System.out.println("Invalid operation flag");
                break;
        }
    }

    public static boolean isValidArguments(String[] args) {
        if (args.length != 4) {
            System.out.println("Invalid number of arguments");
            return false;
        }

        String operation = args[0];
        String message = args[1];
        String offset = args[2];
        String key = args[3];

        if (message.trim().isEmpty()) {
            System.out.println("Message is empty");
            return false;
        }
        if (!offset.equals("-o")) {
            System.out.println("Invalid offset flag");
            return false;
        }

        try {
            int keyValue = Integer.parseInt(key);
            if (keyValue <= 0) {
                System.out.println("Key value must be greater than 0");
                return false;
            } else if (keyValue > 33) {
                System.out.println("Key value must be less than 33");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid key value");
            return false;
        }

        return true;
    }
}