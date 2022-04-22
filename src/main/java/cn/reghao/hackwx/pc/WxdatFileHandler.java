package cn.reghao.hackwx.pc;

import cn.reghao.hackwx.pc.util.ByteHex;
import cn.reghao.hackwx.pc.util.FileHandler;
import cn.reghao.hackwx.pc.util.FileHeader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 解码 PC 版微信的本地图片文件(dat 文件)
 *
 * @author reghao
 * @date 2021-11-26 15:07:26
 */
public class WxdatFileHandler implements FileHandler {
    @Override
    public void handleFile(String filePath) throws IOException {
        File file = new File(filePath);
        byte fileCode = getFileCode(file);
        if (fileCode == 0) {
            String errMsg = String.format("未找到文件 %s 的编码", filePath);
            throw new IOException(errMsg);
        }

        String name = file.getName();
        String parentDirPath = file.getParent();
        String name1 = name.replace(".dat", ".jpg");
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(parentDirPath + File.separator + name1);

        int len = 1;
        byte[] buf = new byte[1];
        while (fis.read(buf, 0, len) != -1) {
            byte res =(byte) (buf[0] ^ fileCode);
            fos.write(res);
        }
        fos.close();
        fis.close();
    }

    /**
     * 取文件头和 jpg 文件头进行异或获取编码值
     *
     * @param
     * @return
     * @date 2021-11-26 下午5:27
     */
    private byte getFileCode(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        int len = 2;
        byte[] buf = new byte[len];
        int ret = fis.read(buf, 0, len);
        fis.close();
        if (ret == -1) {
            throw new IOException("读取文件的前 2 个字节失败");
        }

        String fileHeaderHex = ByteHex.bytes2Hex(buf);
        byte[] b2 = ByteHex.hex2Bytes(fileHeaderHex);

        String hex1 = FileHeader.jpg.getValue();
        byte[] b1 = ByteHex.hex2Bytes(hex1);
        byte[] xorResult = ByteHex.xor(b1, b2);
        if (xorResult[0] == xorResult[1]) {
            return xorResult[0];
        }

        return 0;
    }
}
