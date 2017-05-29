package com.dosug.app.services.admin;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.User;
import com.dosug.app.form.admin.AdminUpdateUserForm;

import java.util.List;

public interface UserAdminService {
  void updateUser(AdminUpdateUserForm form, long userId);

  void banUser(long userId);

  void unbanUser(long userId);

  List<Event> getEventsForUser(long userId);

  User getUser(long userId);
}
