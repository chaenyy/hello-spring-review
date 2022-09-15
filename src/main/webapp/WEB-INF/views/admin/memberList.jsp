<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<fmt:requestEncoding value="utf-8" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="관리자 회원 관리" />
</jsp:include>

<div class="text-center">관리자 회원관리 페이지입니다. 이 페이지를 볼 수 있는 당신은 관리자!!</div>
    <div class="w-75 mx-auto">
        <button 
            type="button" 
            class="btn btn-block btn-outline-primary"
            data-toggle="modal" data-target="#adminNoticeModal">공지</button>
    </div>
    
	<!-- 관리자용 공지 modal -->
    <div class="modal fade" id="adminNoticeModal" tabindex="-1" role="dialog" aria-labelledby="#adminNoticeModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="adminNoticeModalLabel">Notice</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <form name="adminNoticeFrm">
              <div class="form-group">
                <label for="send-to-name" class="col-form-label">받는이 :</label>
                <input type="text" class="form-control" id="send-to-name" placeholder="공란인 경우, 전체공지로 전송됩니다.">
              </div>
              <div class="form-group">
                <label for="message-text" class="col-form-label">메세지 :</label>
                <textarea class="form-control" id="message-text"></textarea>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="adminNoticeSendBtn">전송</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
          </div>
        </div>
      </div>
    </div>
<script>
document.querySelector("#adminNoticeSendBtn").addEventListener('click', (e) => {
	const to = document.querySelector("#send-to-name").value;
	const msg = document.querySelector("#message-text").value;
	if(!msg) return;
	
	const payload = {
		from : '<sec:authentication property="principal.username"/>',
		to,
		msg,
		time : Date.now(),
		type : 'NOTICE'
	};
	
	const url = to ? `/app/admin/notice/\${to}` : '/app/admin/notice';
	stompClient.send(url, null, JSON.stringify(payload)); // 텍스트 기반으로 송부!(객체론 안됨)
	document.adminNoticeFrm.reset();
	
	// 모달 숨기기
	$("#adminNoticeModal").modal('hide');	
});
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>