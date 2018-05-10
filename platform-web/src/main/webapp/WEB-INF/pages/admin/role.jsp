<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <!-- 导入jquery核心类库 -->
    <jsp:include page="${pageContext.request.contextPath}/common/reference.jsp"/>
    <script type="text/javascript">
        function doAdd() {
            var url = '${path}/page.do?module=admin&resource=role_add';
            $(window)._openWindow("roleWindow", url, 800, 500, "用户新增");
        }

        function doEdit() {
            $(this)._hadSelectOneRecord("grid", function (row) {
                var url = '${path}/toEditRole?roleId=' + row.id;
                $(window)._openWindow("roleWindow", url, 800, 500, "用户修改");
            });
        }

        $(function () {
            $("#addRoleWindow").window('close');
            // 数据表格属性
            $("#grid").datagrid({
                toolbar: "#toolbar",
                url: '${pageContext.request.contextPath}/role_list.do'
            });
        });
    </script>
</head>
<body class="easyui-layout">

<!-- 工具栏 -->
<div id="toolbar">
    <a href="#" id="add" onclick="doAdd()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增</a>
    <a href="#" id="edit" onclick="doEdit()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a>
</div>

<div data-options="region:'center'">
    <table id="grid">
        <thead>
        <tr>
            <th data-options="field:'id',width:100, hidden:true">编号</th>
            <th data-options="field:'name',width:200">名称</th>
            <th data-options="field:'description',width:200">描述</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>