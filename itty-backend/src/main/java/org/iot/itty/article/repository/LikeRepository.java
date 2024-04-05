package org.iot.itty.article.repository;

import java.util.List;

import org.iot.itty.article.aggregate.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
	List<LikeEntity> findAllByUserCodeFk(int userCodeFk);
}
