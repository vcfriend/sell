<html>
<#assign title="类目列表"/>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
<#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
    <#--内容区-->
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <td>类目id</td>
                            <td>名称</td>
                            <td>type</td>
                            <td>创建时间</td>
                            <td>修改时间</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                    <tbody>
                    <#list categoryList as category>
                    <tr>
                        <td>${category.categoryId}</td>
                        <td>${category.categoryName}</td>
                        <td>${category.categoryType}</td>
                        <td>${(category.createTime)!''}</td>
                        <td>${(category.updateTime)!''}</td>
                        <td><a href="${ctx}/seller/category/index?categoryId=${category.categoryId}">修改</a></td>
                    </tr>
                    </#list>
                    </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
