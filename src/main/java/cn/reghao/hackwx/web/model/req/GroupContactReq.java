package cn.reghao.hackwx.web.model.req;

/**
 * @author reghao
 * @date 2022-04-22 15:11:56
 */
public class GroupContactReq {
    private String UserName;
    private String ChatRoomId;
    private String EncryChatRoomId;

    public GroupContactReq(String username) {
        this.UserName = username;
        this.ChatRoomId = "";
    }

    public GroupContactReq(String username, String encryChatRoomId) {
        this.UserName = username;
        this.EncryChatRoomId = encryChatRoomId;
    }
}
