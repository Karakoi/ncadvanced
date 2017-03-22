import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {User} from "../../model/user.model";
import {UserService} from "../../service/user.service";
import {Request} from "../../model/request.model";
import {RequestService} from "../../service/request.service";

@Component({
  selector: 'user-profile',
  templateUrl: 'user-profile.component.html',
  styleUrls: ['user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user: User;
  requests: Array<number>;
  sixMonthsStatistic: Array<number>;

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private requestService: RequestService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      let id = +params['id'];
      this.userService.get(id).subscribe((user: User) => {
        this.user = user;
        this.requestService.getQuantityForUser(this.user.id).subscribe(s => {
          this.requests = s;
        });
        this.requestService.getStatisticForSixMonthsForUser(this.user.id).subscribe(s => {
          this.sixMonthsStatistic = s;
        });
      });
    });
  }

  setStatisticByProgressStatus(){
    this.pieChartRequest = {
      chartType: 'PieChart',
      dataTable: [
        ['Request', 'Info'],
        ['In progress: '+ this.requests[3], this.requests[3]],
        ['Free: ' + this.requests[1],this.requests[1]],
        ['Registered: ' + this.requests[0], this.requests[0]],
        ['Reopen: ' + this.requests[4], this.requests[4]],
        ['Joined: ' + this.requests[2],this.requests[2]],
      ],
      options: {
        title: 'Your request statistic',
        legend: { position: 'bottom'
        },
        is3D: true,
        height: 400
      }
    };
  }

  pieChartRequest = {
    chartType: 'PieChart',
    dataTable: [
      ['Request', 'Info'],
      ['Click to see statistic',100],
    ],
    options: {
      title: 'Your Request statistic',
      legend: { position: 'bottom'
      },
      is3D: true,
      height: 400
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
    this.setStatisticByProgressStatus();
    this.setStatisticForSixMonths();
  }
}
