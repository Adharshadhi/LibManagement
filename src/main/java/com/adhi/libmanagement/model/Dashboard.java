package com.adhi.libmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name="dashboards")
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dashboardId;

    @Column(name = "dashboard_name")
    private String dashboardName;

    @Column(name = "labels")
    private String dashboardLabels;

    @Column(name = "query")
    private String dashboardQuery;

    @Column(name = "type")
    private String dashboardType;

    @Column(name = "is_label_query")
    private int isLabelQuery;

    public int getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(int dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getDashboardName() {
        return dashboardName;
    }

    public void setDashboardName(String dashboardName) {
        this.dashboardName = dashboardName;
    }

    public String getDashboardLabels() {
        return dashboardLabels;
    }

    public void setDashboardLabels(String dashboardLabels) {
        this.dashboardLabels = dashboardLabels;
    }

    public String getDashboardQuery() {
        return dashboardQuery;
    }

    public void setDashboardQuery(String dashboardQuery) {
        this.dashboardQuery = dashboardQuery;
    }

    public String getDashboardType() {
        return dashboardType;
    }

    public void setDashboardType(String dashboardType) {
        this.dashboardType = dashboardType;
    }

    public int getIsLabelQuery() {
        return isLabelQuery;
    }

    public void setIsLabelQuery(int isLabelQuery) {
        this.isLabelQuery = isLabelQuery;
    }

    @Override
    public String toString() {
        return "Dashboard{" +
                "dashboardId=" + dashboardId +
                ", dashboardName='" + dashboardName + '\'' +
                ", dashboardLabels='" + dashboardLabels + '\'' +
                ", dashboardQuery='" + dashboardQuery + '\'' +
                ", dashboardType='" + dashboardType + '\'' +
                ", isLabelQuery=" + isLabelQuery +
                '}';
    }
}
