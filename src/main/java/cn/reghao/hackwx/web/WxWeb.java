package cn.reghao.hackwx.web;

import cn.reghao.hackwx.web.model.BaseRequest;
import cn.reghao.hackwx.web.model.req.GroupContactReq;
import cn.reghao.hackwx.web.model.resp.*;
import cn.reghao.jutil.jdk.http.WebRequest;
import cn.reghao.jutil.jdk.http.WebResponse;
import cn.reghao.jutil.jdk.serializer.JsonConverter;
import cn.reghao.jutil.tool.http.DefaultWebRequest;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class WxWeb {
    String domain = "https://wx2.qq.com";
    String prefix = "https://wx2.qq.com/cgi-bin/mmwebwx-bin";
    WebRequest webRequest = new DefaultWebRequest();

    LoginRet login() throws Exception {
        String url = "https://login.wx2.qq.com/jslogin?appid=wx782c26e4c19acffb" +
                "&redirect_uri=https%3A%2F%2Fwx2.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage" +
                "&fun=new&lang=zh_CN&_=" + System.currentTimeMillis();
        WebResponse webResponse = webRequest.get(url);
        if (webResponse.getStatusCode() != 200) {
            log.error("{} 请求失败", url);
            return null;
        }

        String body = webResponse.getBody();
        String uuid = body.split(";")[1].split(" = ")[1].replace("\"", "");
        String qrCode = String.format("https://login.weixin.qq.com/qrcode/%s", uuid);
        webRequest.download(qrCode, "/home/reghao/Downloads/qrcode.jpg");

        url = waitScanQrCode(uuid);
        while (url == null) {
            log.info("休眠 1s 等待扫码...");
            Thread.sleep(1_000);
            url = waitScanQrCode(uuid);
        }

        url += "&fun=new&version=v2&lang=zh_CN";
        webResponse = webRequest.get(url);
        if (webResponse.getStatusCode() != 200) {
            log.error("{} 请求失败", url);
            return null;
        }

        body = webResponse.getBody();
        SAXReader reader = new SAXReader();
        Document document = reader.read(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
        Element root = document.getRootElement();

        String ret = root.element("ret").getStringValue();
        String message = root.element("message").getStringValue();
        String wxsid = root.element("wxsid").getStringValue();
        String skey = root.element("skey").getStringValue();
        String wxuin = root.element("wxuin").getStringValue();
        String passTicket = root.element("pass_ticket").getStringValue();
        String isgrayscale = root.element("isgrayscale").getStringValue();

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUin(wxuin);
        baseRequest.setSid(wxsid);
        baseRequest.setSkey(skey);
        baseRequest.setDeviceID("e405267847146790");
        return new LoginRet(baseRequest, passTicket);
    }

    String waitScanQrCode(String uuid) {
        String url = String.format("https://login.wx2.qq.com/cgi-bin/mmwebwx-bin/login?" +
                "loginicon=true&uuid=%s&tip=0&r=-1325060865&_=%s", uuid, System.currentTimeMillis());
        WebResponse webResponse = webRequest.get(url);
        if (webResponse.getStatusCode() != 200) {
            log.error("{} 请求失败", url);
            return null;
        }

        String body = webResponse.getBody();
        String[] ret = body.split(";");
        if (ret[0].endsWith("200")) {
            url = ret[1].replace(System.lineSeparator(), "")
                    .replace("window.redirect_uri=", "").replace("\"", "");
            return url;
        }
        return null;
    }

    void init(String passTicket, BaseRequest baseRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("BaseRequest", baseRequest);

        String url = String.format("%s/webwxinit", prefix);
        WebResponse webResponse = webRequest.postJson(url, JsonConverter.objectToJson(map));
        if (webResponse.getStatusCode() != 200) {
            log.error("{} 请求失败", url);
            return;
        }

        String body1 = webResponse.getBody();
    }

    void getUserContact(String passTicket, BaseRequest baseRequest, boolean getAvatar) {
        long ts = System.currentTimeMillis();
        String sKey = baseRequest.getSkey();
        String url = String.format("%s/webwxgetcontact?pass_ticket=%s&r=%s&seq=0&skey=%s", prefix, passTicket, ts, sKey);
        WebResponse webResponse = webRequest.get(url);
        if (webResponse.getStatusCode() != 200) {
            log.error("{} 请求失败", url);
            return;
        }

        String body = webResponse.getBody();
        ContactRet contactRet = JsonConverter.jsonToObject(body, ContactRet.class);
        List<Contact> contacts = contactRet.getMemberList();
        if (getAvatar) {
            for (Contact contact : contacts) {
                try {
                    getUserAvatar(contact);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        List<Contact> groups = contacts.stream()
                .filter(contact -> contact.getUserName().startsWith("@@"))
                .collect(Collectors.toList());
        getGroupMember(passTicket, baseRequest, groups, getAvatar);
    }

    void getGroupMember(String passTicket, BaseRequest baseRequest, List<Contact> groups, boolean getAvatar) {
        List<GroupContactReq> list = new ArrayList<>();
        for (Contact group : groups) {
            String username = group.getUserName();
            list.add(new GroupContactReq(username));
        }

        long ts = System.currentTimeMillis();
        String url = String.format("%s/webwxbatchgetcontact?type=ex&r=%s&lang=zh_CN&pass_ticket=%s",
                prefix, ts, passTicket);

        Map<String, Object> map = new HashMap<>();
        map.put("BaseRequest", baseRequest);
        map.put("List", list);
        map.put("Count", list.size());
        WebResponse webResponse = webRequest.postJson(url, JsonConverter.objectToJson(map));
        if (webResponse.getStatusCode() != 200) {
            log.error("{} 请求失败", url);
            return;
        }

        String body = webResponse.getBody();
        GroupContactRet groupContactRet = JsonConverter.jsonToObject(body, GroupContactRet.class);
        for (Contact contact : groupContactRet.getContactList()) {
            String encryChatRoomId = contact.getEncryChatRoomId();
            List<GroupMember> groupMembers = contact.getMemberList();

            List<GroupContactReq> list1 = new ArrayList<>();
            for (GroupMember groupMember : groupMembers) {
                String username = groupMember.getUserName();
                list1.add(new GroupContactReq(username, encryChatRoomId));

                if (list1.size() > 50) {
                    getGroupMemberContact(passTicket, baseRequest, list1, getAvatar);
                    list1.clear();
                }
            }

            if (!list1.isEmpty()) {
                getGroupMemberContact(passTicket, baseRequest, list1, getAvatar);
            }
        }
    }

    void getGroupMemberContact(String passTicket, BaseRequest baseRequest, List<GroupContactReq> list, boolean getAvatar) {
        long ts = System.currentTimeMillis();
        String url = String.format("%s/webwxbatchgetcontact?type=ex&r=%s&lang=zh_CN&pass_ticket=%s",
                prefix, ts, passTicket);
        Map<String, Object> map = new HashMap<>();
        map.put("BaseRequest", baseRequest);
        map.put("List", list);
        map.put("Count", list.size());
        WebResponse webResponse = webRequest.postJson(url, JsonConverter.objectToJson(map));
        if (webResponse.getStatusCode() != 200) {
            log.error("{} 请求失败", url);
            return;
        }

        String body = webResponse.getBody();
        GroupContactRet groupContactRet = JsonConverter.jsonToObject(body, GroupContactRet.class);
        List<Contact> contacts = groupContactRet.getContactList();
        if (getAvatar) {
            for (Contact contact : contacts) {
                try {
                    getUserAvatar(contact);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void getUserAvatar(Contact contact) throws IOException, InterruptedException {
        String username = contact.getUserName();
        String url = domain + contact.getHeadImgUrl();
        String filePath = String.format("/home/reghao/Downloads/wx/%s.jpg", username);
        webRequest.download(url, filePath);
    }

    public static void main(String[] args) throws Exception {
        WxWeb wxWeb = new WxWeb();
        LoginRet loginRet = wxWeb.login();
        BaseRequest baseRequest = loginRet.getBaseRequest();
        String passTicket = loginRet.getPassTicket();
        wxWeb.init(passTicket, baseRequest);
        wxWeb.getUserContact(passTicket, baseRequest, true);
    }
}
