<html>
<#assign title="错误提示"/>
<#include "../common/header.ftl">
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true"> </button>
                <h4>
                    错误!
                </h4> <strong>${msg}</strong><br>
                <a href="${ctx!""}${url!""}" class="alert-link">3s后自动跳转</a>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    setTimeout('location.href="${ctx!""}${url!""}"', 2000);
</script>
</html>