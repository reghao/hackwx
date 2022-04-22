package cn.reghao.hackwx.web.model.resp;

import cn.reghao.hackwx.web.model.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-22 17:13:20
 */
@AllArgsConstructor
@Getter
public class LoginRet {
    private BaseRequest baseRequest;
    private String passTicket;
}
