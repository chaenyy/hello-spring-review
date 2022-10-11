<%@page import="java.util.Date"%>
<%@page import="com.ce.spring2.chat.model.dto.ChatLog"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="관리자와 1:1채팅" name="title"/>
</jsp:include>

<div class="input-group mb-3">
  <input type="text" id="msg" class="form-control" placeholder="관리자에게 보내는 Message">
  <div class="input-group-append" style="padding: 0px;">
    <button id="sendBtn" class="btn btn-outline-secondary" type="button">Send</button>
  </div>
</div>
<div>
	<ul class="list-group list-group-flush" id="data">
		<c:if test="${not empty chatLogs}">
			<c:forEach items="${chatLogs}" var="log">
			<%
				ChatLog chat = (ChatLog) pageContext.getAttribute("log");
				String time = new Date(chat.getTime()).toString();
			%>
				<li class="list-group-item" title="<%= time %>">${log.memberId} : ${log.msg}</li>			
			</c:forEach>
		</c:if>
	</ul>
</div>
<script>
setTimeout(() => {
	stompClient.subscribe(`/app/chat/${chatroomId}`, (message) => {
		const {'content-type':contentType} = message.headers;
		
		if(!contentType) {
			console.log(`/app/chat/${chatroomId} : `, message);
			
			const {memberId, msg, time} = JSON.parse(message.body);
			const li = `
				<li class="list-group-item" title="\${new Date(time).toLocaleString()}">\${memberId} : \${msg}</li>
			`;
			const wrapper = document.querySelector("#data");
			wrapper.insertAdjacentHTML('beforeend', li);
		}
		
	});
}, 500);

document.querySelector("#sendBtn").addEventListener('click', (e) => {
	const msg = document.querySelector("#msg").value;
	if(!msg) return;
	
	const chatlog = {
		chatroomId : `${chatroomId}`,
		memberId : '<sec:authentication property="principal.username"/>',
		msg,
		time : Date.now()
	};
	stompClient.send(`/app/chat/${chatroomId}`, {}, JSON.stringify(chatlog));
	
	// 초기화
	document.querySelector("#msg").value = '';
});
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
