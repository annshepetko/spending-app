package com.ann.spending.dashboard;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.dashboard.service.DashBoardViewService;
import com.ann.spending.dashboard.view.DashboardView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final DashBoardViewService dashBoardViewService;

    public DashboardController(DashBoardViewService dashBoardViewService) {
        this.dashBoardViewService = dashBoardViewService;
    }

    @GetMapping
    public ResponseEntity<DashboardView> retrieveDashboard(@RequestAttribute("user") User user){

        return ResponseEntity.ok(dashBoardViewService.retrieveDashboard(user));
    }
}
