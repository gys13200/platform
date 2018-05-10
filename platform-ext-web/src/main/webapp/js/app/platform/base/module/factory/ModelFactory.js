/**
 * 根据module的数据来生成模块的model
 */
Ext.define('app.platform.base.module.factory.ModelFactory', {

    // 静态变量函数
    statics: {
        getModelByModule: function (moduleModel) {
            var module = moduleModel.data;
            return Ext.define('app.model.' + module.tf_moduleName, {

                extend: 'Ext.data.Model',
                module: module,
                idProperty: module.tf_primaryKey, // 主键
                nameFields: module.tf_nameFields, // 名称
                fields: this.getFields(module), // 设置字段

                getIdValue: function(){
                    return this.get(this.idProperty);
                },

                getNameValue: function(){
                    if(this.nameFields){
                        return this.get(this.nameFields);
                    }else{
                        return null;
                    }
                }
            });
        },

        getFields: function (module) {
            var fields = [];

            for(var i in module.tf_fields){
                var fd = module.tf_fields[i];
                var field = {
                    name: fd.tf_fieldName,
                    title: fd.tf_title,
                    type: this.getTypeByStr(fd.tf_fieldType)
                };
                if(field.type == 'string'){
                    field.useNull = true;
                    field.serialize = this.convertToNull;
                }
                if(field.type == 'date'){
                    field.dateWriteFormat = 'Y-m-d';
                    field.dateReadFormat = 'Y-m-d';
                }
                if(field.type == 'datetime'){
                    field.dateReadFormat = 'Y-m-d H:i:s';
                }
                fields.push(field);
            }
            return fields;
        },
        getTypeByStr: function (str) {
            switch (str){
                case 'String':
                    return 'string';
                case 'Boolean':
                    return 'boolean';
                case 'Integer':
                    return 'int';
                case 'Date':
                    return 'date';
                case 'Datetime':
                    return 'date';
                case 'Double':
                case 'Float':
                case 'Percent':
                    return 'float';
                default:
                    return 'string';
            }

        },
        convertToNull: function (v) {
            return v ? v : null;
        }
    }
});