// 判断按钮权限
function buttonPermissions(permissionsUrl) {
    if (permissionsUrl) {
        let buttons = JSON.parse(sessionStorage.getItem("X-Data-Buttons-List"));
        if (buttons) {
            return buttons.indexOf(permissionsUrl) > -1;
        }
    }
    return false;
}

// 获取字典内容
function dictItem(dictCode) {
    if (dictCode) {
        let dictList = JSON.parse(sessionStorage.getItem("X-Data-Dict-List"));
        if (dictList) {
            return dictList.find(dict => dict.dictCode === dictCode).itemList;
        }
    }
    return false;
}

// 设置剪贴板文本
function setClipboardText(event) {
    // 阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
    event.preventDefault();
    const node = document.createElement('div');
    // 对 documentfragment 不熟，不知道怎么获取里面的内容，用了一个比较笨的方式
    node.appendChild(window.getSelection().getRangeAt(0).cloneContents());
    // getRangeAt(0) 返回对基于零的数字索引与传递参数匹配的选择对象中的范围的引用。对于连续选择，参数应为零。
    const htmlData = '<div>'
        + node.innerHTML
        + '<br/><br/>著作权归作者所有。<br/>'
        + '商业转载请联系作者获得授权，非商业转载请注明出处。<br/>'
        + '作者：XinLau<br/>链接：https://lau.xin/<br/>'
        + '来源：E-Learning<br/><br/>'
        + '</div>';
    const textData = window.getSelection().getRangeAt(0)
        + '\n\n著作权归作者所有。\n'
        + '商业转载请联系作者获得授权，非商业转载请注明出处。\n'
        + '作者：XinLau\n'
        + '来源：E-Learning\n\n';
    if (event.clipboardData) {
        event.clipboardData.setData("text/html", htmlData);
        // setData(剪贴板格式, 数据) 给剪贴板赋予指定格式的数据。返回 true 表示操作成功。
        event.clipboardData.setData("text/plain", textData);
    } else if (window.clipboardData) {
        // window.clipboardData 的作用是在页面上将需要的东西复制到剪贴板上，提供了对于预定义的剪贴板格式的访问，以便在编辑操作中使用。
        return window.clipboardData.setData("text", textData);
    }
};

// 添加复制事件监听器
document.addEventListener('copy', function (e) {
    setClipboardText(e);
});