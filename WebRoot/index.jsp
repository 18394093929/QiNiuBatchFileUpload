<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>jquery upload</title>
    <link rel="stylesheet" href="uploadify/uploadify.css" type="text/css"></link>  
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#file_upload").uploadify({
                //指定swf文件
                'swf': 'uploadify/uploadify.swf',
                //后台处理的页面
                'uploader': 'upload/testQiNiu.do',
                'progressData' : 'speed',
                //按钮显示的文字
                'buttonText': '上传文件',
                //显示的高度和宽度，默认 height 30；width 120
                //'height': 15,
                //'width': 80,
                //上传文件的类型  默认为所有文件    'All Files'  ;  '*.*'
                //在浏览窗口底部的文件类型下拉菜单中显示的文本
                'fileTypeDesc': 'All Files',
                //允许上传的文件后缀
                //'fileTypeExts': '*.gif; *.jpg; *.png',
                'cancel': 'uploadify/cancel.png',
                //上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
                'queueID': 'fileQueue',
                //选择文件后自动上传
                'auto': false,
                //设置为true将允许多文件上传
                'multi': true,
                /* 'onUploadComplete' : function(file) {
                     alert('上传完成');
                 }, */
                'onCancel' : function(file) {
                     alert('取消上传');
                 }
            });
        });

    </script>
  </head>

  <body>
    <div id="fileQueue"></div>  
    <input type="file" name="file_upload" id="file_upload" />
    <p>  
        <a href="javascript:$('#file_upload').uploadify('upload','*')">开始上传</a>   
        <a href="javascript:$('#uploadify').uploadify('cancel')">取消所有上传</a>  
    </p>  
  </body>
</html>
