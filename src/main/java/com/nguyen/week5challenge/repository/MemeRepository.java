package com.nguyen.week5challenge.repository;

import com.nguyen.week5challenge.model.Meme;
import org.springframework.data.repository.CrudRepository;

public interface MemeRepository extends CrudRepository<Meme, Long> {
}
