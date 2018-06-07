//几乎所有的Vue组件都是Vue实例，接收相同的选项对象

// 数据属性
//一个数据对象
var ourData = {a: 1};

//将此对象加入Vue实例中
var vm = new Vue({
    data: ourData
});

//获得Vue实例的属性，返回原数据中对应的字段
console.log(vm.a == ourData.a);


//Vue实例重新设置属性的值会影响到原始数据
vm.a  = 2;
console.log(ourData.a);

//反之亦然
ourData.a = 3;
console.log(vm.a);

// 当这些数据改变时，视图会进行重新渲染。
// 值得注意的是只有当实例被创建时 data 中存在的属性才是响应式的。也就是说如果你添加一个新的属性，比如：vm.b = 'h1';
// 对b的改动不会触发任何视图的更新


///////////////////////////////////////////////////////////////////////////////////////////////////////////////

//实例属性，以前缀$开头

var data = { a: 1 }
var vm = new Vue({
    el: '#example',
    data: ourData
})

vm.$data === data // => true
vm.$el === document.getElementById('example') // => true

// $watch 是一个实例方法
vm.$watch('a', function (newValue, oldValue) {
    // 这个回调将在 `vm.a` 改变后调用
})