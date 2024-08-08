package com.adhi.libmanagement.service;

import com.adhi.libmanagement.dao.DashboardDao;
import com.adhi.libmanagement.model.Dashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private DashboardDao dashboardDao;

    @Autowired
    public DashboardService(DashboardDao dashboardDao){
        this.dashboardDao = dashboardDao;
    }

    public List<String> getDashboardLabel(int dashboardId){
        return dashboardDao.getDashboardLabel(dashboardId);
    }

    public List<Object> getDashboardData(int dashboardId){
        return dashboardDao.getDashboardData(dashboardId);
    }

    public Dashboard getSingleDashboard(int dashboardId){
        return dashboardDao.getSingleDashboard(dashboardId);
    }

}
