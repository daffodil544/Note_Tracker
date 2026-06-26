package com.example.notetracker.user.api;

import com.example.notetracker.authentication.RequiresRole;
import com.example.notetracker.authentication.Role;
import com.example.notetracker.user.api.model.UserProfile;
import io.fluxzero.sdk.persisting.eventsourcing.Apply;
import jakarta.validation.constraints.NotNull;

@RequiresRole(Role.ADMIN)
public record SetRole(@NotNull UserId userId, Role role) implements UserUpdate {
    @Apply
    UserProfile apply(UserProfile profile) {
        return profile.toBuilder().role(role).build();
    }
}
