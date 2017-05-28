package com.dosug.app.controller.admin;

import com.dosug.app.form.admin.AdminUpdateUserForm;
import com.dosug.app.repository.UserRepository;
import com.dosug.app.respose.model.ApiError;
import com.dosug.app.respose.model.Response;
import com.dosug.app.respose.viewmodel.EventPreview;
import com.dosug.app.respose.viewmodel.admin.UserPreview;
import com.dosug.app.respose.viewmodel.admin.UserView;
import com.dosug.app.services.admin.UserAdminService;
import com.dosug.app.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users")
public class UserAdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UserAdminService userAdminService;


    @GetMapping("/test")
    public String test() {
        return "HelLLLLLLL";
    }

    @GetMapping("/")
    public List<UserPreview> getUsers(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                      @RequestParam(name = "usersPerPage", defaultValue = "20") int userPerPage) {

        return userRepository.findAll(new PageRequest(pageNumber - 1, userPerPage))
                .map(UserPreview::new)
                .getContent();
    }

    @GetMapping("/search")
    public List<UserPreview> searchUsers(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                         @RequestParam(name = "usersPerPage", defaultValue = "20") int userPerPage,
                                         @RequestParam(name = "part") String part) {

        PageRequest pageable = new PageRequest(pageNumber - 1, userPerPage);

        return userRepository.simpleSearch(part, pageable)
                .map(UserPreview::new)
                .getContent();
    }

    @GetMapping("/search/count")
    public long searchUsersCount(@RequestParam(name = "part") String part) {

        return userRepository.simpleSearchCount(part);
    }

    @GetMapping("/count")
    public long userCount() {
        return userRepository.count();
    }

    @GetMapping("/{id}")
    public UserView getUserById(@PathVariable(name = "id")long userId) {
        return new UserView(userAdminService.getUser(userId));
    }

    @PostMapping(value = "/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response updateUser(@RequestBody AdminUpdateUserForm updateForm,
                               @PathVariable(name = "id") long userId,
                               Locale locale) {

        Response res = new Response();

        List<ApiError> apiErrors = validationService.validate(updateForm, locale);
        if(!apiErrors.isEmpty()) {
            return res.failure(apiErrors);
        }

        userAdminService.updateUser(updateForm, userId);

        return res.success(null);
    }

    @PostMapping("/{id}/ban")
    public Response banUser(@PathVariable(name = "id")long userId) {
        Response response = new Response();

        userAdminService.banUser(userId);

        return response.success(null);
    }

    @PostMapping("/{id}/delete")
    public Response deleteUser(@PathVariable(name = "id")long userId) {
        Response response = new Response();

        userRepository.delete(userId);

        return response.success(null);
    }

    @PostMapping("/{id}/unban")
    public Response unbanUser(@PathVariable(name = "id")long userId) {
        Response response = new Response();

        userAdminService.unbanUser(userId);

        return response.success(null);
    }


    @GetMapping("/{userId}/events")
    public List<EventPreview> findByUser(@PathVariable(name = "userId")long userId) {
        Response response = new Response();

        return userAdminService.getEventsForUser(userId).stream()
                    .map(EventPreview::new)
                    .collect(Collectors.toList());
    }
}
