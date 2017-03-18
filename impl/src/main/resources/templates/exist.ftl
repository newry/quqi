
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no;initial-scale=1"/>
<title>欢迎您再次光临</title>
<link rel='stylesheet' href='/css/css.css' type='text/css' />
<script type="text/javascript" src="/js/jquery.1.11.1.js"></script>
</head>

<body style="background: url(/image/bg.png) no-repeat top center;background-size: 100%;max-width: 600px;">

<div style="text-align: center;margin:40px 0 50px 0;"><img src="/image/logo.png" width="200px" /></div>
<p class="cen">欢迎您再次光临</p>
<!--
<a href="#" onclick="javascript:tracking()"><div style="text-align: center;width:70%;margin:0 auto;margin-top: 35px;"><p class="d">点击即可连接无线网络</p></div></a>
-->
<p class="cen" style="margin-top:55px;">您已确认链接网络的相关政策</p>
</body>
<script type="text/javascript">
	tracking = function (){
		$.ajax({
	  		url:"/quqi/wifi/v1/user/tracking",
	  		method: "POST",
	  		contentType: "application/json",
	  		data: '{"macAddress":"${macAddress}", "store":"${store}"}',
	  		dataType: "json"
		}).success(function() {
		});
	}
	tracking();
	
</script>

</html>
