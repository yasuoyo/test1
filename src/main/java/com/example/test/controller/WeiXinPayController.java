package com.example.test.controller;

import com.example.test.bean.Result;
import com.example.test.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * @program: test1
 * @description:
 * @author: Zqm
 * @create: 2021-02-04 13:59
 **/
@RestController
@RequestMapping(value = "/weixin/pay")
public class WeiXinPayController {
    @Autowired
    private WeixinPayService weixinPayService;

    /**
     * 创建二维码
     *
     * @param parameterMap：用户订单信息
     * @return
     */
    @RequestMapping(value = "/create/native")
    public Result createNative(@RequestParam Map<String, String> parameterMap) {
        Map<String, String> resultMap = weixinPayService.createnative(parameterMap);
        return  new Result(true, 2000, "创建二维码预付订单成功！", resultMap);
    }

    /**
     * 微信支付状态查询
     * @param outtradeno:商户订单号，由微信服务器生成
     * @return
     */
    @GetMapping(value = "/status/query")
    public Result queryStatus(String outtradeno){
        //查询微信支付状态
        Map map = weixinPayService.queryStatus(outtradeno);
        return new Result(true, 2000,"微信支付状态查询成功！",map);
    }

    /**
     * 支付结果通知回调方法
     * @param request
     * @return
     */
    @RequestMapping(value = "/notify/url")
    public String notifyurl(HttpServletRequest request) throws Exception {
        //获取网络输入流，也就是网络输入流格式的通知结果
        ServletInputStream inputStream = request.getInputStream();

        //创建一个OutputStream输出流->输入文件
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //定义缓冲区
        byte[] buffer = new byte[1024];

        //初始化一个数据长度
        int len = 0;

        //往缓冲区里读文件，数据长度 不等于-1说明有数据
        while ((len = inputStream.read(buffer))!=-1){
            //缓冲区中的数据  写入到   输出流对象中
            //从0开始读到长度最后一位
            byteArrayOutputStream.write(buffer,0,len);
        }

        //将byteArrayOutputStream字节流转为字节数组
        //这就是微信支付结果的字节数组
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        //字节数组  转为  xml字符串
        String xmlResult = new String(byteArray, "Utf-8");
        System.out.println("xmlResult:\n"+xmlResult);

        //xml字符串  转为  Map
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlResult);
        System.out.println("resultMap:\n"+resultMap);

        //返回结果
        String result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        return result;
    }

}
