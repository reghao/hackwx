package cn.reghao.hackwx.android.model.origin;

import lombok.Data;

/**
 * @author reghao
 * @date 2021-01-28 01:38:53
 */
@Data
public class Rcontact {
    private String username;
    private String alias;
    private String conRemark;
    private String domainList;
    private String nickname;
    private String pyInitial;
    private String quanPin;
    private int showHead;
    private int type;
    private int weiboFlag;
    private String weiboNickname;
    private String conRemarkPYFull;
    private byte[] lvbuff;
    private int verifyFlag;
    private String encryptUsername;
    private int chatroomFlag;
    private int deleteFlag;
    private String contactLabelIds;
    private String descWordingId;
    private String openImAppid;
    private String sourceExtInfo;
    private String ticket;
}
