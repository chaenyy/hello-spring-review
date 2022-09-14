<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="utf-8" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="Websocket | Sockjs" />
</jsp:include>
	<div class="input-group mb-3">
		<input type="text" id="message" class="form-control"
			placeholder="Message">
		<div class="input-group-append" style="padding: 0px;">
			<button id="sendBtn" class="btn btn-outline-secondary" type="button">Send</button>
		</div>
	</div>
	
	<div>
		<ul class="list-group list-group-flush" id="data"></ul>
	</div>
<script>
//const ws = new WebSocket(`ws://\${location.host}${pageContext.request.contextPath}/echo`);
const ws = new SockJS(`http://\${location.host}${pageContext.request.contextPath}/echo`); // http -> ws
ws.addEventListener('open', (e) => console.log("open : ", e));
ws.addEventListener('error', (e) => console.log("error : ", e));
ws.addEventListener('close', (e) => console.log("close : ", e));
ws.addEventListener('message', (e) => {
	console.log("message : ", e);
	const container = document.querySelector("#data");
	data.insertAdjacentHTML('beforeend', `<li class="list-group-item">\${e.data}</li>`);
});

document.querySelector("#sendBtn").addEventListener('click', (e) => {
	ws.send(document.querySelector("#message").value);
});
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>