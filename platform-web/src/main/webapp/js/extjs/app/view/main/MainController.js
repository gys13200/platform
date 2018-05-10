Ext.define('Platform-web.view.main.MainController', {
    extend: 'Ext.app.ViewController',
    requires: [
        'Ext.window.MessageBox',
        'Platform-web.store.main.Navigation'
    ],
    alias: 'controller.main',

    routes: {
        ':id': {
            action: 'handleRoute',
            before: 'beforeHandleRoute'
        }
    },
    control: {
        'app-navigation': {
            itemclick: 'onItemclick'
        },
        'app-contentpanel': {
            tabchange: 'tabchange'
        }
    },


    tabchange: function (tabPanel, newPanel) {
        if (newPanel) {
            this.redirectTo(newPanel.id);
        }
    },

    onItemclick: function (selModel, record) {
        if (record) {
            this.redirectTo(record.getId());
        }
    },
    beforeHandleRoute: function (id, action) {
        var me = this,
            store = Ext.StoreMgr.get('navigation');

        var node = store.getNodeById(id);
        if (node) {
            action.resume();
        } else if (store.getCount() === 0) {
            //在store load事件中判断节点，避免store数据未加载情况
            store.on('load', function () {
                node = store.getNodeById(id);
                if (node) {
                    action.resume();
                } else {
                    Ext.Msg.alert('路由跳转失败', '找不到id为' + id + ' 的组件');
                    action.stop();
                }
            });
        } else {
            Ext.Msg.alert(
                '路由跳转失败',
                '找不到id为' + id + ' 的组件. 界面将跳转到应用初始界面',
                function () {
                    me.redirectTo('all');
                }
            );
            //stop action
            action.stop();
        }


    },
    handleRoute: function (id) {
        var me = this,
            mainView = me.getView(),
            navigationTree = mainView.down('app-navigation'),
            contentpanel = mainView.down('app-contentpanel'),
            store = Ext.StoreMgr.get('navigation'),
            ViewClass;
        var node = store.getNodeById(id);

        //响应路由，左侧树定位到相应节点
        navigationTree.getSelectionModel().select(node);
        navigationTree.getView().focusNode(node);

        // 处理右侧面板
        //contentpanel.removeAll(true);
        var text = node.get('text');
        var title = node.isLeaf() ? (node.parentNode.get('text') + ' - ' + text) : text;
        if (node.isLeaf()) {

            var cmp = contentpanel.getComponent(id);

            if (!cmp) {
                ViewClass = Ext.ClassManager.getByAlias('widget.' + id);
                cmp = new ViewClass();
                cmp.title = title;
                cmp.closable = true;
                cmp.id = id;
                contentpanel.add(cmp);
            }

            contentpanel.setActiveTab(cmp);
        }


        //contentpanel.setTitle(title);
        document.title = document.title.split(' - ')[0] + ' - ' + text;

    }
});