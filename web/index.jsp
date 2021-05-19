<%--
  Created by IntelliJ IDEA.
  User: linzhi
  Date: 2017/3/11
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    pageContext.setAttribute("path", path);
%>
<html>
<head>
    <title></title>
    <script src="assets/jquery/jquery-1.10.2.min.js"></script>
    <script>
        $.ajax({
            url:"${path}/user/list.do",
            type:"post",
            dataType:"json",
            success:function (json) {
                if(json.status==true) {
                    console.log(json.data);
                }
            }
        });
    </script>
</head>
<body>

</body>
</html>
