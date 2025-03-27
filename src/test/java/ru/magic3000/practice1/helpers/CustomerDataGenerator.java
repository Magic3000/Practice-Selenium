package ru.magic3000.practice1.helpers;

public class CustomerDataGenerator {
    static final String LAST_NAME = "last_name";

    public static String cachedFirstName;

    public static String getFirstName(String postCode) {
        StringBuilder firstName = new StringBuilder();
        for (int i = 0; i < postCode.length(); i += 2) {
            int number = Integer.parseInt(postCode.substring(i, Math.min(i + 2, postCode.length())));
            number = number % 26;
            firstName.append((char)('a' + number));
        }
        cachedFirstName = firstName.toString();
        return cachedFirstName;
    }

    public static String getLastName() {
        return LAST_NAME;
    }

    public static String getPostCode() {
        return String.valueOf((long)(Math.random() * 1_000_000_0000L));
    }
}
