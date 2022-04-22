package cn.reghao.hackwx.web.model.resp;

import cn.reghao.hackwx.web.model.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-22 13:47:55
 */
@Getter
@Setter
public class ContactRet {
    private BaseResponse BaseResponse;
    private String MemberCount;
    private List<Contact> MemberList;
    private String Seq;
}
