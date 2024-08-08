package com.adhi.libmanagement.controller;

import com.adhi.libmanagement.model.Dashboard;
import com.adhi.libmanagement.model.DashboardModel;
import com.adhi.libmanagement.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DashboardController {

    private DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService){
        this.dashboardService = dashboardService;
    }

    @GetMapping("/getDashboardData")
    public DashboardModel getDashboardData(@RequestParam(defaultValue = "0") Integer dashboardId){
        List<String> dashboardLabels = dashboardService.getDashboardLabel(dashboardId);
        List dashboardData = dashboardService.getDashboardData(dashboardId);
        Dashboard dashboard = dashboardService.getSingleDashboard(dashboardId);
        DashboardModel dashboardModel = new DashboardModel();
        dashboardModel.setDashboardLabels(dashboardLabels);
        dashboardModel.setDashboardData(dashboardData);
        dashboardModel.setDashboard(dashboard);
        return dashboardModel;
    }

}
