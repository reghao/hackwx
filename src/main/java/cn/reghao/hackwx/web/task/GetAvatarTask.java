package cn.reghao.hackwx.web.task;

import cn.reghao.hackwx.web.model.resp.Contact;
import cn.reghao.jutil.jdk.http.WebRequest;

import java.io.IOException;

/**
 * @author reghao
 * @date 2022-04-22 19:15:08
 */
public class GetAvatarTask implements Runnable {
    private final WebRequest webRequest;
    private final Contact contact;

    public GetAvatarTask(WebRequest webRequest, Contact contact) {
        this.webRequest = webRequest;
        this.contact = contact;
    }

    @Override
    public void run() {
        String username = contact.getUserName();
        String url = String.format("https://wx2.qq.com%s", contact.getHeadImgUrl());
        String filePath = String.format("/home/reghao/Downloads/wx/%s.jpg", username);
        try {
            webRequest.download(url, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
