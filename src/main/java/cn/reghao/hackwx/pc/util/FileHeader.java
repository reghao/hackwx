package cn.reghao.hackwx.pc.util;

/**
 * 文件头
 *
 * @author reghao
 * @date 2021-11-26 17:19:34
 */
public enum FileHeader {
    jpg("ffd8");

    private final String value;
    FileHeader(String value) {
        this.value = value;
    }

    public String getName() {
        return this.name();
    }

    public String getValue() {
        return value;
    }
}
