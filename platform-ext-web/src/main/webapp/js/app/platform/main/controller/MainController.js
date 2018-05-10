Ext.define('app.platform.main.controller.MainController', {
    extend: 'Ext.app.ViewController',
    requires: [
        'Ext.window.MessageBox'
    ],
    alias: 'controller.main',

    uses: ['app.platform.base.module.Module'],
    routes: {},
    control: {

    },
    onMainMenuClick: function (btn) {
        var maincenter = this.getView().down('maincenter');

        maincenter.setActiveTab(maincenter.add({
            xtype : 'modulepanel',
            // 将当前的选中菜单的 "模块名称" 加入到参数中
            moduleName : btn.moduleName,
            closable : true,
            reorderable : true
        }));
    },
    // 单击了顶部的 首页 按钮
    onHomePageButtonClick : function(menuitem) {
        this.getView().down('maincenter').setActiveTab(0);
    }
});