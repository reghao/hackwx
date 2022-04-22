package cn.reghao.hackwx.android.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author reghao
 * @date 2021-05-01 20:30:04
 */
@Data
public class WxDialogue {
    private String username;
    private String alias;
    private String nickname;
    private String conRemark;
    private String content;
    private long createTime;
    private LocalDateTime createDateTime;
}
