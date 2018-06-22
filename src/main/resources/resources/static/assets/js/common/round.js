let list = document.getElementById('list');
let prev = document.getElementById('prev');
let next = document.getElementById('next');

function animate(offset) {
    //获取的是style.left，是相对左边获取距离，所以第一张图后style.left都为负值，
    //且style.left获取的是字符串，需要用parseInt()取整转化为数字。
    let newLeft = parseInt(list.style.left) + offset;
    list.style.left = newLeft + 'px';
}

prev.onclick = function() {
    animate(600);
}
next.onclick = function() {
    animate(-600);
}
