<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${param.pageTitle}</title>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.6.0.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style.css" />

<!-- WebSocket:sock.js CDN -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.js"></script>
<!-- WebSocket: stomp.js CDN -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
</head>

<body>
	<div class="input-group mb-3">
	  <input type="text" id="message" class="form-control" placeholder="Message">
	  <div class="input-group-append" style="padding: 0px;">
	    <button id="sendBtn" class="btn btn-outline-secondary" type="button">Send</button>
	  </div>
	</div>
	<div>
	    <ul class="list-group list-group-flush" id="data">
	    	<c:forEach items="${chatLogs}" var="chat">
	    		<li class="list-group-item">${chat.memberId} : ${chat.msg}</li>
	    	</c:forEach>
	    </ul>
	</div>
<script>
const ws = new SockJS(`http://\${location.host}${pageContext.request.contextPath}/stomp`);
const stompClient = Stomp.over(ws);

stompClient.connect({}, (frame) => {
	console.log('connect : ', frame);
	
	stompClient.subscribe(`/app/chat/${param.chatroomId}`, (message) => {
		console.log(`/app/chat/${param.chatroomId} : `, message);
		
		const {'content-type':contextType} = message.headers;
		if(!contextType) return;
		
		const {memberId, msg, time} = JSON.parse(message.body);
		const li = `<li class="list-group-item" title="\${time}">\${memberId} : \${msg}</li>`;
		
		document.querySelector("#data").insertAdjacentHTML('beforeend', li);
	});
});

// 관리자 채팅 보내기
document.querySelector("#sendBtn").addEventListener('click', (e) => {
	const msg = document.querySelector("#message").value;
	if(!msg) return;
	
	const payload = {
		chatroomId : `${param.chatroomId}`,
		memberId : `<sec:authentication property="principal.username"/>`,
		msg,
		time : Date.now()
	};
	stompClient.send(`/app/chat/${param.chatroomId}`, {}, JSON.stringify(payload));
	document.querySelector("#message").value = '';
});

</script>
</body>
</html>