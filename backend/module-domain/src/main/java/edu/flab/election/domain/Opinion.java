package edu.flab.election.domain;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Opinion {
	@Length(max = 10)
	@NotBlank
	String champion;

	@Length(max = 300)
	@NotBlank
	String description;
}
