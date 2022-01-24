// 判断按钮权限
function buttonPermissions(permissionsUrl) {
    if (permissionsUrl) {
        let buttons = JSON.parse(localStorage.getItem("X-Data-Buttons-List"));
        if (buttons) {
            return buttons.indexOf(permissionsUrl) > -1;
        }
    }
    return false;
}

// 获取字典内容
function dictItem(dictCode) {
    if (dictCode) {
        let dictList = JSON.parse(localStorage.getItem("X-Data-Dict-List"));
        if (dictList) {
            return dictList.find(dict => dict.dictCode === dictCode).itemList;
        }
    }
    return false;
}