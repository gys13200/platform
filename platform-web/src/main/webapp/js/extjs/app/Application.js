Ext.define('Platform-web.Application', {
    extend : 'Ext.app.Application',
    name: 'Platform-web',
    appFolder : 'js/extjs/app',
    stores : [
        'UserStore@Platform-web.store.user'
    ],

    requires : [
        'Platform-web.view.system.SysFunc',
        'Platform-web.view.system.role.SysRole',
        'Platform-web.view.user.UserGrid'
    ],


    initComponent: function () {
        //Ext.setGlyphFontFamily('FontAwesome');
    },
    init : function () {
        var me = this;
        //me.redirectTo('all');
    }
});