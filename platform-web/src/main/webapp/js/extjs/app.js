Ext.Loader.setConfig({
    enabled: true,
    paths : {
        Ext : 'js/extjs/extjs',
        'Ext.ux' : 'js/extjs/extjs/ux'
    }
});
Ext.application({

    name : 'Platform-web',

    appFolder : 'js/extjs/app',

    extend : 'Platform-web.Application',

    autoCreateViewport : 'Platform-web.view.main.Main'
});