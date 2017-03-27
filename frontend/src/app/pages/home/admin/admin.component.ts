import {Component, OnInit} from '@angular/core';
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";
import 'rxjs/Rx';

@Component({

  selector: 'admin-home',
  templateUrl: 'admin.component.html',
  styleUrls: ['admin.component.css']
})
export class AdminComponent implements OnInit {
  statisticForAdminDashBoard: Array<number>;
  totalUsers: number;
  totalRequests: number;
  runningToday: number;
  requestToday: number;
  when: string = 'for six months';

  constructor(private requestService: RequestService) {}

  ngOnInit(): void {
    this.setStatisticForSixMonths();

    this.requestService.getTotalUsers().subscribe(s => {
      this.totalUsers = s;
    });

    this.requestService.getTotalRequests().subscribe(s => {
      this.totalRequests = s;
    });

    this.requestService.getRequestToday().subscribe(s => {
      this.requestToday = s;
    });

    this.requestService.getRunningToday().subscribe(s => {
      this.runningToday = s;
    })
  }

  setStatisticByProgress() {
   this.pieChartRequest = {
      chartType: 'PieChart',
      dataTable: [
        ['Request', 'Info'],
        ['Free: ' + this.statisticForAdminDashBoard[0],this.statisticForAdminDashBoard[0]],
        ['Joined: ' + this.statisticForAdminDashBoard[1],this.statisticForAdminDashBoard[1]],
        ['In progress: '+ this.statisticForAdminDashBoard[2], this.statisticForAdminDashBoard[2]],
      ],
      options: {
        title: 'Request statistic by progress',
        legend: { position: 'bottom'
        },
        is3D: true,
        height: 550
      },
    };
  }

  pieChartRequest = {
    chartType: 'PieChart',
    dataTable: [
      ['Request', 'Info'],
      ['Request statistic',100],
    ],
    options: {
      title: 'Request statistic by progress',
      legend: { position: 'bottom'
      },
      is3D: true,
      height: 550
    }
  };

  setStatisticByPriority(){
   this.pieChartRequestPriority = {
      chartType: 'PieChart',
      dataTable: [
        ['Request', 'Info'],
        ['High: '+ this.statisticForAdminDashBoard[5], this.statisticForAdminDashBoard[5]],
        ['Normal: ' + this.statisticForAdminDashBoard[6],this.statisticForAdminDashBoard[6]],
        ['Low: ' + this.statisticForAdminDashBoard[7], this.statisticForAdminDashBoard[7]],
      ],
      options: {
        title: 'Request statistic by priority',
        legend: { position: 'bottom'
        },
        is3D: true,
        height: 550
      },
    };
  }

  pieChartRequestPriority = {
    chartType: 'PieChart',
    dataTable: [
      ['Request', 'Info'],
      ['Request statistic',100],
    ],
    options: {
      title: 'Request statistic by priority',
      legend: { position: 'bottom'
      },
      is3D: true,
      height: 550
    }
  };

  setStatisticForBarChart(){
   this.pieChartRequestForSixMonths = {
      chartType: 'BarChart',
      dataTable: [
        ['Requests', 'Open','Closed'],
        ['Status', this.statisticForAdminDashBoard[4], this.statisticForAdminDashBoard[3]],
        ],
      options: {
        hAxis: {
          title: 'Request statistic ' + this.when,
          minValue: 0,
          textStyle: {
            bold: true,
            fontSize: 12,
            color: '#4d4d4d'
          },
          titleTextStyle: {
            bold: true,
            fontSize: 18,
            color: '#4d4d4d'
          }
        },
        vAxis: {
          title: 'Requests',
          textStyle: {
            fontSize: 14,
            bold: true,
            color: '#848484'
          },
          titleTextStyle: {
            fontSize: 14,
            bold: true,
            color: '#848484'
          }
        }
      }
    };
  }

  pieChartRequestForSixMonths = {
    chartType: 'BarChart',
    dataTable: [
      ['Request', 'Open','Closed'],
      ['Status',100,100],
    ],
    options: {
      hAxis: {
        title: 'Request statistic ' + this.when,
        minValue: 0,
        textStyle: {
          bold: true,
          fontSize: 12,
          color: '#4d4d4d'
        },
        titleTextStyle: {
          bold: true,
          fontSize: 18,
          color: '#4d4d4d'
        }
      },
      vAxis: {
        title: 'Requests',
        textStyle: {
          fontSize: 14,
          bold: true,
          color: '#848484'
        },
        titleTextStyle: {
          fontSize: 14,
          bold: true,
          color: '#848484'
        }
      }
    }
  };

  setStatisticForTwelveMonths(): void {
    this.requestService.getStatisticForAdminDashBoard(12).subscribe(s => {
      this.statisticForAdminDashBoard = s;
      this.when = 'for twelve months';
      this.setStatisticByProgress();
      this.setStatisticByPriority();
      this.setStatisticForBarChart();
    });
  }

  setStatisticForSixMonths(): void {
    this.requestService.getStatisticForAdminDashBoard(6).subscribe(s => {
      this.statisticForAdminDashBoard = s;
      this.when = 'for six months';
      this.setStatisticByProgress();
      this.setStatisticByPriority();
      this.setStatisticForBarChart();
    });
  }

  setStatisticForAllTime(): void {
    this.requestService.getStatisticForAdminDashBoard(10).subscribe(s => {
      this.statisticForAdminDashBoard = s;
      this.when = 'for all time';
      this.setStatisticByProgress();
      this.setStatisticByPriority();
      this.setStatisticForBarChart();
    });
  }
}
