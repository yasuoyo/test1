package com.example.test.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.example.test.bean.AliPayBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;

/**
 * @program: test1
 * @description:
 * @author: Zqm
 * @create: 2021-08-12 14:35
 **/
@Configuration
public class AliConfig {

    @Value("${custom.http.proxyHost:111}")
    private String proxyHost;
    @Value("${custom.http.proxyPort:111}")
    private int proxyPort;
    @Value("${spring.profiles.active:111}")
    private String activeEnv;

    @Autowired
    private AliPayBean aliPayBean;

    public AlipayClient alipayClientService() throws Exception{
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        //设置网关地址
        certAlipayRequest.setServerUrl(aliPayBean.getServerUrl());
        //设置应用Id
        certAlipayRequest.setAppId(aliPayBean.getAppId());
        //设置应用私钥
        certAlipayRequest.setPrivateKey(aliPayBean.getPrivateKey());
        //设置请求格式,固定值json
        certAlipayRequest.setFormat(aliPayBean.getFormat());
        //设置字符集
        certAlipayRequest.setCharset(aliPayBean.getCharset());
        //设置签名类型
        certAlipayRequest.setSignType(aliPayBean.getSignType());
        //如果是生产环境或者预演环境,则使用代理模式
        if("prod".equals(activeEnv)||"stage".equals(activeEnv)||"test".equals(activeEnv)){
            //设置应用公钥证书路径
            certAlipayRequest.setCertContent(getCertContenByPath(aliPayBean.getAppCertPath()));
            //设置支付宝公钥证书路径
            certAlipayRequest.setAlipayPublicCertContent(getCertContenByPath(aliPayBean.getAlipayCertPath()));
            //设置支付宝根证书路径
            certAlipayRequest.setRootCertContent(getCertContenByPath(aliPayBean.getAlipayRootCertPath()));
            certAlipayRequest.setProxyHost(proxyHost);
            certAlipayRequest.setProxyPort(proxyPort);
        }else {
            //local
            String serverPath = this.getClass().getResource("/").getPath();
            //设置应用公钥证书路径
            certAlipayRequest.setCertPath(serverPath+aliPayBean.getAppCertPath());
            //设置支付宝公钥证书路径
            certAlipayRequest.setAlipayPublicCertPath(serverPath+aliPayBean.getAlipayCertPath());
            //设置支付宝根证书路径
            certAlipayRequest.setRootCertContent(serverPath+aliPayBean.getAlipayRootCertPath());

        }
       return new DefaultAlipayClient(certAlipayRequest);

    }

    public String getCertContenByPath(String name){
        InputStream inputStream = null;
        String content = null;
        try{
            inputStream = this.getClass().getClassLoader().getResourceAsStream(name);
            content = new String(FileCopyUtils.copyToByteArray(inputStream));
        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }




}
