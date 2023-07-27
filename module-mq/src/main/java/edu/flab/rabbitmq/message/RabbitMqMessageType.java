package edu.flab.rabbitmq.message;

public enum RabbitMqMessageType {
	ELECTION_REGISTER,      // 재판 등록이 접수됐음을 알림
	ELECTION_REGISTER_ACK,  // 상대가 재판 내용을 승인했음을 알림
	ELECTION_REGISTER_NCK,  // 상대가 재판 내용을 거절했음을 알림
	ELECTION_REGISTER_OK,   // 재판 내용이 최종 승인됐음을 알림
	ELECTION_FINISHED       // 재판이 종료됐음을 알림
}
