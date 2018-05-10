Ext.define('Platform-web.view.system.role.SysRoleModel', {
    extend: 'Ext.app.ViewModel',
    fields : [
        {name : 'id', type : 'string'},
        {name : 'name', type : 'string'},
        {name : 'description', type : 'string'},
        {name : 'homePage', type : 'string'},
        {name : 'homePageTitle', type : 'string'}
    ],
    alias : 'viewmodel.SysRoleModel',
    stores : {
        SysRoleStore : {
            type : 'SysRoleStore',
            storeId : 'SysRoleStore'
        }
    }
});