function loadDashboards(totalDashboards){
    let counter = 0;
    let canvasContainerOne = document.querySelector(".canvaContainerOne");
    let canvasContainerTwo = document.querySelector(".canvaContainerTwo");
    if(document.querySelector("canvas") !== null && counter === 0 ){
        canvasContainerOne.innerHTML = "";
        canvasContainerTwo.innerHTML = "";
    }
    let backgroundClr = generateRandomColors(5);
    let borderClr = generateRandomColors(5);
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
                            borderWidth: 1,
                            backgroundColor: backgroundClr,
                            borderColor: borderClr
                        }]
                    },
                    options: {
                        responsive: false
                    }
                });
            })
            .catch(error => console.log("Exception caught"+error));
        counter++;
        (counter % 2 !== 0) ? canvasContainerOne.append(chartElement) : canvasContainerTwo.append(chartElement);
    }
}

function generateRandomColors(count){
    let colorArray = [];
    const aVal = 0.5;
    for(let i=0; i<count; i++){
        let rVal = Math.floor(Math.random() * 256);
        let gVal = Math.floor(Math.random() * 256);
        let bVal = Math.floor(Math.random() * 256);
        colorArray.push("rgb(" + rVal + ", " + gVal + ", " + bVal + ", " + aVal + ")");
    }
    return colorArray;
}