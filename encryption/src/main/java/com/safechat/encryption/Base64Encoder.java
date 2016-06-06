package com.safechat.encryption;

import java.util.Arrays;

public class Base64Encoder {

    private Base64Encoder() {
    }


    private static final byte[] newline = null;
    private static final int linemax = -1;


    /**
     * This array is a lookup table that translates 6-bit positive integer
     * index values into their "Base64Encoder Alphabet" equivalents as specified
     * in "Table 1: The Base64Encoder Alphabet" of RFC 2045 (and RFC 4648).
     */
    private static final char[] toBase64 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
    };

    private static int outLength(int srclen) {
        int len = 0;
        len = 4 * ((srclen + 2) / 3);
        return len;
    }

    /**
     * Encodes all bytes from the specified byte array into a newly-allocated
     * byte array using the {@link Base64Encoder} encoding scheme. The returned byte
     * array is of the length of the resulting bytes.
     *
     * @param src the byte array to encode
     * @return A newly-allocated byte array containing the resulting
     * encoded bytes.
     */
    public static byte[] encode(byte[] src) {
        int len = outLength(src.length);          // dst array size
        byte[] dst = new byte[len];
        int ret = encode0(src, 0, src.length, dst);
        if (ret != dst.length)
            return Arrays.copyOf(dst, ret);
        return dst;
    }

    private static int encode0(byte[] src, int off, int end, byte[] dst) {
        char[] base64 = toBase64;
        int sp = off;
        int slen = (end - off) / 3 * 3;
        int sl = off + slen;
        int dp = 0;
        while (sp < sl) {
            int sl0 = Math.min(sp + slen, sl);
            for (int sp0 = sp, dp0 = dp; sp0 < sl0; ) {
                int bits = (src[sp0++] & 0xff) << 16 |
                        (src[sp0++] & 0xff) << 8 |
                        (src[sp0++] & 0xff);
                dst[dp0++] = (byte) base64[(bits >>> 18) & 0x3f];
                dst[dp0++] = (byte) base64[(bits >>> 12) & 0x3f];
                dst[dp0++] = (byte) base64[(bits >>> 6) & 0x3f];
                dst[dp0++] = (byte) base64[bits & 0x3f];
            }
            int dlen = (sl0 - sp) / 3 * 4;
            dp += dlen;
            sp = sl0;
            if (dlen == linemax && sp < end) {
                for (byte b : newline) {
                    dst[dp++] = b;
                }
            }
        }
        if (sp < end) {               // 1 or 2 leftover bytes
            int b0 = src[sp++] & 0xff;
            dst[dp++] = (byte) base64[b0 >> 2];
            if (sp == end) {
                dst[dp++] = (byte) base64[(b0 << 4) & 0x3f];
                dst[dp++] = '=';
                dst[dp++] = '=';
            } else {
                int b1 = src[sp++] & 0xff;
                dst[dp++] = (byte) base64[(b0 << 4) & 0x3f | (b1 >> 4)];
                dst[dp++] = (byte) base64[(b1 << 2) & 0x3f];
                dst[dp++] = '=';
            }
        }
        return dp;
    }


    private static final int[] fromBase64 = new int[256];

    static {
        Arrays.fill(fromBase64, -1);
        for (int i = 0; i < toBase64.length; i++)
            fromBase64[toBase64[i]] = i;
        fromBase64['='] = -2;
    }

    public static byte[] decode(String src) {
        return decode(src.getBytes());
    }

    public static byte[] decode(byte[] src) {
        byte[] dst = new byte[outLength(src, 0, src.length)];
        int ret = decode0(src, 0, src.length, dst);
        if (ret != dst.length) {
            dst = Arrays.copyOf(dst, ret);
        }
        return dst;
    }

    private static int outLength(byte[] src, int sp, int sl) {
        int paddings = 0;
        int len = sl - sp;
        if (len == 0)
            return 0;
        if (len < 2) {
            throw new IllegalArgumentException(
                    "Input byte[] should at least have 2 bytes for base64 bytes");
        }
        if (src[sl - 1] == '=') {
            paddings++;
            if (src[sl - 2] == '=')
                paddings++;
        }
        if (paddings == 0 && (len & 0x3) != 0)
            paddings = 4 - (len & 0x3);
        return 3 * ((len + 3) / 4) - paddings;
    }

    private static int decode0(byte[] src, int sp, int sl, byte[] dst) {
        int[] base64 = fromBase64;
        int dp = 0;
        int bits = 0;
        int shiftto = 18;       // pos of first byte of 4-byte atom
        while (sp < sl) {
            int b = src[sp++] & 0xff;
            if ((b = base64[b]) < 0) {
                if (b == -2) {         // padding byte '='
                    // =     shiftto==18 unnecessary padding
                    // x=    shiftto==12 a dangling single x
                    // x     to be handled together with non-padding case
                    // xx=   shiftto==6&&sp==sl missing last =
                    // xx=y  shiftto==6 last is not =
                    if (shiftto == 6 && (sp == sl || src[sp++] != '=') ||
                            shiftto == 18) {
                        throw new IllegalArgumentException(
                                "Input byte array has wrong 4-byte ending unit");
                    }
                    break;
                }
                throw new IllegalArgumentException(
                        "Illegal base64 character " +
                                Integer.toString(src[sp - 1], 16));
            }
            bits |= (b << shiftto);
            shiftto -= 6;
            if (shiftto < 0) {
                dst[dp++] = (byte) (bits >> 16);
                dst[dp++] = (byte) (bits >> 8);
                dst[dp++] = (byte) (bits);
                shiftto = 18;
                bits = 0;
            }
        }
        // reached end of byte array or hit padding '=' characters.
        if (shiftto == 6) {
            dst[dp++] = (byte) (bits >> 16);
        } else if (shiftto == 0) {
            dst[dp++] = (byte) (bits >> 16);
            dst[dp++] = (byte) (bits >> 8);
        } else if (shiftto == 12) {
            // dangling single "x", incorrectly encoded.
            throw new IllegalArgumentException(
                    "Last unit does not have enough valid bits");
        }
        // anything left is invalid, if is not MIME.
        // if MIME, ignore all non-base64 character
        while (sp < sl) {
            throw new IllegalArgumentException(
                    "Input byte array has incorrect ending byte at " + sp);
        }
        return dp;
    }
}
