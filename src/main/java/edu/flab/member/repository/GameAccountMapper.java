package edu.flab.member.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import edu.flab.member.domain.GameAccount;
import edu.flab.member.dto.GameAccountUpdateDto;

@Mapper
public interface GameAccountMapper {
	void save(GameAccount gameAccount);

	void update(GameAccountUpdateDto dto);

	Optional<GameAccount> findById(Long id);

	Optional<GameAccount> findByLoginId(String loginId);
}
