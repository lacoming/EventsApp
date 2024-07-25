package com.github.lacoming.eventsapi.service;

import com.github.lacoming.eventsapi.entity.Comment;
import com.github.lacoming.eventsapi.exception.EntityNotFoundException;
import com.github.lacoming.eventsapi.model.PageModel;
import com.github.lacoming.eventsapi.repository.CommentRepository;
import com.github.lacoming.eventsapi.repository.EventRepository;
import com.github.lacoming.eventsapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    public List<Comment> findAllByEventId(Long eventId) {
        return findAllByEventId(eventId, null).getContent();
    }

    public Page<Comment> findAllByEventId(Long eventId, PageModel pageModel) {
        return commentRepository.findAllByEventId(
                eventId,
                pageModel == null ? Pageable.unpaged() : pageModel.toPageRequest()
        );
    }

    @Transactional
    public Comment save(Comment comment, Long userId, Long eventId) {
        var currentEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Event with id {0} not found", eventId)
                ));
        return userRepository.findById(userId)
                .map(user -> {
                    comment.setUser(user);
                    comment.setEvent(currentEvent);
                    return commentRepository.save(comment);
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("User with id {0} not found!", userId)
                ));
    }

    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    public boolean hasInEvent(Long commentId, Long eventId, Long authorId) {
        return commentRepository.existsByIdAndEventIdAndUserId(commentId, eventId, authorId);
    }
}
