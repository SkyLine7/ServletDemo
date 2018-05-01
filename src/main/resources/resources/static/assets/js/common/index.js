

/*function initFun(){
	//创建xhr对象:IE低版本不支持
	var xhr = new XMLHttpRequest();
	//设置超时时间:单位:毫秒(ms)
	xhr.timeout = 1000 * 30 * 60;
	//设置期望响应返回格式：JSON对象
	xhr.responseType = "json";
	//创建异步post请求
	xhr.open("GET",'/ServletDemo/testServlet?method=testInitMethod',true);
	//设置请求头:
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xhr.setRequestHeader("Cache-Control", "no-cache");
	//注册请求加载完成回调函数
	xhr.onload = function(e){
		if(xhr.readyState==4){
			var res = this.response;
			if(this.status==200 && res.code==200 && res.data != undefined){
				window.console.log(this.response);
			}else if(this.status==404 || res.code==404){
				window.location.href = "/ServletDemo/templates/common/404.html";
			}else if(this.status==500 || res.code==500){
				window.location.href = "/ServletDemo/templates/common/500.html";
			}
		}	
	};
	//注册超时回调函数
	xhr.ontimeout = function(e){
		//跳转超时页面
		window.location.replace("/ServletDemo/templates/common/timeout.html");
	};
	//注册异常回调函数
	xhr.onerror = function(e){
		//跳转页面
		window.location.replace("/ServletDemo/templates/common/500.html");
	};
	//获取请求进度回调函数:上传文件时适用
	xhr.upload.onprogress = function(e){
		window.console.log(e.loaded); //当前进度
		window.console.log(e.total); //总进度
		var percent = Math.floor(e.loaded/e.total*100); //进度百分比
		window.console.log(percent);
	};
	//发送数据:
	try{
		xhr.send(null);
	}catch(e){
		window.alert("网络不佳，请重试！");
	};
}*/

/*function sendAjax1(){
	//json串
	var jsonData = {
		"name": "panda",
		"age": 18,
		"hegiht": 183
	};
	//创建xhr对象:IE低版本不支持
	var xhr = new XMLHttpRequest();
	//设置超时时间:单位:毫秒(ms)
	xhr.timeout = 1000 * 30 * 60;
	//设置期望响应返回格式：JSON对象
	xhr.responseType = "json";
	//创建异步post请求
	xhr.open("POST",'/demo1/queryList?rdm=' + Math.random(),true);
	//设置请求头:
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xhr.setRequestHeader("Cache-Control", "no-cache");
	//注册请求加载完成回调函数
	xhr.onload = function(e){
		if(xhr.readyState==4){
			var res = this.response;
			if(this.status==200 && res.code==200 && res.data != undefined){
				window.console.log(this.response);
			}else if(this.status==404 || res.code==404){
				window.location.href = "/templates/common/404.html";
			}else if(this.status==500 || res.code==500){
				window.location.href = "/templates/common/500.html";
			}
		}	
	};
	//注册超时回调函数
	xhr.ontimeout = function(e){
		//跳转超时页面
		window.location.replace("/templates/common/timeout.html");
	};
	//注册异常回调函数
	xhr.onerror = function(e){
		//跳转页面
		window.location.replace("/templates/common/500.html");
	};
	//获取请求进度回调函数:上传文件时适用
	xhr.upload.onprogress = function(e){
		window.console.log(e.loaded); //当前进度
		window.console.log(e.total); //总进度
		var percent = Math.floor(e.loaded/e.total*100); //进度百分比
		window.console.log(percent);
	};
	//发送数据:
	try{
		xhr.send("jsonData=" + JSON.stringify(jsonData)+"&dd=" + 2);
	}catch(e){
		window.alert("网络不佳，请重试！");
	};
}*/


function sendAjax1(){
	
	var jsonData = {
		name: "any",
		age: 88,
		hegiht: "182"
		
	};
	var ajaxCommon = new AjaxCommon();
	
	ajaxCommon.getGetAjax({
		url: '/demo1/queryList1',
		data: jsonData,
		async: true,
		timeOut: 1000 * 30 * 60,
		responseType: "json",
		header: {
			ddd: "AAA",
			bbb: "BBB"
		},
		callback:function(status,res){
			console.log(res);
		},
		progress:function(percent){
			console.log(percent);
		}
	});
}



