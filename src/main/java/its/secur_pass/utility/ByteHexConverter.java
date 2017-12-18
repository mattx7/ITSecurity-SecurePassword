package its.secur_pass.utility;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import static javax.xml.bind.DatatypeConverter.printHexBinary;

public class ByteHexConverter {

    private ByteHexConverter() {
    }

    public static String convert(byte[] bytes) {
        return printHexBinary(bytes);
    }

    public static byte[] convert(String string) {
        return parseHexBinary(string);
    }
}
