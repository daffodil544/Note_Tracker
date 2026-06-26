package com.example.notetracker.user.api;

import com.example.notetracker.user.api.model.UserProfile;
import io.fluxzero.sdk.modeling.Id;

public class UserId extends Id<UserProfile> {
    public UserId(String id) {
        super(id);
    }
}
