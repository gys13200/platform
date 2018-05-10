
Ext.define('Platform-web.view.system.role.SysRole', {
    extend: 'Ext.grid.Panel',

    requires: [
        'Ext.window.MessageBox',
        'Platform-web.view.system.role.SysRoleController',
        'Platform-web.view.system.role.SysRoleModel',
        'Platform-web.view.system.role.SysRoleStore',
        'Platform-web.view.system.role.SysRoleWin'
    ],
    xtype: 'sys-role',
    border : true,
    viewModel : {
        type : 'SysRoleModel'
    },
    controller : 'SysRoleController',

    bind : {
        store : '{SysRoleStore}',
        //controller : '{SysRoleController}'
    },
    columns : [
        {xtype : 'rownumberer'},
        {text: 'ID', dataIndex : 'id', flex : 1},
        {text: '角色名称', dataIndex : 'name', flex : 1},
        {text: '主页', dataIndex : 'homePage', flex : 1},
        {text: '主页标题', dataIndex : 'homePageTitle', flex : 1},
        {text: '描述', dataIndex : 'description', flex : 2}
    ],

    tbar :[
        {xtype: "textfield", fieldLabel: "角色名称",labelAlign: 'right'},
        {xtype: "button", text: "搜索", glyph: 0xf002, handler: "search"},
        "->",
        {xtype: "button", text: "新增", glyph: 0xf0fe, handler: "add"},
        {xtype: "button", text: "修改", glyph: 0xf044, handler: "search"}
    ],
    bbar : [
        {
            xtype: 'pagingtoolbar',
            bind: {
                store: "{SysRoleStore}"
            },
            displayInfo: true
        }
    ],
    initComponent : function () {
        Ext.setGlyphFontFamily('FontAwesome');
        var me = this;
        if(me.store){
            me.store.reload();
        }
        this.callParent(arguments);
    }

});
