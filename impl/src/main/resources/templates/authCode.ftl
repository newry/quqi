
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no;initial-scale=1"/>
<title>请输入验证码</title>
<link rel='stylesheet' href='/css/css.css' type='text/css' />

</head>

<body style="max-width: 600px;">
<div class="top">
    <div class="left" onclick="window.location='/quqi/wifi/?macAddress=${macAddress}&store=${store}'">返回</div>
    <div class="right">请输入验证码</div>
</div>
<p class="phone">短信已发送至 ${phoneNumber}</p>
<p class="input">
    <div class="box">
        <label for="ipt">
            <ul>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </label>
        <input class="input1" type="tel" id="ipt" maxlength="4">
    </div>
</p>
<p class="c"><input id="btn" type="button" style="height:32px;width:120px;border:none;background: none;color:#c9caca;" value="重新发送&gt;" onclick="sendCode(this, true)" /></p>
<script type="text/javascript">
    var clock = '';
    var nums = 60;
    var btn;
    function sendCode(thisBtn, needSend)
    {   
        btn = thisBtn;
        btn.disabled = true; //将按钮置为不可点击
        btn.value = nums+'秒后可重新获取';
        clock = setInterval(doLoop, 1000); //一秒执行一次
        if(needSend){
	        $.ajax({
		  		url:"/quqi/wifi/v1/user/authCode",
		  		method: "POST",
		  		contentType: "application/json",
		  		data: '{"macAddress":"${macAddress}", "phoneNumber":"${phoneNumber}"}',
		  		dataType: "json"
			}).success(function() {
			});
		}
        
    }
    function doLoop()
    {
        nums--;
        if(nums > 0){
            btn.value = nums+'秒后可重新获取';
        }else{
            clearInterval(clock); //清除js定时器
            btn.disabled = false;
            btn.value = '重新发送>';
            nums = 60; //重置时间
        }
    }
</script>
<style>

        body{
            -webkit-tap-highlight-color: transparent;
        }
        .box{text-align: center;}
        .input1{
            margin: 0;
            padding: 0;
            width: 1px;
            opacity: 0;
            height: 1px;
            border: none;
        }
        label{
            display: block;
        }
        ul{
            font-size: 0;
            display: inline-block;
            position: relative;
            font-size: 0;
        }
        ul li{
            border-radius: 5px;
            display: inline-block;
            width: 40px;
            height: 40px;
            margin-right: 3px;
            font-size: 16px;
            font-weight: 700;
            text-align: center;
            line-height: 40px;
            vertical-align: middle;
            overflow: hidden;
            background: #efefef;
        }
        ul li:first-child {
            border-left: none;
        }

    </style>
<script src="/js/jquery.1.11.1.js"></script>
<script>
    $('input').on('input', function (e){
        var numLen = 4;
        var pw = $('#ipt').val();
        var list = $('li');
        for(var i=0; i<numLen; i++){
            if(pw[i]){
                $(list[i]).text(pw[i]);
            }else{
                $(list[i]).text('');
            }
        }
        if(pw.length==4){
	        $.ajax({
		  		url:"/quqi/wifi/v1/user/",
		  		method: "POST",
		  		contentType: "application/json",
		  		data: '{"macAddress":"${macAddress}","store":"${store}", "phoneNumber":"${phoneNumber}", "authCode":"'+pw+'"}',
		  		dataType: "json"
			}).fail(function() {
				//console.log('failed');
				$('#ipt').val('');
				for(var i=0; i<numLen; i++){
                	$(list[i]).text('');
		        }
				
			});
        }
    });
</script>
</body>
</html>