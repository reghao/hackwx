package cn.reghao.hackwx.web.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-22 10:54:52
 */
@Setter
@Getter
public class BaseRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String Uin;
    private String Sid;
    private String Skey;
    private String DeviceID;
}
