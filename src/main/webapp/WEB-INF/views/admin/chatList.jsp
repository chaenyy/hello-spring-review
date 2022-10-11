<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="채팅관리" name="title"/>
</jsp:include>

<table class="table text-center" id="tbl-chat-list">
  <thead>
    <tr>
      <th scope="col">회원아이디</th>
      <th scope="col">메세지</th>
    </tr>
  </thead>
  <tbody>
  	<c:forEach items="${chatList}" var="chat">
  		<tr data-chatroom-id="${chat.chatroomId}">
  			<td>${chat.memberId}</td>
  			<td class="msg">${chat.msg}</td>
  		</tr>
  	</c:forEach>
  </tbody>
</table>
<script>
const trClickHandler = (e) => {
	const chatroomId = e.target.parentElement.dataset.chatroomId;
	console.log(chatroomId);
	
	// 팝업
	const url = `${pageContext.request.contextPath}/admin/chat.do?chatroomId=\${chatroomId}`;
	const name = chatroomId; // window의 이름으로 사용
	const spec = "width=500px, height=500px";
	open(url, name, spec);
};

// 채팅방 별 채팅 팝업 생성
document.querySelectorAll("tr[data-chatroom-id]").forEach((tr) => {
	tr.addEventListener('click', trClickHandler);
});

// 채팅 목록 및 메세지 최신화 처리를 위한 구독
setTimeout(() => {
	stompClient.subscribe('/app/admin/chatList', (message) => {
		console.log(message);
		
		const {chatroomId, memberId, msg} = JSON.parse(message.body);
		let tr = document.querySelector(`tr[data-chatroom-id="\${chatroomId}"]`);
		
		if(tr) {			
			tr.querySelector(".msg").innerHTML = msg;
		} else {
			// 신규채팅방인 경우
			tr = document.createElement("tr");
			
			const memberIdTd = document.createElement("td");
			memberIdTd.innerHTML = memberId;
			
			const msgTd = document.createElement("td");
			msgTd.classList.add('msg');
			msgTd.innerHTML = msg;
			
			tr.append(memberIdTd, msgTd);
			tr.addEventListener('click', trClickHandler);
		}
		
		// 끌어올리기
		// 새로 생성된 것이 아닌 기존 존재하는 태그이므로 잘라 붙여넣기 처리!
		document.querySelector("#tbl-chat-list tbody").insertAdjacentElement('afterbegin', tr);
	});
}, 500);
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
