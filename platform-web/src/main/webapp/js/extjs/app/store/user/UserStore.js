Ext.define('Platform-web.store.user.UserStore', {
    extend : 'Ext.data.Store',
    model : 'Platform-web.model.user.UserModel',
    autoLoad : true,

    proxy : {
        type : 'ajax',
        api : {
            read : 'json/user.json'
        },
        reader : {
            type : 'json',
            rootProperty : 'users',
            successProperty: 'success'
        }
    }
});