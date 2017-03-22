import {Component, OnInit} from '@angular/core';
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";
import 'rxjs/Rx';

declare let $: any;

@Component({

  selector: 'admin-home',
  templateUrl: 'admin.component.html',
})
export class AdminComponent implements OnInit {
  requests: Request[];
  progress: Array<number>;
  priority: Array<number>;
  sixMonthsStatistic: Array<number>;

  constructor(private requestService: RequestService) {
  }

  ngOnInit(): void {
    this.requestService.getQuantityRequest().subscribe((s => {
      this.progress = s;
    }));

    this.requestService.getQuantityRequestByPriority().subscribe((s => {
      this.priority = s;
    }));

    this.requestService.getStatisticForSixMonths().subscribe(s => {
      this.sixMonthsStatistic = s;
    })
  }

  setStatisticByProgress() {
   this.pieChartRequest = {
      chartType: 'PieChart',
      dataTable: [
        ['Request', 'Info'],
        ['In progress: '+ this.progress[3], this.progress[3]],
        ['Free: ' + this.progress[1],this.progress[1]],
        ['Registered: ' + this.progress[0], this.progress[0]],
        ['Reopen: ' + this.progress[4], this.progress[4]],
        ['Joined: ' + this.progress[2],this.progress[2]],
      ],
      options: {
        title: 'Request statistic by progress status',
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
      ['Click to see statistic',100],
    ],
    options: {
      title: 'Request statistic by progress status',
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
        ['High: '+ this.priority[0], this.priority[0]],
        ['Normal: ' + this.priority[1],this.priority[1]],
        ['Low: ' + this.priority[2], this.priority[2]],
      ],
      options: {
        title: 'Request statistic by priority status',
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
      ['Click to see statistic',100],
    ],
    options: {
      title: 'Request statistic by priority status',
      legend: { position: 'bottom'
      },
      is3D: true,
      height: 550
    }
  };

  setStatisticForSixMonths(){
   this.pieChartRequestForSixMonths = {
      chartType: 'Gauge',
      dataTable: [
        ['Open', 'Closed'],
        ['Open', this.sixMonthsStatistic[0]],
        ['Closed', this.sixMonthsStatistic[1]]],
      options: {
        title: 'Request statistic for six months',
        width: 600, height: 400,
        redFrom: 90, redTo: 100,
        yellowFrom:75, yellowTo: 90,
        minorTicks: 5
      },
    };
  }

  pieChartRequestForSixMonths = {
    chartType: 'Gauge',
    dataTable: [
      ['Request', 'not closed'],
      ['Click to see open',100],
      ['Click to see closed',100],
    ],
    options: {
      title: 'Request statistic for six months',
      width: 600, height: 400,
      redFrom: 90, redTo: 100,
      yellowFrom:75, yellowTo: 90,
      minorTicks: 5
    }
  };

  setStatistic(): void {
    this.setStatisticForSixMonths();
    this.setStatisticByPriority();
    this.setStatisticByProgress();
  }
}
