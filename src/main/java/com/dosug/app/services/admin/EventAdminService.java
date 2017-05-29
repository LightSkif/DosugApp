package com.dosug.app.services.admin;

import com.dosug.app.form.admin.AdminUpdateEventForm;

public interface EventAdminService {
    void banEvent(long eventId);

    void allowEvent(long eventId);

    void updateEvent(AdminUpdateEventForm form, long eventId);

}
