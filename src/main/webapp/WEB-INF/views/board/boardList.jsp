<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="게시판" name="title"/>
</jsp:include>
<style>
/*글쓰기버튼*/
input#btn-add{float:right; margin: 0 0 15px;}
</style>
<section id="board-container" class="container">
	<input type="button" value="글쓰기" id="btn-add" class="btn btn-outline-success" 
		onclick="location.href='${pageContext.request.contextPath}/board/boardForm.do'"/>
	<table id="tbl-board" class="table table-striped table-hover">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>첨부파일</th> <!-- 첨부파일 있을 경우, /resources/images/file.png 표시 width: 16px-->
			<th>조회수</th>
		</tr>
		<c:if test="${not empty list}">
			<c:forEach items="${list}" var="list">
				<tr data-no="${list.no}">
					<td>${list.no}</td>
					<td>${list.title}</td>
					<td>${list.memberId}</td>
					<td>
						<fmt:parseDate value="${list.createdAt}" var="createdAt" pattern="yyyy-MM-dd'T'HH:mm" />
						<fmt:formatDate value="${createdAt}" pattern="yy/MM/dd HH:mm"/>
					</td>
					<td>
						<c:if test="${list.attachCount gt 0}">
							<img src="${pageContext.request.contextPath}/resources/images/file.png" width="16px"/>
						</c:if>
					</td>
					<td>${list.readCount}</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list}">
			<tr>
				<td colspan="6" class="text-center">조회된 게시글이 없습니다.</td>
			</tr>
		</c:if>
	</table>
	<nav>
		${pagebar}
	</nav>
</section> 
<script>
document.querySelectorAll("tr[data-no]").forEach((tr) => {
	tr.addEventListener('click', (e) => {
		//console.log(e.target);
		const tr = e.target.parentElement;
		const no = tr.dataset.no;
		//console.log(no);
		
		if(no) {
			location.href = `${pageContext.request.contextPath}/board/boardDetail.do?no=\${no}`;	
		}
	})
});
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
