// ws.js
const ws = new SockJS(`http://${location.host}/spring2/stomp`);

// stomp 객체 생성 -> 웹소켓 객체를 직접 제어하지 않고 stomp를 통해서 제어
const stompClient = Stomp.over(ws);

// 연결된 이후 호출해주는 핸들러. {} -> 옵션
stompClient.connect({}, (frame) => {
	console.log("connect : ", frame);
	
	// 연결 이후 구독 신청
	/* 
		stompClient.subscribe("/topic/a", (message) => {
			console.log("/topic/a : ", message);
		});
		
		stompClient.subscribe("/app/a", (message) => {
			console.log("/app/a : ", message);
		})
	*/
	
	// 전체 공지
	stompClient.subscribe("/app/notice", (message) => {
		console.log("/app/notice : ", message);
		const {body} = message;
		const {msg, time} = JSON.parse(body);
		alert(`전체공지
=========================
${msg}
=========================
${new Date(time)}`);
	});
	
	// 개별 공지
	stompClient.subscribe(`/app/notice/${memberId}`, (message) => {
		console.log(`/app/notice/${memberId} : `, message);
		const {body} = message;
		const {msg, time} = JSON.parse(body);
		alert(`개인공지
=========================
${msg}
=========================
${new Date(time)}`);
	});
});