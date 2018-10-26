<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@include file="../../common/taglib.jsp" %>
	<%@include file="../../common/common-css.jsp" %>
	<%@include file="../../common/common-js.jsp" %>
<!-- 写需要的 js 文件 -->
<script type="text/javascript">
	
</script>
	
</head>
<body>
<form id="fenye" name="fenye" action="<%=request.getContextPath()%>/background/${firstLowerPoName}/find.html" method="post">
<div style="overflow-y:scroll;overflow-x:scroll; height:75%;">
<table width="100%" cellpadding="0" cellspacing="0" >
  <!-- 搜索页面展示 -->
  <tr>
     <td align="left" >
       <div id="search_bar" class="mt10">
       <div class="box">
          <div class="box_border">
          	<!-- 表头显示 -->
            <div class="box_top">
                <b>&nbsp;&nbsp;系统管理——> XXX管理 </b>
            </div>
            <div class="box_center pt10 pb10">
              <!-- 查询条件添加 -->
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td>&nbsp;&nbsp;查询条件1</td>
                  <td><input type="text" name="propsName1" value="" class="input-text lh25"/></td>
                  <td>&nbsp;&nbsp;查询条件2</td>
                  <td><input type="text" name="propsName2" value="" class="input-text lh25"/></td>
                    <td>
	                    <div class="box_bottom pb5 pt5 pr10" style="border-top:0px solid #dadada;">
			              <div class="search_bar_btn" style="text-align:right;">
			                 <input type="submit" name="button" class="btn btn82 btn_search" value="查询">   
	                         <sec:authorize ifAnyGranted="ROLE_${tableName}_add">
	                            <input type="button" name="button" class="btn btn82 btn_add" value="新增" onclick="GOTO('<%=request.getContextPath()%>/background/${firstLowerPoName}/addUI.html')">
	                         </sec:authorize>
	                         <sec:authorize ifAnyGranted="ROLE_${tableName}_delete">
	                            <input type="button" name="button" class="btn btn82 btn_del" value="删除" onclick="return deleteAll()">
	                         </sec:authorize>
	                         <input type="button" name="button" class="btn btn82 btn_res" value="重置" onclick="clearText()">
			              </div>
			            </div>
                    </td>
                </tr>
              </table>
            </div>
          </div>
        </div>
        </div>
     </td>
  </tr>
  <!-- 主页面展示 -->
  <tr>
    <td>
        <div id="table" class="mt10">
            <div class="box span10 oh">
              <table class="list_table" width="100%" cellspacing="1" onmouseover="changeto()"  onmouseout="changeback()">
			          <tr>
			            <th >
			              <input id="chose" type="checkbox" name="checkbox" onclick="selectAllCheckBox()" />
			            </th>
			            <!-- 显示的列 -->
              			${listPageCloumns}
              			<th >基本操作</th>
			          </tr>
			          <c:forEach var="key" items="pageView.data" >
			              <tr class="tr">
							<!-- 显示的内容 -->
           					${listPageContent}	
			              </tr>
			          </c:forEach>
              </table>
        </div>
      </div>
    </td>
  </tr>
   <!-- 分页 -->
   <tr>
    <td>
        <div id="table" class="mt10">
            <div class="box span10 oh">
              <%@include file="../../common/webfenye.jsp" %>
            </div>
      </div>
    </td>
  </tr>
  
</table>
</div>
</form>
</body>
</html>
