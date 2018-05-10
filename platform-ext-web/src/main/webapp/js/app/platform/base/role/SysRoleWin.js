Ext.define('app.platform.base.role.SysRoleWin', {

    extend: 'Ext.window.Window',
    xtype: 'sysRoleWin',
    requires: [
        'app.platform.base.role.SysRoleController',
        'app.platform.base.role.SysRoleModel'
    ],
    controller: 'SysRoleController',
    title: '你好',
    bodyPadding: 10,
    layout: 'form',
    resizable: false,
    constrain: true,
    modal: true,
    width: '60%',
    defaults: {
        anchor: '95%'
    },
    items: [{
        xtype: 'form',
        layout: 'form',
        waitMsgTarget: true,
        reference: 'sysrole-form',
        anchor: '99%',
        items: [
            {
                xtype: 'fieldset',
                title: '用户信息',
                defaultType: 'textfield',
                defaults: {
                    anchor: '99%',
                    //labelWidth: 60,
                    labelAlign: 'left',
                    allowBlank: false
                },
                items: [
                    {
                        name: "name",
                        fieldLabel: "角色名称"

                    }, {
                        name: "homePageTitle",
                        fieldLabel: "主页标题"
                    }, {
                        name: "homePage",
                        fieldLabel: "主页"
                    }, {
                        xtype: "textarea",
                        name: "name",
                        fieldLabel: "描述"
                    }
                ]
            }, {
                xtype: 'fieldset',
                title: '授权信息',
                defaultType: 'textfield',
                defaults: {
                    anchor: '99%',
                    //labelWidth: 60,
                    labelAlign: 'left',
                    allowBlank: false
                },
                items: [{
                    fieldLabel: '权限设置',
                    xtype: 'combotree',
                    storeUrl: 'function/function_menu_ext.do',
                    name: 'resIDS',
                    showDetail: true,
                    selectModel: 'leaf',
                    cascade: 'both',
                    checkModel: 'multiple',
                    rootVisible: false
                }]
            }
        ]
    }],
    dockedItems: [{
        xtype: 'toolbar',
        dock: 'bottom',
        items: [
            '->',
            {text: '保存', handler: 'save'},
            {text: '取消', handler: 'cancel'}
        ]
    }, {
        xtype: 'statusbar',
        dock: 'bottom',
        reference: 'form-statusbar',
        defaultText: ''
    }]

});