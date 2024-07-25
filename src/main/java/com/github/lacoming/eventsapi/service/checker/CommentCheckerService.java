package com.github.lacoming.eventsapi.service.checker;

import com.github.lacoming.eventsapi.aop.AccessCheckType;
import com.github.lacoming.eventsapi.service.CommentService;
import com.github.lacoming.eventsapi.utils.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCheckerService extends AbstractAccessCheckerService<CommentCheckerService.CommentAccessData> {

    private final CommentService commentService;

    @Override
    protected boolean check(CommentAccessData accessData) {
        return commentService.hasInEvent(
                accessData.commentId,
                accessData.eventId,
                accessData.currentUserId
        );
    }

    @Override
    protected CommentAccessData getAccessData(HttpServletRequest request) {
        return new CommentAccessData(
                getFromPathVariable(request, "id", Long::valueOf),
                getFromRequestParams(request, "eventId", Long::valueOf),
                AuthUtils.getAuthenticatedUser().getId()
        );
    }

    @Override
    public AccessCheckType getType() {
        return AccessCheckType.COMMENT;
    }

    protected record CommentAccessData(Long commentId, Long eventId, Long currentUserId) implements AccessData {

    }
}
