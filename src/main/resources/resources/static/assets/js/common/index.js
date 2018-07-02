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
	
	var ajaxCommon = new AjaxCommon();
	ajaxCommon.getPostAjax({
		url: '/demo1/queryList4',
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


function sendAjax3(){
	$.ajax({
	url:'/demo1/queryList1',
	type:'POST',
	async:true,  
	data:{
	    name: 'yang',
	    age: 25,
	    height: "182"
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


//获取dom元素
let domList = document.querySelectorAll(".act");
//绑定事件监听
for (let i=0;i<domList.length;i++){
	domList[i].addEventListener("click",reset,false);
}
function reset() {
	//5秒内不能第二次提交
	let _self = this;
	let btnText = this.innerHTML;
    let count = 5;
    let timer = window.setInterval(function () {
		if(count > 0){
            _self.classList.remove("btn-info");
            _self.classList.add("btn-default");
            _self.setAttribute("disabled",true);
            _self.innerHTML = ''+count+'秒后启用';
            count--;
        }else if(count == 0){
            _self.removeAttribute("disabled");
            _self.classList.remove("btn-default");
            _self.classList.add("btn-info");
            _self.innerHTML = btnText;
            count--;
		}else{
            window.clearInterval(timer);
		}
    },1000);
}

function addLis() {
    let xlf = document.getElementById('xlf');
    let drop = document.getElementById('drop');
    drop.addEventListener("dragenter", handleDragover, false);
    drop.addEventListener("dragover", handleDragover, false);
    drop.addEventListener("drop", onDropDown, false);

    if(xlf.addEventListener){
        xlf.addEventListener('change', handleFile, false);
    }
}

addLis();

function handleDragover(e) {
    e.stopPropagation();
    e.preventDefault();
    e.dataTransfer.dropEffect = 'copy';
}

function onDropDown(e) {
    e.stopPropagation();
    e.preventDefault();
    let files = e.dataTransfer.files;
    let f = files[0];
    readFile(f);
}

function handleFile(e) {
    let files = e.target.files;
    let f = files[0];
    readFile(f);
}

function readFile(file) {
    let name = file.name;
    let reader = new FileReader();
    reader.onload = function (e) {
        let data = e.target.result;
        let wb = XLSX.read(data, { type: "binary" });
        console.log(wb);

		var fromTo = '';
		var persons = [];
		// 遍历每张表读取
		for (var sheet in wb.Sheets) {
			if (wb.Sheets.hasOwnProperty(sheet)) {
				fromTo = wb.Sheets[sheet]['!ref'];
				console.log(fromTo);
                persons = persons.concat(XLSX.utils.sheet_to_json(wb.Sheets[sheet]));
				//发现json格式不是你想要的你可以
				//XLSX.utils.sheet_to_json(wb.Sheets[sheet],{raw:true, header:1})
				// 如果只取第一张表，就取消注释这行
				$("#hiddata").val(JSON.stringify(persons));
				break; // 如果只取第一张表，就取消注释这行
			}
		}
    };
	// 以二进制方式打开文件
    reader.readAsBinaryString(file);
}