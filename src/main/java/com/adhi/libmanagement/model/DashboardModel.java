package com.adhi.libmanagement.model;

import java.util.List;

public class DashboardModel {
    private List<String> dashboardLabels;
    private List<Object> dashboardData;
    private Dashboard dashboard;

    public DashboardModel() {}

    public DashboardModel(List<String> dashboardLabels, List<Object> dashboardData, Dashboard dashboard) {
        this.dashboardLabels = dashboardLabels;
        this.dashboardData = dashboardData;
        this.dashboard = dashboard;
    }

    public List<String> getDashboardLabels() {
        return dashboardLabels;
    }

    public void setDashboardLabels(List<String> dashboardLabels) {
        this.dashboardLabels = dashboardLabels;
    }

    public List<Object> getDashboardData() {
        return dashboardData;
    }

    public void setDashboardData(List<Object> dashboardData) {
        this.dashboardData = dashboardData;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public String toString() {
        return "DashboardModel{" +
                "dashboardLabels=" + dashboardLabels +
                ", dashboardData=" + dashboardData +
                ", dashboard=" + dashboard +
                '}';
    }
}
