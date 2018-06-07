// 1. 计算属性

var vm = new Vue({
    el: '#countField',
    data: {
        message: 'Hello'
    },
    computed: {
        // 计算属性的 getter
        reversedMessage: function () {
            // this指向 vm 实例
            return this.message.split('').reverse().join('');
        }
    }
});


console.log(vm.reversedMessage);
vm.message = 'Goodbye';
console.log(vm.reversedMessage);


// 2. 计算属性缓存 和 方法的区别
//虽然调用方法也能达到相同的效果
var vm = new Vue({
    el: '#countMethod',
    data: {
        message: 'Hello'
    },
    methods: {
        // 计算属性的 getter
        reversedMessage: function () {
            // this指向 vm 实例
            return this.message.split('').reverse().join('');
        }
    }
});

// 两种方式的最终结果确实是完全相同的。
// 但不同的是计算属性是基于它们的依赖进行缓存的。计算属性只有在它的相关依赖发生改变时才会重新求值。
// 如果换成 Date.now() 时间就不会变。
// 这就意味着只要 message 还没有发生改变，多次访问 reversedMessage 计算属性会立即返回之前的计算结果，而不必再次执行函数。


// 3. 计算属性 和 侦听属性
// Vue 提供了一种更通用的方式来观察和响应 Vue 实例上的数据变动：侦听属性。
// 当你有一些数据需要随着其它数据变动而变动时，你很容易滥用 watch 回调。
// 然而，通常更好的做法是使用计算属性而不是命令式的 watch 回调.



// 4. 计算属性的 setter,比如

var vm = new Vue({
    el: '#demo',
    data: {
        firstName: 'Foo',
        lastName: 'Bar'
    },
    computed: {
        fullName: {
            // getter
            get: function () {
                return this.firstName + ' ' + this.lastName
            },
            // setter
            set: function (newValue) {
                var names = newValue.split(' ')
                this.firstName = names[0]
                this.lastName = names[names.length - 1]
            }
        }
    }
});