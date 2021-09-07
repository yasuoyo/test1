package com.example.test.service.Impl;

import com.example.test.utils.HttpClient;
import com.example.test.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

/**
 * @program: test1
 * @description:
 * @author: Zqm
 * @create: 2021-02-04 14:05
 **/
@Service
public class WeixinPayServiceImpl implements WeixinPayService {
    //应用ID
    @Value("${weixin.appid:111}")
    private String appid;
    //商户号
    @Value("${weixin.partner:111}")
    private String partner;
    //密钥
    @Value("${weixin.partnerkey:111}")
    private String partnerkey;
    //通知地址
    @Value("${weixin.notifyurl:111}")
    private String notifyurl;

    /**
     * 创建二维码
     *
     * @param parameterMap：用户订单信息
     * @return
     */
    @Override
    public Map createnative(Map<String, String> parameterMap) {
        try {
            //远程调用
            //创建一个Map集合存放请求参数
            Map<String, String> paramMap = new HashMap<>();
            //添加数据
            //应用ID
            paramMap.put("appid", appid);
            //商户号
            paramMap.put("mch_id", partner);
            //随机字符串
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            //商品描述
            paramMap.put("body", "茶碗儿购物是真的好");
            //订单号
            paramMap.put("out_trade_no", parameterMap.get("outtradeno"));
            //交易总金额，单位为：分
            paramMap.put("total_fee", parameterMap.get("totalfee"));
            //终端IP
            paramMap.put("spbill_create_ip", "127.0.0.1");
            //通知地址，添URL地址
            paramMap.put("notify_url", notifyurl);
            //交易类型，NATIVE
            paramMap.put("trade_type", "NATIVE");

            //签名
            //paramMap.put("sign","");
            //Map转为XML数据，可以自带签名
            //将请求参数集合，转为带有签名的XML数据格式
            String xmlStr = WXPayUtil.generateSignedXml(paramMap, partnerkey);

            //URL地址，微信支付接口链接
            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

            //提交方式:HTTPS
            //创建HttpClient对象,对url请求地址进行操作
            HttpClient httpClient = new HttpClient(url);
            //设置提交方式为HTTPS
            httpClient.setHttps(true);
            //提交参数,参数以XML数据格式提交
            httpClient.setXmlParam(xmlStr);

            //执行请求
            //发送XML数据，使用post请求
            httpClient.post();

            //获取返回的数据,此时返回的数据为XML数据格式
            String content = httpClient.getContent();
            System.out.println("content:" + content);

            //返回数据转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 查询微信支付状态
     * @param outtradeno：商户订单号，由微信服务器生成
     * @return
     */
    @Override
    public Map queryStatus(String outtradeno) {
        try {
            //远程调用
            //创建一个Map集合存放请求参数
            Map<String, String> paramMap = new HashMap<>();
            //添加数据
            //应用ID
            paramMap.put("appid", appid);
            //商户号
            paramMap.put("mch_id", partner);
            //随机字符串
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            //订单号
            paramMap.put("out_trade_no", outtradeno);

            //签名
            //paramMap.put("sign","");
            //Map转为XML数据，可以自带签名
            //将请求参数集合，转为带有签名的XML数据格式
            String xmlStr = WXPayUtil.generateSignedXml(paramMap, partnerkey);

            //URL地址，微信查询订单接口链接
            String url = "https://api.mch.weixin.qq.com/pay/orderquery";

            //提交方式:HTTPS
            //创建HttpClient对象,对url请求地址进行操作
            HttpClient httpClient = new HttpClient(url);
            //设置提交方式为HTTPS
            httpClient.setHttps(true);

            //提交参数,参数以XML数据格式提交
            httpClient.setXmlParam(xmlStr);

            //执行请求
            //发送XML数据，使用post请求
            httpClient.post();

            //获取返回的数据,此时返回的数据为XML数据格式
            String content = httpClient.getContent();
            //System.out.println("content:" + content);

            //返回数据转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
