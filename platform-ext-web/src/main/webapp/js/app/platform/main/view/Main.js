Ext.define('app.platform.main.view.Main', {
    extend: 'Ext.container.Container',

    xtype: 'app-main',

    requires: [
        'app.platform.main.controller.MainController',
        'app.platform.main.region.Header',
        'app.platform.main.region.Footer',
        'app.platform.main.model.MainModel',
        'app.platform.main.menu.MainMenuToolbar',
        'app.platform.main.region.Center'
    ],
    viewModel: {
        type: 'main'
    },
    controller: 'main',
    layout: {
        type: 'border'
    },
    defaults: {
        bodyPadding: 0,
        split: false

    },

    items: [{
        xtype: 'app-header',
        region: 'north'
    }, {
        xtype: 'mainmenutoolbar',
        region: 'north'
    },{
        region : 'center', // 中间面版
        xtype : 'maincenter'
    }, {
        xtype: 'app-footer',
        region: 'south'
    }
    ],
    initComponent: function () {
        Ext.setGlyphFontFamily('FontAwesome'); // 设置图标字体文件，只有设置了以后才能用glyph属性
        this.callParent();
    },
    resize: function () {
        container.getController().onMainResize();
    }
});