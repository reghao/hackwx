package cn.reghao.hackwx.web.model.resp;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-22 13:41:34
 */
@Getter
@Setter
public class Contact extends BaseObject<String> {
    private String Uin;
    private String UserName;
    private String NickName;
    private String HeadImgUrl;
    private String ContactFlag;
    private String MemberCount;
    private List<GroupMember> MemberList;
    private String RemarkName;
    private String HideInputBarFlag;
    private String Sex;
    private String Signature;
    private String VerifyFlag;
    private String OwnerUni;
    private String PYInitial;
    private String PYQuanpin;
    private String RemarkPYInitial;
    private String RemarkPYQuanpin;
    private String StarFriend;
    private String AppAccountFlag;
    private String Statues;
    private String AttrStatus;
    private String Province;
    private String City;
    private String Alias;
    private String SnsFlag;
    private String UniFriend;
    private String DisplayName;
    private String ChatRoomId;
    private String KeyWord;
    private String EncryChatRoomId;
    private String IsOwner;
}
