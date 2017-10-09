<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
response.flushBuffer();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="/business_union/resources/css/frame.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<meta name="description" content="亿企联|一点点|一起联" >
<meta name="keywords" content="亿企联|一点点|一起联" >
<meta name="baidu-site-verification" content="v4SVMECT1g" />

<title>亿企联&nbsp;&nbsp;一点点&nbsp;&nbsp;一起联</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<script type="application/x-javascript">
			 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
		</script>
<meta name="keywords" content="">
<script type="text/javascript"
	src="${path}/resources/js/Plugins/msgbox/msgbox.js"></script>
<script type="text/javascript"
	src="${path}/resources/js/Plugins/msgbox/msgUtil.js"></script>
	
<script type="text/javascript" src="${path}/resources/js/DD_roundies_min.js"></script>
<script type="text/javascript" src="${path}/resources/js/jQueryUtil/jquery.cookie.js"></script>
<style rel="stylesheet">
	.logo{border:none }
	.footer_anquan img{display:inline-block;}
</style>
</head>
<body style="background:#fff">


	<div class="top clearfix">
        <img src="${path }/resources/images/logo.png" class="logo"/>
        <div class="help">
            <a href="http://www.yidiandian.net" class="little" target="_blank">一点点官网</a> 
            <span class="v_line">|</span> 
            <a href="${path }/help/jumpToHelpView.do" class="help_a" target="_blank">帮助</a> 
        </div>
    </div>


	<div class="login_bg">
        <div class="login_content">        	
            <div class="login">
            	<div class="login_opacity"></div>
            	<div class="login_div">
                    <div class="welconme_login">欢迎登陆</div>
                    <form name='loginForm' id="loginForm" action="<%=request.getContextPath()%>/login.do" method='post'>
                    	
                    	<!--error tips start-->
	                    <div class="login_error" style="display: none;">
	                    	<div class="error_icon">
	                    		<img src="${path }/resources/images/error_close.png" />
	                    	</div>
	                    	<div class="error_content">
	                    		${ErrorMessage}
	                    	</div>
	                    </div>
	                    <!--error tips end-->
                    
                        <div class="user clearfix">                        
                            <img class="user_icon" src="${path }/resources/images/login/login_user_name_icon.png">
                            <input type="text" name="username" id="username" value="${errorUsername }" placeholder="请输入邮箱或者手机号码" />                         
                        </div>
                        
                        <div class="user clearfix pswd_margin_top ">                        
                            <img class="user_icon" alt="" src="${path }/resources/images/login/login_user_pswd_icon.png" />
                           <input type="password" name="password" placeholder="请输入登录密码" id="password" />                      		
                        </div>
                        
                        <div class="about_pswd clearfix">                   	
                                <div class="remeber_pswd" id="remeber_pswd">
                                	<div id="trueDiv" style="display: none;"><img src="${path }/resources/images/checkout_ture.png" id="trueImg"/></div>
                                    <div id="falseDiv"><img src="${path }/resources/images/checkout_false.png" id="falseImg"/></div>
                                </div>
                                <label onclick="clickRememberMe();">记住帐号</label>
                                <input id="rememberUserName" name="rememberUserName" type="hidden" value="1"/>  
                                <span class="forgot_pswd"><a href="${path }/store/toForgetFirst.do" target="_blank">忘记密码？</a></span>    
                                                   
                        </div>
                        
                        
                        
                        <div class="check_code clearfix">
                        
                            <input type="text" name="veryCode" placeholder="请输入验证码" id="veryCode" maxlength="4" />
                            
                            <img id="imgObj" alt="" src="${path}/verifyCode.jpeg" onclick="changeImg()"/>
                                                  
                        </div>
                        
                       
                        
                        <div class="login_submit">                        
                            <input type="button" value="登录" onclick="ajaxLogin_post()" />                      
                        </div>
                        
                        <div class="register">
                        		 没有账号？<a href="javascript:void(0);" onclick="openPage('2');" >立即注册</a>
                        </div>                       
                    </form>
               	</div>        
          	</div>
        </div>       
    </div>
    
    
    <!-- system notice start-->     
    <div class="system_notice clearfix" id="sysNoticeDiv">
       
    </div>   
    <!-- system notice end-->

	 <!--success cases start-->    
     <div class="success_case_title"> 
     	成功案例 
     </div> 
     <div class="succes_cases"> 
        <ul class="succes_cases_list"> 
            <!-- <li><div class="div_circle"><img src=""/></div><div class="case_name">中国银行</div></li> 
            <li><div class="div_circle"><img src=""/></div><div class="case_name">中国银行</div></li> 
             <li><div class="div_circle"><img src=""/></div><div class="case_name">中国银行</div></li> 
             <li><div class="div_circle"><img src=""/></div><div class="case_name">中国银行</div></li> 
             <li><div class="div_circle"><img src=""/></div><div class="case_name">中国银行</div></li> 
            <li><div class="div_circle"><img src=""/></div><div class="case_name">中国银行</div></li> 
            <li><div class="div_circle"><img src=""/></div><div class="case_name">中国银行</div></li>  -->
         </ul>    
    </div> 
    <!--success cases end-->
    
    <div style="height:200px;"></div>

	<div class="footer">
		<span class="footer_anquan"><a  key ="5952287cefbfb024e6458ddb"  logo_size="83x30"  logo_type="business"  href="http://www.anquan.org" ><script src="//static.anquan.org/static/outer/js/aq_auth.js"></script></a>
			<a  key ="5952287cefbfb024e6458ddb"  logo_size="83x30"  logo_type="common"  href="http://www.anquan.org" ><script src="//static.anquan.org/static/outer/js/aq_auth.js"></script></a>
			</span><span style="display:inline-block">copyright@2015.亿企联All rights reserved.<br/>
			<!-- 	   	 粤ICP备15050427号-4 -->
		   	 <a style="color:#6C3365" href="http://www.miitbeian.gov.cn" target="_blank">粤ICP备15050427号-4</a>
	   	 </span>
	</div>
	<script>
	
	
	document.onkeydown=keyListener;
	function keyListener(e){
	    e = e ? e : event;// 兼容FF
	    if(e.keyCode == 13){
	    	ajaxLogin_post();
	    }
	}

	$(document).ready(function(c) {
		
		var uName = $.cookie('username');    
	     if(uName != null && uName != "null" && uName != "") {  
	     	$("#username").val(uName);  
	        $("#falseDiv").hide();	
			$("#trueDiv").show();
			$("#rememberUserName").val("0"); 
	     } 
		var ErrorMessage = '${ErrorMessage}';
		if(ErrorMessage != null && ErrorMessage != ""){
			$(".login_error").show();
		}
		DD_roundies.addRule('.div_circle', '45px 45px 45px 45px', true);
		$('.close').on('click', function(c){
			$('.login-form').fadeOut('slow', function(c){
		  		$('.login-form').remove();
			});
		});	  
		// 勾选记住密码
		$("#falseImg").on("click",function(){
			$("#falseDiv").hide();	
			$("#trueDiv").show();
			$("#rememberUserName").val("0");
			
		});
		// 取消勾选记住密码
		$("#trueImg").on("click",function(){
			$("#falseDiv").show();	
			$("#trueDiv").hide();
			$("#rememberUserName").val("1");
		});
		$("#check_code").onlyNum();
		findSysNotice();
		findSucc();
	});
	
	function changeImg(){    
	    var imgSrc = $("#imgObj");    
	    var src = imgSrc.attr("src");    
	    imgSrc.attr("src",chgUrl(src));    
	}
	
	function chgUrl(url){
	    var timestamp = (new Date()).valueOf();    
//	     url = url.substring(0,17);
	    if((url.indexOf("&")>=0)){
	        url = url + "×tamp=" + timestamp;
	    }else{
	        url = url + "?timestamp=" + timestamp;    
	    }
	    return url;
	}
	
	function ajaxLogin_post(){
		var userName = $("#username").val();
		var password = $("#password").val();
		var veryCode = $("#veryCode").val();
		if(userName == null || userName == "" || userName == "请输入邮箱登陆"){
			setErrorTips("用户名不能为空");
			$("#userName").focus();
			return;
		}
		if(password == null || password == ""){
			setErrorTips("密码不能为空");
			$("#password").focus();
			return;
		}else if(password.length < 6){
			setErrorTips("密码格式错误");
			$("#password").focus();
			return;
		}
		if(veryCode == null || veryCode == "" || veryCode == "请输入验证码"){
			setErrorTips("验证码不能为空");
			$("#veryCode").focus();
			return;
		}
		var rememberMe = $("#rememberUserName").val();
		if(rememberMe == "0"){
			if(userName != null && userName != ""){
				$.cookie('username', userName, {expires:30});
			}
		}else{
			if(userName != null && userName != ""){
				$.cookie('username', "");
			}
			
		}
		$("#loginForm").submit();
	}
	
	function openPage(type){
		if(type == "1"){
			window.location = webRoot + "/store/toForgetFirst.do";
		}else if(type == "2"){
			window.location = webRoot + "/store/toRegisterView.do";
		}
		
	}
	
	function setErrorTips(errorMessage){
		$(".error_content").html(errorMessage);
		$(".login_error").show();
	}
	
	function clickRememberMe(){
		var userName = $("#username").val();
		var rememberMe = $("#rememberUserName").val();
		if(rememberMe == "1"){
			$("#falseDiv").hide();	
			$("#trueDiv").show();
			$("#rememberUserName").val("0");
		}else{
			$("#falseDiv").show();	
			$("#trueDiv").hide();
			$("#rememberUserName").val("1");
		}
	}
	
	
	function findSysNotice(){
		$("#sysNoticeDiv").html("");
		var url_ = webRoot + "/user/method/findSysNotice.do";
		$.ajax({
			type : "POST",
			async : false,
			dataType : "json",
			url : url_,
			success : function(data) {
				var html_ = "<div class='system_notice_name'>系统公告</div><ul>";
				if(data != null){
					for(var i = 0; i < data.length; i++){
						html_ += "<li><a href='${path}/sysNotice/page/toSystemDetail.do?sysNoticeId="+data[i].id+"' target='_blank'>";
						html_ += data[i].noticeTitle;
						html_ += "</a></li>";
					}
				}else{
					html_ += "<li>暂无最新公告信息</li>";
				}
				html_ += "</ul><div class='more'> <a href='${path}/sysNotice/page/toSystem.do' target='_blank'>查看更多&gt;&gt;</a></div>";
				$("#sysNoticeDiv").html(html_);
			},
			error : function(e) {

			}
		});
		
	}
	
	function findSucc(){
		var url_ = webRoot + "/user/method/findSucc.do?current_date=" + new Date().getTime();
		$.ajax({
			type : "POST",
			async : false,
			dataType : "json",
			url : url_,
			success : function(data) {
				var ulhtml = "";
				if(data!=null){
					for(var i=0;i<data.length;i++){
					
	 				ulhtml+="<li><div class='div_circle'><img src="+data[i].enterpriseLogo+"></div><div class='case_name'>"+data[i].enterpriseName+"</div></li> ";
				}
				}
					$(".succes_cases_list").html(ulhtml);
					
			},
			error : function(e) {

			}
		});
	}
	
</script>
</body>
</html>