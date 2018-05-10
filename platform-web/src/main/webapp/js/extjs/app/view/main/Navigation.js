Ext.define('Platform-web.view.main.Navigation', {
    extend : 'Ext.tree.Panel',
    xtype : 'app-navigation',
    rootVisible : false,
    frame: false,
    bodyBorder: true,
    containerScroll: true,
    collapsible: true,
    animate: true,
    bind : {
        store : '{navigationStore}',
        title : '{navigationTitle}'
    }
});