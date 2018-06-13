/**
 * 通用 ajax方法
 * @author pcongda
 * 
 */

(function(window,document){
	"use strict";  //启用严格模式
	//定义全局变量
	var self = null;
	//判断是否为数值类型
	function isNumber(val){
	    if(val === "" || val ==null){
	        return false;
	    }
	    if(!isNaN(val)){
	        return true;
	    }else{
	        return false;
	    }
	}
	//GET请求拼接字符串:e.g:&A=a&B=b
	function toUrlData(obj){
	    if (obj == null){
	        return obj;
	    }
	    if(typeof obj === "object"){
	    	var arr = [];
		    for (var i in obj){
		        var str = i+"="+obj[i];
		        arr.push(str);
		    }
		    return arr.join("&");
	    }else{
	    	throw new Error("参数不是对象类型!");
	    }
	} 
	
	//默认传入参数
	var defaultSettings = {
		url: null,
		data: null,
		type: "POST",
		async: true,
		timeOut: 1000 * 30 * 60,
		responseType: 'json',
		header: {}, //请求头
		callback: null, //回调函数
		progress: null //进度函数
	};
	
	//构造方法
	function AjaxCommon(){
		self = this;
	}
	
	//原型方法
	AjaxCommon.prototype = {
		_inital: function(options){
			//判断是否传入了参数
			if(!options) {
	            throw new Error("请传入配置参数");
	        }
			//合并参数:浅拷贝，合并对象属性
			self.defaultSettings = Object.assign({},defaultSettings,options,true);
			//定义错误信息
			self.error = null;
			//判断参数是否传入正确
			if(self.defaultSettings 
			   && self.defaultSettings.url 
			   && self.defaultSettings.data 
			   && typeof(self.defaultSettings.responseType) === "string"
			   && (self.defaultSettings.type.toUpperCase() == "GET" || self.defaultSettings.type.toUpperCase() == "POST")  //put,delete请求先不管
			   && isNumber(self.defaultSettings.timeOut)
			   && typeof(self.defaultSettings.async) === "boolean"){
				
				//判断请求头是否传入正确
		    	for(var i in self.defaultSettings.header){
		    		if(typeof(self.defaultSettings.header[i]) !== "string"){
				    	throw new Error("header参数类型不对!");
		    		}
		    	}
				console.log("参数校验成功!");
			}else{
				throw new Error("参数类型不对!");
			}	
		},	
		
		//GET请求
		getGetAjax: function(options){
			//开启遮罩层
			DialogHelper.Loading(true);
			//参数校验
			self._inital(options);
			//创建xhr对象
			var xhr = new XMLHttpRequest();
			//设置超时时间:单位:毫秒(ms)
			xhr.timeout = self.defaultSettings.timeOut;
			//设置期望响应返回格式：JSON对象
			xhr.responseType = self.defaultSettings.responseType;
			//创建异步GET请求
			xhr.open("GET",self.defaultSettings.url + '?' + toUrlData(self.defaultSettings.data) + '&rdm=' + Math.random(),self.defaultSettings.async);
			//设置固定请求头:
			xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		    xhr.setRequestHeader("Cache-Control", "no-cache");
		    
		    //设置用户添加的请求头
	    	for(var i in self.defaultSettings.header){
		    	xhr.setRequestHeader(i,self.defaultSettings.header[i]);
	    	}
		    
			//注册请求加载完成回调函数
		    xhr.onload = function(e){
				if(xhr.readyState==4){
					var res = this.response;
					//关闭遮罩层
                    DialogHelper.Loading(false);
                    if(this.status==200 && res.code==200){
						self.defaultSettings.callback(this.status,this.response);
					}else {
						DialogHelper.Fail(res.msg);
					}
				}	
			};
			//注册超时回调函数
			xhr.ontimeout = function(e){
                DialogHelper.Fail("请求超时，请重试！");

            };
			//注册异常回调函数
			xhr.onerror = function(e){
                DialogHelper.Fail("服务器异常，请重试！");
            };
			
			/*//获取请求进度回调函数:上传文件时适用
			xhr.upload.addEventListener("progress",function(e){
				console.log("progress");
				console.log(e.loaded); //当前进度
				console.log(e.total); //总进度
				var percent = Math.floor(e.loaded/e.total*100); //进度百分比
				self.defaultSettings.progress(percent);
			},false);*/
			
			//获取请求进度回调函数:上传文件时适用
			xhr.upload.onprogress = function(e){
				console.log("progress");
				console.log(e.loaded); //当前进度
				console.log(e.total); //总进度
				var percent = Math.floor(e.loaded/e.total*100); //进度百分比
				self.defaultSettings.progress(percent);
			};
			
			//发送数据
			try{
				xhr.send(null);
			}catch(e){
				this.error = "发送数据失败，请重试!";
			};
		},
		
		//POST请求
		getPostAjax: function(options){
			DialogHelper.Loading(true);
			self._inital(options);
			var xhr = new XMLHttpRequest();
			//设置超时时间:单位:毫秒(ms)
			xhr.timeout = self.defaultSettings.timeOut;
			//设置期望响应返回格式：JSON对象
			xhr.responseType = self.defaultSettings.responseType;
			//创建异步post请求
			xhr.open("POST",self.defaultSettings.url + '?rdm=' + Math.random(),self.defaultSettings.async);
			//设置请求头:
			xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		    xhr.setRequestHeader("Cache-Control", "no-cache");
		    
		    //设置请求头
	    	for(var i in self.defaultSettings.header){
		    	xhr.setRequestHeader(i,self.defaultSettings.header[i]);
	    	}
		    
			//注册请求加载完成回调函数
		    xhr.onload = function(e){
				if(xhr.readyState==4){
					var res = this.response;
                    DialogHelper.Loading(false);
                    if(this.status==200 && res.code==200){
						self.defaultSettings.callback(this.status,this.response);
					}else {
                        DialogHelper.Fail(res.msg);
					}
				}	
			};
			//注册超时回调函数
			xhr.ontimeout = function(e){
                DialogHelper.Fail("请求超时，请重试！");
			};
			//注册异常回调函数
			xhr.onerror = function(e){
                DialogHelper.Fail("服务器异常，请重试！");
			};
			
			/*//获取请求进度回调函数:上传文件时适用
			xhr.upload.addEventListener("progress",function(e){
				console.log(e.loaded); //当前进度
				console.log(e.total); //总进度
				var percent = Math.floor(e.loaded/e.total*100); //进度百分比
				self.defaultSettings.progress(percent);
			},false);*/
			
			//获取请求进度回调函数:上传文件时适用
			xhr.upload.onprogress = function(e){
				console.log(e.loaded); //当前进度
				console.log(e.total); //总进度
				var percent = Math.floor(e.loaded/e.total*100); //进度百分比
				self.defaultSettings.progress(percent);
			};
			
			//发送数据:
			try{
				xhr.send("jsonData=" + JSON.stringify(self.defaultSettings.data));
			}catch(e){
				this.error = "发送数据失败，请重试!";
			};
		},
		
		
		//formData上传
		getAjaxWithFile:function(options){
			DialogHelper.Loading(true);
			self._inital(options);
			//创建formData对象
			var formData = new FormData();
			//添加数据
			formData.append("jsonData",self.defaultSettings.data);
			var xhr = new XMLHttpRequest();
			//设置超时时间:单位:毫秒(ms)
			xhr.timeout = self.defaultSettings.timeOut;
			//设置期望响应返回格式：JSON对象
			xhr.responseType = self.defaultSettings.responseType;
			//创建异步post请求
			xhr.open(self.defaultSettings.type,self.defaultSettings.url + '?rdm=' + Math.random(),self.defaultSettings.async);
			//设置请求头:
			xhr.setRequestHeader("Content-Type", "multipart/form-data");
		    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		    xhr.setRequestHeader("Cache-Control", "no-cache");
		    //设置请求头
	    	for(var i in self.defaultSettings.header){
		    	xhr.setRequestHeader(i,self.defaultSettings.header[i]);
	    	}
		    
			//注册请求加载完成回调函数
		    xhr.onload = function(e){
				if(xhr.readyState==4){
					var res = this.response;
                    DialogHelper.Loading(false);
                    if(this.status==200 && res.code==200){
						self.defaultSettings.callback(this.status,this.response);
					}else {
                        DialogHelper.Fail(res.msg);
                    }
				}	
			};
			//注册超时回调函数
			xhr.ontimeout = function(e){
                DialogHelper.Fail("请求超时，请重试！");
            };
			//注册异常回调函数
			xhr.onerror = function(e){
                DialogHelper.Fail("服务器异常，请重试！");
			};
			
			//获取请求进度回调函数:上传文件时适用
			xhr.upload.onprogress = function(e){
				console.log(e.loaded); //当前进度
				console.log(e.total); //总进度
				var percent = Math.floor(e.loaded/e.total*100); //进度百分比
				self.defaultSettings.progress(percent);
			};
			//发送数据:
			try{
				xhr.send(formData);
			}catch(e){
				this.error = "发送数据失败，请重试!";
			};
		}
	}
	//将对象放入window对象中(接口暴露给外部)
	window.AjaxCommon = AjaxCommon;
})(window,document);