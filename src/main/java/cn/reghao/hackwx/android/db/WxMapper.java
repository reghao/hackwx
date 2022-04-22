package cn.reghao.hackwx.android.db;

import cn.reghao.hackwx.android.model.WxContact;
import cn.reghao.hackwx.android.model.WxDialogue;
import cn.reghao.hackwx.android.model.WxMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WxMapper {
    List<WxContact> findAllContact();
    List<WxDialogue> findAllDialogue();
    List<WxMessage> findMessageByUsername(String username);
    //List<WxMessage> findMessageByAlias(@Param("alias") String alias);
    List<WxMessage> findMessageByAlias(String alias);
}
