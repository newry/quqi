<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no;initial-scale=1"/>
<title>快速链接免费无线网络</title>
<link rel='stylesheet' href='/css/css.css' type='text/css' />
<script type="text/javascript" src="/js/jquery.1.11.1.js"></script>
</head>

<body style="background: url(/image/bg2.png) no-repeat top center;background-size: 100%;max-width: 600px;margin:0 auto;position:relative;">
<div style="text-align: center;margin:40px 0 110px 0;"><img src="/image/logo.png" width="200px" /></div>
<p class="cen" style="color:#b6b5b4;margin-top: 150px;border-bottom: 1px solid #5c5555;width: 70%;margin:0 auto;padding-bottom: 7px;">快速链接免费无线网络</p>
<div style="text-align: center;width:70%;margin:0 auto;margin-top: 15px;"><input id="phoneNumber" type="text" style="height: 40px;width: 98%;line-height: 32px;text-align: center;" value="" placeholder='输入手机号码' ></input></div>
<a href="#" onclick="javascript:sendCode()"><div style="text-align: center;width:70%;margin:0 auto;margin-top: 10px;"><p class="d4">点击即可连接无线网络</p></div></a>
<p class="cen" style="margin-top:10px;">您已确认链接网络的相关政策</p>

<div class="content" id="content" style="text-align: center;bottom:0;position: fixed; "><img src="/image/bottom.png" width="100%" /></div>

<script type="text/javascript">
	sendCode = function (){
		var phoneNumber = $('#phoneNumber').val();
		if(phoneNumber.length!=11){
			return false;
		}
		$.ajax({
	  		url:"/quqi/wifi/v1/user/authCode",
	  		method: "POST",
	  		contentType: "application/json",
	  		data: '{"macAddress":"${macAddress}", "phoneNumber":"'+phoneNumber+'"}',
	  		dataType: "json"
		}).success(function() {
			window.location='/quqi/wifi/authCode?macAddress=${macAddress}&store=${store}&phoneNumber='+phoneNumber;
		});
	}
	
</script>
</body>
</html>
