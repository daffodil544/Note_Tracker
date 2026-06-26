package com.example.notetracker.user.api.model;

import com.example.notetracker.authentication.Role;
import com.example.notetracker.authentication.Sender;
import com.example.notetracker.user.api.UserId;
import io.fluxzero.sdk.common.serialization.FilterContent;
import io.fluxzero.sdk.modeling.Aggregate;
import io.fluxzero.sdk.modeling.EventPublication;
import lombok.Builder;

@Aggregate(searchable = true, eventPublication = EventPublication.IF_MODIFIED)
@Builder(toBuilder = true)
public record UserProfile(UserId userId, UserDetails details, Role role) {
    @FilterContent
    UserProfile filter(Sender sender) {
        return sender.isAuthorizedFor(userId) ? this : null;
    }
}
