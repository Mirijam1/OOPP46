package nl.tudelft.gogreen.server.controller;

import nl.tudelft.gogreen.server.service.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    private final IStatusService statusService;

    @Autowired
    public StatusController(IStatusService statusService) {
        this.statusService = statusService;
    }

    @RequestMapping(
            path = "/test",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
        Map<String, String> getStatus() {
        return Collections.singletonMap("response", statusService.getStatus());
    }

    @RequestMapping(
            path = "/restricted/test",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
        Map<String, String> getRestrictedStatus() {
        return Collections.singletonMap("response", statusService.getRestrictedStatus());
    }

    @RequestMapping(
            path = "/admin/test",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
        Map<String, String> getAdminStatus() {
        return Collections.singletonMap("response", statusService.getAdminStatus());
    }
}
