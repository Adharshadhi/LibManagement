package com.adhi.libmanagement.dao;

import com.adhi.libmanagement.model.Dashboard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class DashboardDao {

    private static final String CLASS_NAME = "com.adhi.libmanagement.controller.DashboardDao";

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getDashboardLabel(int dashboardId){
        try{
            Dashboard dashboard = getSingleDashboard(dashboardId);
            List<String> dashboardLabel = new ArrayList<>();
            if(dashboard.getIsLabelQuery() == 1){
                Query query = entityManager.createNativeQuery(dashboard.getDashboardLabels());
                List<Object[]> resultObjData = query.getResultList();
                for (Object[] resultObjDatum : resultObjData) {
                    dashboardLabel.add((String) resultObjDatum[0]);
                }
            }else{
                dashboardLabel = Arrays.asList(dashboard.getDashboardLabels().split(","));
            }
            return dashboardLabel;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in getDashboardLabel method -> " +  ex.getMessage());
            return null;
        }
    }

    public List<Object> getDashboardData(int dashboardId){
        try{
            Dashboard dashboard = getSingleDashboard(dashboardId);
            String queryToExecute = dashboard.getDashboardQuery();
            Query query = entityManager.createNativeQuery(queryToExecute);
            List<Object[]> resultObjData = query.getResultList();
            List<Object> resultData = new ArrayList<>();
            if(dashboard.getIsLabelQuery() == 1){
                for (Object[] resultObjDatum : resultObjData) {
                    resultData.add(resultObjDatum[1]);
                }
            }else {
                for (Object[] resultObjDatum : resultObjData) {
                    resultData.addAll(Arrays.asList(resultObjDatum));
                }
            }
            return resultData;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in getDashboardData method -> " +  ex.getMessage());
            return null;
        }
    }

    public Dashboard getSingleDashboard(int dashboardId){
        try{
            TypedQuery<Dashboard> typedQuery = entityManager.createQuery("FROM Dashboard WHERE dashboardId = :dashboardId", Dashboard.class);
            typedQuery.setParameter("dashboardId",dashboardId);
            Dashboard dashboard = typedQuery.getSingleResult();
            return dashboard;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in getSingleDashboard method -> " +  ex.getMessage());
            return null;
        }
    }

}
