package edu.flab.member.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.flab.member.domain.GameAccount;
import edu.flab.member.dto.GameAccountUpdateDto;

@Mapper
public interface GameAccountMapper {
	void save(GameAccount gameAccount);

	void update(@Param("id") Long id, @Param("updateParam") GameAccountUpdateDto dto);

	Optional<GameAccount> findById(Long id);

	boolean isDuplicated(String loginId);
}
