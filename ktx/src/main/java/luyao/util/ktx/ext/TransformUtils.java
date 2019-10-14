package luyao.util.ktx.ext;

/**
 * Created by luyao
 * on 2019/9/20 15:49
 */
public class TransformUtils {
    public static int bytes2Int(byte[] bytes) {
        if (null == bytes || bytes.length == 0) return 0;
        return (((bytes[0] & 0XFF) << 24)
                | ((bytes[1] & 0xFF) << 16)
                | ((bytes[2] & 0xFF) << 8)
                | (bytes[3] & 0xFF));
    }

    /**
     * int 转 byte[]
     */
    public static byte[] int2Bytes(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((i >> 24) & 0xFF);
        bytes[1] = (byte) ((i >> 16) & 0xFF);
        bytes[2] = (byte) ((i >> 8) & 0xFF);
        bytes[3] = (byte) (i & 0xFF);
        return bytes;
    }

    /**
     * byte[] 转 short
     */
    public static short bytes2Short(byte[] bytes) {
        if (null == bytes || bytes.length == 0) return 0;
        return (short) ((bytes[0] & 0xff) |
                ((bytes[1] & 0xff)) << 8);
    }

    /**
     * short 转 byte[]
     */
    public static byte[] short2Bytes(short s) {
        int temp = s;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.valueOf(temp & 0xff).byteValue();
            temp = temp >> 8;
        }
        return b;
    }

    /**
     * byte[] 转 long
     */
    public static long bytes2Long(byte[] bytes) {
        if (null == bytes || bytes.length == 0) return 0;
        long s0 = bytes[0] & 0xff;
        long s1 = bytes[1] & 0xff;
        long s2 = bytes[2] & 0xff;
        long s3 = bytes[3] & 0xff;
        long s4 = bytes[4] & 0xff;
        long s5 = bytes[5] & 0xff;
        long s6 = bytes[6] & 0xff;
        long s7 = bytes[7] & 0xff;
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        return s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
    }


    /**
     * long 转 byte[]
     */
    public static byte[] long2Bytes(long l) {
        long temp = l;
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = Long.valueOf(temp & 0xff).byteValue();
            temp = temp >> 8;
        }
        return b;
    }
}
