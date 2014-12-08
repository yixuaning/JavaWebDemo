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
 * ���ķ����� 
 *  
 * @author liufeng 
 * @date 2013-05-20 
 */  
public class CoreService {  
    /** 
     * ����΢�ŷ��������� 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // xml�������  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // ���ͷ��ʺţ�open_id��  
            String fromUserName = requestMap.get("FromUserName");  
            // �����ʺ�  
            String toUserName = requestMap.get("ToUserName");  
            // ��Ϣ����  
            String msgType = requestMap.get("MsgType");  
  
            // Ĭ�ϻظ����ı���Ϣ  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            // ����href����ֵ������˫�������������ַ��������˫���ų�ͻ������Ҫת��  
            textMessage.setContent("��ӭ��עWeChatLomo������һ���нڲٵĲ�Ʒ��Ŀǰ���ڿ����׶Ρ�����������ϵ<a href=\"yixuaning@sina.com\">�ҵ�����/a>!");  
            // ���ı���Ϣ����ת����xml�ַ���  
            respMessage = MessageUtil.textMessageToXml(textMessage);  
  
            // �ı���Ϣ  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
                // �����û����͵��ı���Ϣ����  
                String content = requestMap.get("Content");  
  
                // ����ͼ����Ϣ  
                NewsMessage newsMessage = new NewsMessage();  
                newsMessage.setToUserName(fromUserName);  
                newsMessage.setFromUserName(toUserName);  
                newsMessage.setCreateTime(new Date().getTime());  
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);

				List<Article> articleList = new ArrayList<Article>();
				Article article1 = new Article();
				article1.setTitle("����WeChatLomo\n����");
				article1.setDescription("");
				article1.setPicUrl("http://wechatlomo.duapp.com/images/wechatlomo.jpeg");
				article1.setUrl("http://wechatlomo.duapp.com/index.html");

				Article article2 = new Article();
				article2.setTitle("��Ͳ����˽�һ�¸ò�Ʒ�ĳ��򹷣�");
				article2.setDescription("");
				article2.setPicUrl("http://wechatlomo.duapp.com/images/yasin.jpg");
				article2.setUrl("http://wechatlomo.duapp.com/blog.htm");

				Article article3 = new Article();
				article3.setTitle("WeChatLomo��ȱ��Ʒ��������������");
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