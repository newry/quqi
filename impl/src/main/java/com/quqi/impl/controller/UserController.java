package com.quqi.impl.controller;

import java.text.DecimalFormat;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.quqi.impl.controller.response.Error;
import com.quqi.impl.controller.response.OperationResponse;
import com.quqi.impl.controller.response.OperationResult;
import com.quqi.impl.entity.wifi.User;
import com.quqi.impl.entity.wifi.UserAuthCode;
import com.quqi.impl.entity.wifi.UserSMSHistory;
import com.quqi.impl.entity.wifi.UserTracking;
import com.quqi.impl.repository.wifi.UserAuthCodeRepository;
import com.quqi.impl.repository.wifi.UserRepository;
import com.quqi.impl.repository.wifi.UserSMSHistoryRepository;
import com.quqi.impl.repository.wifi.UserTrackingRepository;
import com.quqi.impl.util.Utils;

@RestController
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserTrackingRepository userTrackingRepository;
	@Autowired
	private UserAuthCodeRepository userAuthCodeRepository;
	@Autowired
	private UserSMSHistoryRepository userSMSHistoryRepository;

	@RequestMapping(value = "/quqi/wifi/v1/user/{macAddress}", method = RequestMethod.GET)
	public ResponseEntity<OperationResponse> checkUser(@PathVariable("macAddress") String macAddress) throws Exception {
		LOG.debug("macAddress={}", macAddress);
		String macAddressToUse = StringUtils.trimToEmpty(macAddress);
		if (StringUtils.isNotEmpty(macAddressToUse)) {
			User user = userRepository.findByMacAddress(macAddressToUse);
			if (user != null) {
				OperationResponse or = new OperationResponse();
				OperationResult result = new OperationResult();
				result.setSuccess(true);
				or.setResult(result);
				return new ResponseEntity<OperationResponse>(or, HttpStatus.OK);
			}
		}
		OperationResponse or = new OperationResponse();
		OperationResult result = new OperationResult();
		result.setSuccess(true);
		or.setResult(result);
		return new ResponseEntity<OperationResponse>(or, HttpStatus.NOT_FOUND);

	}

	@RequestMapping(value = "/quqi/wifi/v1/user/tracking", method = RequestMethod.POST)
	public ResponseEntity<OperationResponse> createUserTracking(@RequestBody Map<String, String> request)
			throws Exception {
		LOG.debug("request={}", request);
		String macAddressTo = StringUtils.trimToEmpty(request.get("macAddress"));
		String store = StringUtils.trimToEmpty(request.get("store"));
		if (StringUtils.isNotEmpty(macAddressTo)) {
			User user = userRepository.findByMacAddress(macAddressTo);
			if (user != null) {
				createTracking(store, user);
			}
		}
		OperationResponse or = new OperationResponse();
		OperationResult result = new OperationResult();
		result.setSuccess(true);
		or.setResult(result);
		return new ResponseEntity<OperationResponse>(or, HttpStatus.OK);
	}

	@RequestMapping(value = "/quqi/wifi/v1/user/", method = RequestMethod.POST)
	public ResponseEntity<OperationResponse> createUser(@RequestBody Map<String, String> request) throws Exception {
		LOG.debug("request={}", request);
		String macAddress = StringUtils.trimToEmpty(request.get("macAddress"));
		String phoneNumber = StringUtils.trimToEmpty(request.get("phoneNumber"));
		String authCode = StringUtils.trimToEmpty(request.get("authCode"));
		if (StringUtils.isNotEmpty(macAddress)) {
			User user = userRepository.findByMacAddress(macAddress);
			if (user == null) {
				UserAuthCode userAuthCode = userAuthCodeRepository.findByMacAddress(macAddress, phoneNumber,
						Utils.getCalenderWithHour(-1));
				if (userAuthCode != null && userAuthCode.getAuthCode().equals(authCode)) {
					user = new User();
					user.setMacAddress(macAddress);
					user.setPhoneNumber(phoneNumber);
					userRepository.save(user);
					String store = StringUtils.trimToEmpty(request.get("store"));
					createTracking(store, user);
					OperationResponse or = new OperationResponse();
					OperationResult result = new OperationResult();
					result.setSuccess(true);
					or.setResult(result);
					return new ResponseEntity<OperationResponse>(or, HttpStatus.CREATED);
				} else {
					OperationResponse or = new OperationResponse();
					OperationResult result = new OperationResult();
					result.setSuccess(false);
					Error error = new Error();
					error.setCode("INVALID_AUTH_CODE");
					result.setError(error);
					or.setResult(result);
					return new ResponseEntity<OperationResponse>(or, HttpStatus.UNAUTHORIZED);
				}
			}
		}
		OperationResponse or = new OperationResponse();
		OperationResult result = new OperationResult();
		result.setSuccess(true);
		or.setResult(result);
		return new ResponseEntity<OperationResponse>(or, HttpStatus.OK);
	}

	@RequestMapping(value = "/quqi/wifi/v1/user/authCode", method = RequestMethod.POST)
	public ResponseEntity<OperationResponse> sendAuthCode(@RequestBody Map<String, String> request) throws Exception {
		LOG.debug("request={}", request);
		String macAddress = StringUtils.trimToEmpty(request.get("macAddress"));
		String phoneNumber = StringUtils.trimToEmpty(request.get("phoneNumber"));
		if (StringUtils.isNotEmpty(macAddress) && StringUtils.isNotEmpty(phoneNumber)) {
			User user = userRepository.findByMacAddress(macAddress);
			if (user == null) {
				UserAuthCode userAuthCode = userAuthCodeRepository.findByMacAddress(macAddress, phoneNumber,
						Utils.getCalenderWithHour(-1));
				if (userAuthCode == null) {
					userAuthCode = new UserAuthCode();
					userAuthCode.setMacAddress(macAddress);
					userAuthCode.setPhoneNumber(phoneNumber);
					userAuthCode.setAuthCode(new DecimalFormat("0000").format(System.currentTimeMillis() % 10000));
					userAuthCodeRepository.save(userAuthCode);
				}
				LOG.debug("userAuthCode={}", userAuthCode.getAuthCode());
				sendSms(phoneNumber, userAuthCode.getAuthCode());
			}
		}
		OperationResponse or = new OperationResponse();
		OperationResult result = new OperationResult();
		result.setSuccess(true);
		or.setResult(result);
		return new ResponseEntity<OperationResponse>(or, HttpStatus.OK);
	}

	private void createTracking(String store, User user) {
		UserTracking tracking = new UserTracking();
		tracking.setUser(user);
		tracking.setStore(store);
		userTrackingRepository.save(tracking);
	}

	private void sendSms(String phoneNumber, String code) {
		
		UserSMSHistory userSMSHistory = userSMSHistoryRepository.findByPhoneNumer(phoneNumber,
				Utils.getCalenderWithMinute(-1));
		if (userSMSHistory == null) {
			try {
				doSendSms(phoneNumber, code);
				userSMSHistory = new UserSMSHistory();
				userSMSHistory.setPhoneNumber(phoneNumber);
				userSMSHistoryRepository.save(userSMSHistory);
			} catch (Exception e) {
				LOG.error("Error during send sms", e);
			}
		}
	}

	private void doSendSms(String phoneNumber, String code) throws ClientException, ServerException {
//		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIeobj1lz58X4W",
//				"nJBVoCnYUCo546P4bblDxa70oSIu6w");
//		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms", "sms.aliyuncs.com");
//		IAcsClient client = new DefaultAcsClient(profile);
//		SingleSendSmsRequest request = new SingleSendSmsRequest();
//		request.setSignName("测试签名");
//		request.setTemplateCode("SMS_111111");
//		request.setParamString("{\"code\":\"" + code + "\"}");
//		request.setRecNum(phoneNumber);// 接收号码
//		SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
//		LOG.info(httpResponse.getModel());
	}

}
