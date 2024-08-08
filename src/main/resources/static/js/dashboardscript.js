function loadDashboards(totalDashboards){
    let counter = 0;
    let canvaContainerOne = document.querySelector(".canvaContainerOne");
    let canvaContainerTwo = document.querySelector(".canvaContainerTwo");
    while(totalDashboards > counter){
        let chartElement = document.createElement("canvas");
        chartElement.setAttribute("id","mychart"+(counter+1));
        fetch('/getDashboardData?dashboardId='+(counter+1))
            .then(response => response.json())
            .then(data => {
                new Chart(chartElement, {
                    type: data.dashboard.dashboardType,
                    data: {
                        labels: data.dashboardLabels,
                        datasets: [{
                            label: data.dashboard.dashboardName,
                            data: data.dashboardData,
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: false
                    }
                });
            })
            .catch(error => console.log("Exception caught"+error));
        counter++;
        (counter % 2 !== 0) ? canvaContainerOne.append(chartElement) : canvaContainerTwo.append(chartElement);
    }
}
