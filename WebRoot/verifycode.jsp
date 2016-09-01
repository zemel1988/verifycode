<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
    <head>  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery.js"></script>    
        <script type="text/javascript" src="js/verifyCode.js"></script> 
         
        <title>test verify code</title>  
    </head>  
    <body>  
    
        <a href="#"><img id="imgObj"  src="verifyimage.create" onclick="changeImg()" border="0"/></a>  
        <input id="veryCode"  type="text" name="veryCode" /> 
        	<span id="change" onclick="changeImg()"><a href="#">换一张</a></span> 
        	<input type="button" onclick="isRightCode()"/ value="验证">
        <div id="info"></div>
        
    </body>  
</html>