function sendAjax2(){
	
	var jsonData = {
		name: "any",
		age: 88,
		hegiht: "182"
	};
	
	var formData = new FormData();
	formData.append("jsonData",jsonData);
	
	var ajaxCommon = new AjaxCommon();
	
	ajaxCommon.getPostAjax({
		url: '/demo1/queryList1',
		data: formData,
		async: true,
		timeOut: 1000 * 30 * 60,
		responseType: "json",
		header: {
			ddd: "AAA",
			bbb: "BBB"
		},
		callback:function(status,res){
			console.log(res);
		},
		progress:function(percent){
			console.log(percent);
		}
	});
}




/*function sendAjax2(){
	//创建formData对象
	var formData = new FormData();
	//构造json串
	var jsonData = {
		"name": "panda",
		"age": 18,
		"hegiht": 183
	};
	formData.append("jsonData",JSON.stringify(jsonData));
	formData.append("dd",2);
	//创建xhr对象:IE低版本不支持
	var xhr = new XMLHttpRequest();
	//设置超时时间:单位:毫秒(ms)
	xhr.timeout = 1000 * 30 * 60;
	//设置期望响应返回格式：JSON对象
	xhr.responseType = "json";
	//创建异步post请求
	xhr.open("POST",'/ServletDemo/testServlet?method=testAJaxMethod1',true);
	//设置请求头:
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	//xhr.setRequestHeader("Content-Type", "multipart/form-data");
    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xhr.setRequestHeader("Cache-Control", "no-cache");
	//注册请求加载完成回调函数
	xhr.onload = function(e){
		if(xhr.readyState==4){
			var res = this.response;
			if(this.status==200 && res.code==200 && res.data != undefined){
				window.console.log(this.response);
			}else if(this.status==404 || res.code==404){
				window.location.href = "/ServletDemo/templates/common/404.html";
			}else if(this.status==500 || res.code==500){
				window.location.href = "/ServletDemo/templates/common/500.html";
			}
		}	
	};
	//注册超时回调函数
	xhr.ontimeout = function(e){
		//跳转超时页面
		window.location.href = "/ServletDemo/templates/common/timeout.html";
	};
	//注册异常回调函数
	xhr.onerror = function(e){
		//跳转页面
		window.location.href = "/ServletDemo/templates/common/500.html";
	};
	//获取请求进度回调函数:上传文件时适用
	xhr.upload.onprogress = function(e){
		window.console.log(e.loaded); //当前进度
		window.console.log(e.total); //总进度
		var percent = Math.floor(e.loaded/e.total*100); //进度百分比
		window.console.log(percent);
	};
	//发送数据:
	try{
		xhr.send(formData);
	}catch(e){
		window.alert("网络不佳，请重试！");
	};
}*/


function sendAjax3(){
	$.ajax({
	url:'/demo1/queryList1',
	type:'POST', //GET
	async:true,    //或false,是否异步
	data:{
	    name: 'yang',
	    age: 25,
	    height: 180
	},
	timeout:1000 * 60 * 30,  //超时时间:ms
	cache: false,
	dataType:'json',    //期望返回的数据格式：json/xml/html/script/jsonp/text
	processData: true,  //序列化data,把{ width:1680, height:1050 }参数对象序列化为width=1680&height=1050这样的字符串。
	//contentType: false, //此处无需设置，使用默认即可
	beforeSend:function(xhr){ 
	    console.log(xhr);
	    console.log('发送前');
	},
	success:function(data,textStatus,jqXHR){
	    console.log(data);
	    console.log(textStatus);
	    console.log(jqXHR);
	},
	error:function(xhr,textStatus,errorThrown){
	    console.log('错误');
	    console.log(xhr);
	    console.log(textStatus);
	},
	complete:function(xhr, textStatus){
	    //可做超时处理
		console.log('结束');  
	    }
	}); 
}

		