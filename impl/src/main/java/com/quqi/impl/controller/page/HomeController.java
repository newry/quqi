package com.quqi.impl.controller.page;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.quqi.impl.entity.wifi.User;
import com.quqi.impl.repository.wifi.UserRepository;

@Controller
public class HomeController {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(path = "/quqi/wifi/", method = RequestMethod.GET)
	public String index(@RequestParam String macAddress, @RequestParam String store, Map<String, Object> model) {
		User user = userRepository.findByMacAddress(macAddress);
		model.put("macAddress", macAddress);
		model.put("store", store);
		if (user != null) {
			model.put("user", user);
			return "exist";
		} else {
			return "new";
		}
	}

	@RequestMapping(path = "/quqi/wifi/authCode", method = RequestMethod.GET)
	public String authCode(@RequestParam String macAddress, @RequestParam String store, @RequestParam String phoneNumber,
			Map<String, Object> model) {
		model.put("macAddress", macAddress);
		model.put("store", store);
		model.put("phoneNumber", phoneNumber);
		return "authCode";
	}

}
