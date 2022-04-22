package cn.reghao.hackwx.web.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-22 13:40:21
 */
@Getter
@Setter
public class BaseResponse {
    private int Ret;
    private String ErrMsg;
}
