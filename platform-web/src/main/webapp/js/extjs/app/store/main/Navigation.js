Ext.define('Platform-web.store.main.Navigation', {
    extend : 'Ext.data.TreeStore',
    alias : 'store.navigation',
    proxy : {
        type : 'ajax',
        url : 'function/function_menu_ext.do'
    },
    root : {
        text : 'all',
        id : 'all',
        expanded : true
    },
    autoLoad : false
});