// 全局通用对象
window.utilsObj = {
    renderSelectOptions: function (data, settings) {
        settings = settings || {};
        let value = settings.value || 'value',
            name = settings.name || 'name',
            selectedVal = settings.selectedValue || '',
            type = settings.type || undefined,
            entity = settings.entity || undefined;
        let html = [];
        for (let i = 0, item; i < data.length; i++) {
            item = data[i];
            html.push('<option value="');
            html.push(item[value]);
            html.push('"');
            if (type) {
                html.push(' type="' + item[type] + '"');
            }
            if (entity) {
                html.push(' entity="' + item[entity] + '"');
            }
            if (selectedVal && selectedVal == item[value]) {
                html.push(' selected="selected"');
            }
            html.push('>');
            html.push(item[name]);
            html.push('</option>');
        }
        return html.join('');
    }
};

/**
 * 函数中的参数为 要获取的cookie键的名称。
 * @param c_name
 * @returns {string}
 */
function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) {
                c_end = document.cookie.length;
            }
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}

/**
 * <P>生成 URL 请求参数</P>
 * @param param
 * @returns {string}
 */
function generateUrlRequestParam(param) {
    let requestParam = '?';
    let arr = []
    for (let key in param) {
        arr.push(key + "=" + param[key]);
    }
    return requestParam + arr.join("&");
}

/**
 * 表格数据行选中增加背景色
 * @param obj
 */
function tableRowBg(obj) {
    // 移除其他当前行的兄弟节点的 <style> 属性
    for (let i = 0; i < $(obj).siblings().length; i++) {
        $(obj).siblings().eq(i).removeAttr('style');
    }
    // 选中行的背景色
    $(obj).attr('style', 'background-color:#e6e6e6;');
}

/**
 * 从响应Json获取表格数据的聚合行
 * @param origin
 * @returns {{}}
 */
function getAggregateObj(origin) {
    let obj = {};
    if (origin != undefined) {
        obj = origin.length > 0 ? origin[0] : {};
    }
    return obj;
}

/**
 * Layui：parseData 回调函数返回结果
 */
function getParesDataResult(res) {
    if (res['status'] == 200) {
        return {
            "code": 0 //解析接口状态
            , "msg": res['message'] //解析提示文本
            , "count": res['data']['totalNum'] //解析数据长度
            , "data": res['data']['items'] //解析数据列表
            //后台返回合计数据
            , "totalRow": getAggregateObj(res['data']['aggregateItems'])
        };
    } else if (res.status == 401) {
        layer.msg(res['message']);
        window.parent.location.href = '/login';
    } else {
        layer.msg(res['message']);
        return {
            "code": 0, //解析接口状态
            "msg": res['message'], //解析提示文本
            "count": 0, //解析数据长度
            "data": [] //解析数据列表
        };
    }
}

/**
 * EChart图表根据容器高度重新渲染
 */
function resizeChart(jqObj, chartIns, height) {
    jqObj.height(height - 40);    // jquery设置高度需要减去40
    chartIns.resize();
}

/**
 * 弹出子页面
 */
function layerOpenSubPage(uri) {
    parent.layer.open({
        type: 2,
        title: "用户信息批量导入",
        area: ['80%', '80%'],
        content: uri,
        btn: ['确定'],
        success: function (layerOpen, index) {//弹出成功后执行
            //弹出的子页面
            let subpage = top.window[layerOpen.find('iframe')[0]['name']];
        },
        yes: function (index, layero) {
            parent.layer.closeAll();
        }
    });
}

/**
 * 获取分页对象：当前页、每页条数
 */
function getCurrPageAndLimit(formParentJqueryObj) {
    let curr = formParentJqueryObj.siblings('.layui-form').find('.layui-laypage-skip .layui-input').val();
    let limit = formParentJqueryObj.siblings('.layui-form').find('.layui-laypage-limits option:selected').val();
    return {curr: curr === undefined ? 1 : curr, limit: limit === undefined ? 10 : limit};
}

/**
 * 获取通用字典列表
 */
function getDictionaryList(uri) {
    let data = [];
    $.ajax({
        type: 'GET',
        async: false,
        url: uri,
        dataType: 'json',
        success: function (obj) {
            if (obj['status'] == 200) {
                data = obj['data'];
            }
        },
        error: function (obj) {
            console.log(obj);
            layer.msg('请求出错！', uri);
        }
    });
    return data;
}

/**
 *  日期格式化
 * */
function formatDate(date) {
    //将2020/2/3这种日期格式，转换为2020-02-03
    let arr = date.toLocaleDateString().split("/");
    if (arr[1].length < 2) {//对月份进行处理：如果月份的长度为1，则拼接上字符串0
        arr.splice(1, 1, "0" + arr[1]);
    }
    if (arr[2].length < 2) {//对天数进行处理：如果天数的长度为2，则拼接上字符串0
        arr.splice(2, 1, "0" + arr[2]);
    }
    return arr.join("-");
}
