package com.github.lacoming.eventsapi.event;

import com.github.lacoming.eventsapi.event.model.EmailNotificationEvent;
import com.github.lacoming.eventsapi.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationEmailListener {

    private final SubscriptionService subscriptionService;

    @EventListener(EmailNotificationEvent.class)
    public void onEvent(EmailNotificationEvent event) {
        log.info("Send email for subscribers. Event: " + event);
        subscriptionService.sendNotifications(
                event.getOrganization(),
                event.getCategories(),
                event.getEventName()
        );
    }
}
