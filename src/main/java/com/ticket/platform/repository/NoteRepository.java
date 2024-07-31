package com.ticket.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.platform.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

	public List<Note> findByTitleNote(String titleNote);
	
}
