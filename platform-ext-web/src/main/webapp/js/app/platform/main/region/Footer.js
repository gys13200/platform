
Ext.define('app.platform.main.region.Footer', {

    extend : 'Ext.toolbar.Toolbar',
    xtype : 'app-footer',
    uses: [
        'app.ux.ButtonTransparent'
    ],
    default: {
        xtype: 'buttontransparent'
    },
    items : [
        {
            xtype: 'buttontransparent',
            bind: {
                text: '欢迎你， {user.name}'
            }
        }, ' ',{
            xtype: 'buttontransparent',
            bind: {
                text: '当前年度： {user.year}'
            }
        }
    ]

});