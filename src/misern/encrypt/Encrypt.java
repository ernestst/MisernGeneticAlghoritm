package misern.encrypt;

/**
 * The Encrypt class contains static methods for binary nad gray conversions
 * @author Michał Fiłończuk
 * @version 1.0
 */
public class Encrypt {
    /**
     * Converses integer value to binary form
     * @param value integer value to converse
     * @return string with converted value in minimal form (without left zeros)
     */
    public static String toBinary(int value) {
        return Integer.toBinaryString(value);
    }

    /**
     * Converses integer value to binary form
     * @param value integer value to converse
     * @param bits number of bits to expand binary form (with left zeros)
     * @return string with converted value in expanded form (with left zeros)
     */
    public static String toBinary(int value, int bits) {
        StringBuilder builder = new StringBuilder();

        String converted = toBinary(value);

        for(int i = 0; i < bits - converted.length(); i++) {
            builder.append("0");
        }

        builder.append(converted);

        return builder.toString();
    }

    /**
     * Converses integer value to gray form
     * @param value integer value to converse
     * @return string with converted value in minimal form (without left zeros)
     */
    public static String toGray(int value) {
        String binary = toBinary(value);
        StringBuilder gray = new StringBuilder();

        gray.append(binary.charAt(0));

        for (int i = 1; i < binary.length(); i++) {
            gray.append(xor_c(binary.charAt(i - 1), binary.charAt(i)));
        }

        return gray.toString();
    }

    /**
     * Converses integer value to gray form
     * @param value integer value to converse
     * @param bits number of bits to expand gray form (with left zeros)
     * @return string with converted value in expanded form (with left zeros)
     */
    public static String toGray(int value, int bits) {
        StringBuilder builder = new StringBuilder();

        String converted = toGray(value);

        for(int i = 0; i < bits - converted.length(); i++) {
            builder.append("0");
        }

        builder.append(converted);

        return builder.toString();
    }

    private static char xor_c(char a, char b) {
        return (a == b) ? '0' : '1';
    }
}
