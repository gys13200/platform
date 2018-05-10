Ext.define('app.platform.main.region.Header', {

    extend : 'Ext.toolbar.Toolbar',
    xtype : 'app-header',

    uses : ['app.ux.ButtonTransparent'],
    defaults : {
        xtype : 'buttontransparent'
    },

    height : '5%',
    layout : {
        type : 'hbox',
        align : 'middle'
    },
    items : [
        {
            text: '菜单',
            glyph : 0xf0c9,
            //iconCls: 'fas fa-bars',
            menu: [{
                text: '工程管理',
                menu: [{
                    text : '工程项目'
                }, {
                    text : '工程标段'
                }]
            }]
        },'  ',
        {
            xtype : 'label',
            bind: {
                text: '{system.name}'
            },
            style : 'font-size:20px;color:blue;'
        },{
            xtype : 'label',
            bind: {
                text: '(v{system.version})'
            },
            style : 'font-size:20px;color:blue;'
        },
        '->','->'
        ,
        ' ', ' ',
        {
            text : '主页',
            //iconCls: 'fas fa-user',
            glyph: 0xf015,
            handler: 'onHomePageButtonClick'
        }, {
            text : '设置',
            glyph: 0xf013
        },{
            text : '帮助',
            glyph: 0xf059
        }, {
            text : '关于',
            glyph: 0xf05e
        }, {
            text : '注销',
            glyph: 0xf08b
        }
    ]

});