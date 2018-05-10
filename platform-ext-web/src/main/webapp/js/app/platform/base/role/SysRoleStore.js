Ext.define('app.platform.base.role.SysRoleStore', {
    extend : 'Ext.data.Store',
    //model : 'SysRoleModel',
    autoLoad : true,
    alias : 'store.SysRoleStore',
    proxy : {
        type : 'ajax',
        api : {
            read : '/role_list'
        },
        reader : {
            type : 'json',
            rootProperty : 'rows',
            successProperty: 'success',
            totalProperty: 'total'
        }
    }
});