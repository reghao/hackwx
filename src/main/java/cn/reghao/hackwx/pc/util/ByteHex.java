package cn.reghao.hackwx.pc.util;

/**
 * 二进制十六进制转换
 *
 * @author reghao
 * @date 2021-11-26 17:03:20
 */
public class ByteHex {
    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 字节数组转换为十六进制字符串
     *
     * @param
     * @return
     * @date 2021-11-26 下午5:25
     */
    public static String bytes2Hex(byte[] bytes) {
        StringBuilder hexStr = new StringBuilder();
        for (byte b : bytes) {
            int num = b < 0 ? 256 + b : b;
            hexStr.append(HEX[num/16]).append(HEX[num%16]);
        }

        return hexStr.toString();
    }

    /**
     * 十六进制字符串转换为字节数组
     *
     * @param
     * @return
     * @date 2021-11-26 下午5:25
     */
    public static byte[] hex2Bytes(String hex) {
        int len = hex.length();
        byte[] bytes = new byte[len/2];
        for (int i = 0; i < len/2; i++) {
            String tmp = hex.substring(i*2, i*2+2);
            bytes[i] = (byte) Integer.parseInt(tmp, 16);
        }

        return bytes;
    }

    /**
     * 两个相同长度字节数组的异或值
     *
     * @param
     * @return
     * @date 2021-11-26 下午5:26
     */
    public static byte[] xor(byte[] b1, byte[] b2) {
        if (b1.length != b2.length) {
            return new byte[0];
        }

        int len = b1.length;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = (byte) (b1[i] ^ b2[i]);
        }

        return result;
    }

    public static void main(String[] args) {
        String hex1 = FileHeader.jpg.getValue();
        byte[] b1 = hex2Bytes(hex1);

        String hex2 = "";
        byte[] b2 = hex2Bytes(hex2);

        xor(b1, b2);
    }
}
