package com.github.lacoming.eventsapi.service.checker;

import com.github.lacoming.eventsapi.aop.AccessCheckType;
import com.github.lacoming.eventsapi.entity.Event;
import com.github.lacoming.eventsapi.service.EventService;
import com.github.lacoming.eventsapi.utils.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventCheckerService extends AbstractAccessCheckerService<EventCheckerService.EventAccessData> {

    private final EventService eventService;

    @Override
    protected boolean check(EventAccessData accessData) {
        return eventService.isEventCreator(
                accessData.eventId,
                accessData.currentUserId
        );
    }

    @Override
    protected EventAccessData getAccessData(HttpServletRequest request) {
        var eventId = getFromPathVariable(
                request,
                "id",
                Long::valueOf
        );
        return new EventAccessData(eventId, AuthUtils.getAuthenticatedUser().getId());
    }

    @Override
    public AccessCheckType getType() {
        return AccessCheckType.EVENT;
    }

    protected record EventAccessData(Long eventId, Long currentUserId) implements AccessData {

    }
}
