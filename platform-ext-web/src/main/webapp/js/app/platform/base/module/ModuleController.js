/**
 * 模块的控制器
 */

Ext.define('app.platform.base.module.ModuleController', {
    extend : 'Ext.app.ViewController',

    requires : [],

    alias : 'controller.module',

    init : function() {
        console.log('modulecontroller.init')
    },

    addRecord : function(){
        var grid = this.getView().down('modulegrid');
        var model = Ext.create(grid.getStore().model);

        model.set('tf_id', 0);

        grid.getStore().insert(0, model);



        console.log(model);
        grid.getStore().sync();
    }

});