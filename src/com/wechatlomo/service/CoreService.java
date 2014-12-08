package com.wechatlomo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wechatlomo.message.resp.Article;
import com.wechatlomo.message.resp.NewsMessage;
import com.wechatlomo.message.resp.TextMessage;
import com.wechatlomo.util.MessageUtil;

/** 
 * 核心服务类 
 *  
 * @author liufeng 
 * @date 2013-05-20 
 */  
public class CoreService {  
    /** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
  
            // 默认回复此文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义  
            textMessage.setContent("欢迎关注WeChatLomo，这是一个有节操的产品，目前处于开发阶段。有问题请联系<a href=\"yixuaning@sina.com\">我的邮箱/a>!");  
            // 将文本消息对象转换成xml字符串  
            respMessage = MessageUtil.textMessageToXml(textMessage);  
  
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
                // 接收用户发送的文本消息内容  
                String content = requestMap.get("Content");  
  
                // 创建图文消息  
                NewsMessage newsMessage = new NewsMessage();  
                newsMessage.setToUserName(fromUserName);  
                newsMessage.setFromUserName(toUserName);  
                newsMessage.setCreateTime(new Date().getTime());  
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);

				List<Article> articleList = new ArrayList<Article>();
				Article article1 = new Article();
				article1.setTitle("关于WeChatLomo\n引言");
				article1.setDescription("");
				article1.setPicUrl("http://wechatlomo.duapp.com/images/wechatlomo.jpeg");
				article1.setUrl("http://wechatlomo.duapp.com/index.html");

				Article article2 = new Article();
				article2.setTitle("你就不想了解一下该产品的程序狗？");
				article2.setDescription("");
				article2.setPicUrl("http://wechatlomo.duapp.com/images/yasin.jpg");
				article2.setUrl("http://wechatlomo.duapp.com/blog.htm");

				Article article3 = new Article();
				article3.setTitle("WeChatLomo还缺产品狗，你来不来？");
				article3.setDescription("");
				article3.setPicUrl("http://wechatlomo.duapp.com/images/gallery_img_8.png");
				article3.setUrl("http://wechatlomo.duapp.com/index.html");

				articleList.add(article1);
				articleList.add(article2);
				articleList.add(article3);
				newsMessage.setArticleCount(articleList.size());
				newsMessage.setArticles(articleList);
				respMessage = MessageUtil.newsMessageToXml(newsMessage);
			}
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return respMessage;  
    }  
}  