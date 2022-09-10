<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="utf-8" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="관리자 회원 관리" />
</jsp:include>

<div>관리자 회원관리 페이지입니다. 이 페이지를 볼 수 있는 당신은 관리자!!</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>