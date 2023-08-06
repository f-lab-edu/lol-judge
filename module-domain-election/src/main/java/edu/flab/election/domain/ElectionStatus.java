package edu.flab.election.domain;

public enum ElectionStatus {
	PENDING,                    // 당사자 간 내용 합의 대기 중
	PENDING_HOST_NCK,           // 호스트측이 내용 합의 거절
	PENDING_PARTICIPANT_NCK,    // 대상이 합의 거절
	READY,                      // 내용 합의 완료
	IN_PROGRESS,                // 재판 진행 중
	FINISHED                    // 재판 판결 완료
}
