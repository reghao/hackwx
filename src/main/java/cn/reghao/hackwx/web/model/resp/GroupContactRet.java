package cn.reghao.hackwx.web.model.resp;

import cn.reghao.hackwx.web.model.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-22 15:21:50
 */
@Getter
@Setter
public class GroupContactRet {
    private BaseResponse BaseResponse;
    private List<Contact> ContactList;
    private int Count;
}
