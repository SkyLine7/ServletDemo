
(function(window,document){
	"use strict";  //启用严格模式
	//弹框通用
	var DialogHelper = {
		
		   Loading: function(b) {
			   if(b) {
				   layer.load(2, {shade: [0.9,'#fff'], time: 30 * 1000});  //30秒后自动关闭
			   }else{
				   //关闭所有加载层
				   layer.closeAll('loading');
			   }
		   },
		   
		   Alert: function(msg, callback) {
			   if(typeof(callback) !== "undefined" && callback != null) {
				   layer.msg(msg, {icon: 1}, callback);
			   }else{
				   layer.msg(msg, {icon: 1});
			   }
		   },  
		   
		   Success: function(msg, callback) {
			   if(typeof(callback) !== "undefined" && callback != null) {
				   layer.msg(msg, {icon: 1}, callback);
			   }else{
				   layer.msg(msg, {icon: 1});
			   }
		   },
		   
		   Fail: function(msg, callback) {
			   if(typeof(callback) !== "undefined" && callback != null) {
				   layer.msg(msg, {icon: 5}, callback);
			   }else{
				   layer.msg(msg, {icon: 5});
			   }
		   },
		   
		   SysError: function() {
			   layer.msg("对不起,服务器出了一点问题,请重试！", {icon: 16});
		   }
	};
	//将对象放入window对象中(接口暴露给外部)
	window.DialogHelper = DialogHelper;
})(window,document);
