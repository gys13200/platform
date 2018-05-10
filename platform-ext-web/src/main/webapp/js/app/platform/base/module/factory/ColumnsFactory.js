Ext.define('app.platform.base.module.factory.ColumnsFactory', {

    statics: {
        getColumns: function (moduleModel, schemeOrderId) {

            var scheme = moduleModel.get('tf_gridSchemes')[0];
            var columns = [];
            for(var i in scheme.tf_schemeGroups){
                var sg = scheme.tf_schemeGroups[i];
                // 是否需要分组

                var isGroup = sg.tf_isShowHeaderSpans;
                var group = {
                    gridGroupId: sg.tf_gridGroupId,
                    text: sg.tf_gridGroupName,
                    locked: sg.tf_isLocked,
                    columns: []
                };

                for(var j in sg.tf_groupFields){
                    var gf = sg.tf_groupFields[j];
                    var fd = moduleModel.getFieldDefine(gf.tf_fieldId);
                    var field;

                    if(fd.tf_isHidden)
                        continue;

                    field = this.getColumn(gf, fd, moduleModel);
                    field.locked = sg.tf_isLocked;

                    if(isGroup){
                        this.canReduceTitle(group, field);
                        group.columns.push(field);
                    }
                }
                if(isGroup){
                    this.canReduceTitle(group, field);
                    columns.push(group);
                }

            }
            return columns;
        },

        canReduceTitle: function (group, field) {
            if(field.text.indexOf(group.text) == 0){
                field.text = field.text.slice(group.text.length).replace('(', '').replace(')', '').replace('（', '').replace('）', '');
                if(field.text.indexOf('<br/>') == 0){
                    field.text = field.text.slice(5);
                }
            }
        },
        /**
         * 根据groupFiled, fieldDefine的定义， 生成一个column
         * @param gf
         * @param fd
         * @param module
         */
        getColumn: function(gf, fd, module){

            var ft = fd.tf_title.replace(new RegExp('--', 'gm'), '<br/>');
            if(fd.behindText){
                ft += '<br/>(' + fd.behindText + ')';
            }

            var field = {
                filter: {},
                maxWidth: 800,
                groupFieldId: gf.tf_gridFieldId, // 加上这个属性， 用于在列改变了宽度之后， 传到后台
                sortable: true,
                text: ft,
                dataIndex: fd.tf_fieldName
            };

            switch (fd.tf_fieldType){
                case 'Date':
                    Ext.apply(field, {
                        xtype: 'datecolumn',
                        align: 'center',
                        width: 100,
                        formatter: 'dateRenderer',
                        editor : { // 如果需要行内修改，需要加入此属性
                            xtype : 'datefield',
                            format : 'Y-m-d',
                            editable : false
                        }
                    });
                    break;
                case 'Datetime':
                    Ext.apply(field, {
                        xtype: 'datecolumn',
                        align: 'center',
                        width: 130,
                        //formatter: 'dateRenderer'
                    });
                    break;
                case 'Boolean':
                    field.xtype = 'checkcolumn';
                    field.stopSelection = false;
                    field.processEvent = function (type) {
                        if(type == 'click'){
                            return false;
                        }
                    };
                    break;
                case 'Integer':
                    Ext.apply(field, {
                        xtype: 'numbercolumn',
                        align: 'center',
                        tdCls: 'intcolor',
                        format: '#',
                        formatter: 'intRenderer',
                        editor: {
                            xtype: 'numberfield'
                        }
                    });
                    break;
                case 'Double':
                    Ext.apply(field, {
                        xtype: 'numbercolumn',
                        align: 'center',
                        width: 110,
                        editor: {
                            xtype: 'numberfield'
                        },
                        formatter: fd.tf_isMoney ? 'monetaryRenderer' : 'floatRenderer'
                    });
                    break;
                case 'Float':
                    Ext.apply(field, {
                        xtype: 'numbercolumn',
                        align: 'center',
                        width: 110,
                        formatter: 'floatRenderer'
                    });
                    break;
                case 'Percent':
                    Ext.apply(field, {
                        //xtype: 'numbercolumn',
                        align: 'center',
                        formatter: 'percentRenderer',
                        editor: {
                            xtype: 'numberfield',
                            step: 0.01
                        },
                        width: 110
                    });
                    break;
                case 'String':
                    break;
                default:
                    break;
            }
            if(fd.tf_allowSummary){
                Ext.apply(field, {
                    hasSummary: true,
                    summaryType: 'sum'
                });
            }

            if(gf.tf_columnWidth > 0){
                field.width = gf.tf_columnWidth;
            }else if(gf.tf_columnWidth == -1){
                field.flex = 1;
                field.minWidth = 120;
            }
            return field;
        },

        nameFieldRenderer: function (val, rd, model, row, col, store, gridview) {
            return filterTextSetBk(store, '<strong>'+val+'</strong>');
        }
    }
});