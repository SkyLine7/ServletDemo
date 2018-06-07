//声明式渲染
var app = new Vue({
    el: '#app1',
    data: {
        message: 'Hello Vue!'
    }
});


var app1 = new Vue({
    el: '#app2',
    data: {
        message1: '页面加载于' + new Date().toLocaleDateString()
    }
});



//条件
var app3 = new Vue({
    el: '#app3',
    data: {
        seen: true
    }
})


//循环
var app4 = new Vue({
    el: '#app4',
    data: {
        todos: [
            { text: '学习 JavaScript' },
            { text: '学习 Vue' },
            { text: '整个牛项目' }
        ]
    }
});

//处理用户输入
var app5 = new Vue({
    el: '#app5',
    data: {
        message: 'hello Vue.js'
    },
    methods: {
        reverseMessage: function () {
            //字符串反转
            this.message = this.message.split('').reverse().join('')
        }
    }
});


//输入与状态的双向绑定
var app6 = new Vue({
    el: '#app6',
    data: {
        message: 'Hello Vue!'
    }
});