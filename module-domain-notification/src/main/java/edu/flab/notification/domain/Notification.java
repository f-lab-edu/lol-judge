package edu.flab.notification.domain;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {
	private Long id;

	@NotNull
	@Positive
	private Long memberId;

	@NotBlank
	private String contents;

	@NotNull
	private OffsetDateTime createdAt;

	@Default
	private ReadStatus readStatus = ReadStatus.NOT_READ;
}
