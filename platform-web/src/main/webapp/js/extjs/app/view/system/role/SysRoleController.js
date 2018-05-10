Ext.define('Platform-web.view.system.role.SysRoleController', {
    extend: 'Ext.app.ViewController',
    requires: [
        'Ext.window.MessageBox'
    ],
    alias: 'controller.SysRoleController',


    search : function () {
        this.getViewModel().getStore('SysRoleStore').reload();
    },
    add: function () {

        var win = Ext.create('Platform-web.view.system.role.SysRoleWin', {
            title : '角色新增'
        });
        win.show(this.getView());
    },
    save : function (btn) {
        var form_statusbar = this.lookupReference('form-statusbar');
        var sysrole_form = this.lookupReference('sysrole-form');
        if (sysrole_form.getForm().isValid()) {
            form_statusbar.showBusy('loading');
            sysrole_form.getForm().submit({
                waitMsg: '正在执行',
                url : '/user',
                success: function (form, action) {
                    btn.up('sysRoleWin').destroy();
                },
                failure: function (form, action) {
                    Ext.Msg.alert('Failed', action);

                }
            });
        }

        //btn.up('sysRoleWin').destroy();
    },
    cancel: function (btn) {
        btn.up('sysRoleWin').destroy();
    }
});