package com.quqi.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;

public class TestSMS {

	public static void main(String[] args) throws Exception {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIeobj1lz58X4W",
				"nJBVoCnYUCo546P4bblDxa70oSIu6w");
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms", "sms.aliyuncs.com");
		IAcsClient client = new DefaultAcsClient(profile);
		SingleSendSmsRequest request = new SingleSendSmsRequest();
		request.setSignName("曲奇大数据");
		request.setTemplateCode("SMS_53835195");
		request.setParamString("{\"code\":\"1234\"}");
		request.setRecNum("13916954968");// 接收号码
		SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
		System.out.println(httpResponse);

	}

}
