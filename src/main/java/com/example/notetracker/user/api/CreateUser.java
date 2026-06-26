package com.example.notetracker.user.api;

import com.example.notetracker.authentication.RequiresRole;
import com.example.notetracker.authentication.Role;
import com.example.notetracker.user.api.model.UserDetails;
import com.example.notetracker.user.api.model.UserProfile;
import io.fluxzero.sdk.persisting.eventsourcing.Apply;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RequiresRole(Role.ADMIN)
public record CreateUser(@NotNull UserId userId,
                         @NotNull @Valid UserDetails details,
                         Role role) implements UserUpdate {
    @Apply
    UserProfile apply() {
        return UserProfile.builder().userId(userId).details(details).role(role).build();
    }
}
