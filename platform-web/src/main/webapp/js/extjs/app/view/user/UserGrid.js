
Ext.define('Platform-web.view.user.UserGridController', {
    extend : 'Ext.app.ViewController',

    alias : 'controller.usergrid',

    dbclick : function (view, record, item, index, e, eOpts) {
        alert("hello " + record.get("name"));
    }
});

Ext.define('Platform-web.view.user.UserGrid', {
    extend : 'Ext.grid.Panel',
    xtype : 'app-usergrid',
    store : 'UserStore',
    controller : 'usergrid',
    title : '用户列表',
    columns : [
        {text: '用户名', dataIndex : 'name', flex : 1},
        {text: '年龄', dataIndex : 'age', flex : 1},
        {text: '电话', dataIndex : 'phone', flex : 1}
    ],
    width: 400,
    border : true,
    listeners : {
        itemdblclick : {
            fn : 'dbclick',
            scope : 'controller'
        }
    }
});