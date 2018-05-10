/**
 * This class is the view model for the Main view of the application.
 */
Ext.define('app.platform.main.model.MainModel', {
    extend : 'Ext.app.ViewModel',

    alias : 'viewmodel.main',

    data : {
        system : {
            name : '工程项目合同及资金管理系统',
            version : '5.2014.06.60',
            iconUrl : ''
        },
        user: {
            name: '张三',
            year: '2018'
        },
        // 系统菜单的定义，这个菜单可以是从后台通过ajax传过来的
        systemMenu : []

    },

    // 根据data.systemMenu生成菜单条和菜单按钮下面使用的菜单数据
    getMenus: function () {

        if(this.data.systemMenu.length == 0){
            this.loadMenu();
        }

        var items = [];
        var menuData = this.get('systemMenu'); // 取得定义好的菜单数据
        Ext.Array.each(menuData, function (group) { // 遍历菜单项的数组
            var submenu = [];
            // 对每一个菜单项，遍历菜单条的数组
            Ext.Array.each(group.items, function (menuitem) {
                submenu.push({
                    mainmenu: 'true',
                    moduleName: menuitem.module,
                    text: menuitem.text,
                    icon: menuitem.icon,
                    glyph: menuitem.glyph,
                    handler: 'onMainMenuClick' // MainController中的事件处理程序
                })
            });
            var item = {
                text: group.text,
                menu: submenu,
                icon: group.icon,
                glyph: group.glyph
            };
            items.push(item);
        });
        return items;
    },

    loadMenu: function () {
        var that = this;
        Ext.Ajax.request({
            async: false,
            url: '/data/json/menu.json',
            timeout: 60000,
            success: function (response, opts) {
                that.data.systemMenu = Ext.decode(response.responseText);
            },
            failure: function (response, opts) {
                alert("菜单加载失败");
            }
        });
    }

});