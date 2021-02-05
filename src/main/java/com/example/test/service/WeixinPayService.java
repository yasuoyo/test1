package com.example.test.service;

import java.util.Map;

public interface WeixinPayService {
    /**
     * 创建二维码
     *
     * @param parameterMap：用户订单信息
     * @return
     */
    Map createnative(Map<String, String> parameterMap);

    /**
     * 查询微信支付状态
     *
     * @param outtradeno：商户订单号，由微信服务器生成
     * @return
     */
    Map queryStatus(String outtradeno);

}
