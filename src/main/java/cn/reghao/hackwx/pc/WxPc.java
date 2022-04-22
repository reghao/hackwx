package cn.reghao.hackwx.pc;

import cn.reghao.hackwx.pc.util.DirProcessor;

import java.io.IOException;

/**
 * @author reghao
 * @date 2021-11-26 14:05:59
 */
public class WxPc {
    public static void main(String[] args) {
        String dirPath = "/home/reghao/data/dl/wx/Image";
        WxdatFileHandler wxdatFileHandler = new WxdatFileHandler();
        DirProcessor dirProcessor = new DirProcessor(dirPath, wxdatFileHandler);
        try {
            dirProcessor.walkAndProcess();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
