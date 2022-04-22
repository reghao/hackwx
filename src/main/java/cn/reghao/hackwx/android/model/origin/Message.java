package cn.reghao.hackwx.android.model.origin;

import lombok.Data;

/**
 * @author reghao
 * @date 2021-01-27 12:40:57
 */
@Data
public class Message {
    private int msgId;
    private int msgSvrId;
    // 消息类型
    // 1-文本和表情(以文本表示) 3-图片 34-语音 43-视频 47-大表情 49-分享的链接,网址等 10000-撤回的消息
    private int type;
    private int status;
    // 0-对方发送的消息 1-自己发送的消息
    private int isSend;
    private int isShowTimer;
    private int createTime;
    private String talker;
    private String content;
    private String imgPath;
    private String reserved;
    private byte[] lvbuffer;
    private String transContent;
    private String transBrandWording;
    private int talkerId;
    private String bizClientMsgId;
    private int bizChatId;
    private String bizChatUserId;
    private int msgSeq;
    private int flag;
    private byte[] solitaireFoldInfo;
}
