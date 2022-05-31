package br.com.builders.basecliente.utils;

import java.io.UnsupportedEncodingException;

public class Encoding {
    public static final String LATIN_1 = "ISO-8859-1";
    public static final String UTF_8 = "UTF-8";
    public static final int RADIX_HEX = 16;

    private Encoding() {
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);

        for(int i = 0; i < b.length; ++i) {
            int v = b[i] & 255;
            if (v < 16) {
                sb.append('0');
            }

            sb.append(Integer.toHexString(v));
        }

        return sb.toString().toUpperCase();
    }

    public static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];

        for(int i = 0; i < b.length; ++i) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte)v;
        }

        return b;
    }

    public static final String utf8ToString(byte[] bytes) throws UnsupportedEncodingException {
        return bytes != null ? new String(bytes, "UTF-8") : null;
    }

    public static int modulo11(String numero) {
        int sum = 0;
        int coeficient = 2;

        for(int i = numero.length() - 1; i >= 0; --i) {
            int digit = Integer.parseInt(String.valueOf(numero.charAt(i)));
            sum += digit * coeficient;
            ++coeficient;
        }

        int dv = 11 - sum % 11;
        if (dv >= 10) {
            dv = 0;
        }

        return dv;
    }

    public static int modulo11(long numero) {
        return modulo11(String.valueOf(numero));
    }
}
