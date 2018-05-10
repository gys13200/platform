Ext.define('Platform-web.view.main.NavigationMenu', {
    extend : 'Ext.menu.Menu',
    xtype : 'app-navigationMenu',
    shadow: "drop",
    allowOtherMenus: true,
    items: [
        new Ext.menu.Item({
            text: "新建"
        })
    ]
});