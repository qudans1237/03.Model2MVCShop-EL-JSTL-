<%@ page language="java" contentType="text/html; charset=EUC-KR"%>


<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">


</head>


<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">
 <form name="detailForm"  method="post">


<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
	
						상 품 리 뷰
					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" >유저아이디</option>
				<option value="1" >상품명</option>
			</select>
			<input 	type="text" name="searchKeyword" 
						value=""
						class="ct_input_g" style="width:200px; height:20px" >
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						검색
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
			전체  5<!--  ${resultPage.totalCount } -->건수, 현재 <!--${resultPage.currentPage}-->1  페이지
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">유저아이디</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">리뷰날짜</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">   </td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	

		<tr class="ct_list_pop">
			<td align="center">1</td>
		<td></td>
		
		<td align="left">
				<a href=>user03 </a></td>
		<td></td>
		<td align="left">자전거</td>
		<td></td>
		<td align="left">2022-04-11</td>
		<td></td>
		
		<td align="left">
		
				
					<a href="/getProduct.do?prodNo=&menu=">내용보러가기</a></td>
		<!--/c:if-->
	
	</tr>			
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	

</table>	
			
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<jsp:include page="../common/productPageNavigator.jsp"/>	
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
