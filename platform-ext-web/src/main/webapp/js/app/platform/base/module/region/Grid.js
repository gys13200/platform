/**
 * 模块数据的主显示区域，继承自Grid
 */

Ext.define('app.platform.base.module.region.Grid', {
    extend : 'Ext.grid.Panel',
    alias : 'widget.modulegrid',
    uses : [
        'app.platform.base.module.region.GridToolbar',
        'app.platform.base.module.factory.ColumnsFactory'
    ],
    bind : {
        title : '{tf_title}' // 数据绑定到ModuleModel中的tf_title
    },
    dockedItems : [{
        xtype : 'gridtoolbar', // 按钮toolbar
        dock : 'top'
    }],

    autoScroll: true,
    columnLines: true,

    viewConfig:{
        stripeRows: true,
        enableTextSelection: true,
        scrollOffset: 0,
        fixed: true,
        flex: 1
    },

    initComponent: function () {
        var viewModel = this.up('modulepanel').getViewModel();

        this.columns = app.platform.base.module.factory.ColumnsFactory.getColumns(viewModel, 10);

        this.cellEditing = new Ext.grid.plugin.CellEditing({
            clicksToEdit: 2
        });
        this.plugins = [this.cellEditing];

        this.callParent();
    }
});