package cn.reghao.hackwx.web.model.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-22 15:11:56
 */
@Getter
@Setter
public class GroupMember {
    private String Uin;
    private String UserName;
    private String NickName;
    private String PYInitial;
    private String PYQuanpin;
    private String RemarkPYInitial;
    private String RemarkPYQuanpin;
    private String AttrStatus;
    private String MemberStatus;
    private String DisplayName;
    private String KeyWord;
}
