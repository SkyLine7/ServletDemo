// Vue 的模版语法： 使用Mustache 的语法

// 1.插值：{{ }}
// <span>Message: {{ msg }}</span>,使用v-once 指令一次性的插值，当数据改变时，插值处的内容不会变更，但这会影响到该节点上的其它数据绑定


// 2. 原始的html: v-html
// 如果要输出真正的 HTML，需要使用 v-html 指令：
// <p>Using v-html directive: <span v-html="rawHtml"></span></p>

// 3. Mustache 语法不能作用在 HTML 特性上,这种情况应该使用 v-bind 指令：
// <div v-bind:id="dynamicId"></div>


// 4. 使用JavaScript 表达式
//{{ number + 1 }}

//{{ ok ? 'YES' : 'NO' }}

//{{ message.split('').reverse().join('') }}

//<div v-bind:id="'list-' + id"></div>


// 5.指令 (Directives) 是带有 v- 前缀的特殊特性。指令特性的值预期是单个 JavaScript 表达式 (v-for 是例外情况)。
// 指令的职责是，当表达式的值改变时，将其产生的连带影响，响应式地作用于 DOM。例如

// <p v-if="seen">现在你看到我了</p>


// 6.一些指令能接收一个"参数"，在指令名称之后以冒号表示. 例如

//这里 href 是参数，告知 v-bind 指令将该元素的 href 特性与表达式 url 的值绑定。
// <a v-bind:href="url">...</a>
// <a v-on:click="doSomething">...</a>


// 7. 修饰符
//是以半角句号 . 指明的特殊后缀，用于指出一个指令应该以特殊方式绑定。
// 例如，.prevent 修饰符告诉 v-on 指令对于触发的事件调用 event.preventDefault()：
// <form v-on:submit.prevent="onSubmit">...</form>


// 8. 缩写

// v-bind 和 v-on 这两个最常用的指令，提供了特定简写：
// <!-- 完整语法 -->
//<a v-bind:href="url">...</a>

// <!-- 缩写 -->
//<a :href="url">...</a>


// <!-- 完整语法 -->
//<a v-on:click="doSomething">...</a>

// <!-- 缩写 -->
//<a @click="doSomething">...</a>