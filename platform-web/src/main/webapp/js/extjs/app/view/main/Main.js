Ext.define('Platform-web.view.main.Main', {
    extend: 'Ext.container.Container',

    xtype: 'app-main',

    requires: [
        'Platform-web.view.user.UserGrid',
        'Platform-web.view.main.Header',
        'Platform-web.view.main.MainModel',
        'Platform-web.view.main.MainController',
        'Platform-web.view.main.Navigation',
        'Platform-web.view.main.ContentPanel',
        'Platform-web.view.main.Button',
        'Platform-web.view.main.MainMenuToolbar'
    ],
    viewModel: {
        type: 'main'
    },
    controller: 'main',
    layout: {
        type: 'border'
    },
    defaults: {
        bodyPadding: 5,
        split: true

    },

    items: [
        {
            xtype: 'app-navigation',
            region: 'west',
            width: '18%'
        }, {
            xtype: 'app-contentpanel',
            region: 'center'
        }, {
            xtype: 'app-header',
            region: 'north'
        }, {
            xtype: 'mainmenutoolbar',
            region: 'north' // 把他放在maintop的下面
        }, {
            xtype: 'app-button',
            region: 'south',
            height: "3%"
        }
    ],
    initComponent: function () {
        Ext.setGlyphFontFamily('FontAwesome'); // 设置图标字体文件，只有设置了以后才能用glyph属性
        this.callParent();
    }
});