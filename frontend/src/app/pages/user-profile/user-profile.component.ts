import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {User} from "../../model/user.model";
import {UserService} from "../../service/user.service";
import {Request} from "../../model/request.model";
import {RequestService} from "../../service/request.service";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'user-profile',
  templateUrl: 'user-profile.component.html',
  styleUrls: ['user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user: User;
  requests: Array<number>;
  statisticForUser: Array<number>;
  hasRequest: boolean = false;
  hasSixMonthsRec: boolean = false;
  hasAnyRequest: boolean = false;
  setPeriod: number;
  when: string = 'for 6 months';

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private requestService: RequestService,
              private toast: ToastsManager) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      let id = +params['id'];
      this.userService.get(id).subscribe((user: User) => {
        this.user = user;
        this.requestService.getQuantityForUserByProgressStatus(this.user.id).subscribe(s => {
          if (s[0]!= 0 || s[1]!= 0 || s[2]!= 0) {
            this.hasRequest = true;
            this.requests = s;
            this.setStatisticByProgressStatus();
          } else {
            this.hasAnyRequest = true;
          }
        }, e => this.toast.warning("can't research statistic",'warning'));
        this.requestService.getOpenClosedRequestForUser(this.user.id, 6).subscribe(s => {
          if (s[0]!= 0 || s[1]!= 0) {
            this.hasSixMonthsRec = true;
            this.when = 'for ' + 6 + ' months.';
            this.statisticForUser = s;
            this.setStatisticOpenClosedReq();
          }
        }, e => this.toast.warning("can't research statistic",'warning'));
      });
    });
  }

  setStatisticByProgressStatus(){
    this.pieChartRequest = {
      chartType: 'PieChart',
      dataTable: [
        ['Request', 'Info'],
        ['Free: ' + this.requests[0],this.requests[0]],
        ['Joined: ' + this.requests[1],this.requests[1]],
        ['In progress: '+ this.requests[2], this.requests[2]],
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

  setStatisticOpenClosedReq(){
    this.pieChartRequestForOpenClosed = {
      chartType: 'BarChart',
      dataTable: [
        ['Status', 'Open','Closed'],
        ['Status', this.statisticForUser[0],this.statisticForUser[1]]
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

  pieChartRequestForOpenClosed = {
    chartType: 'BarChart',
    dataTable: [
      ['Request', 'Open', 'Closed'],
      ['Click to see open',100,100]
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

  setStatisticByPeriod(howLong: number): void {
    this.requestService.getOpenClosedRequestForUser(this.user.id, howLong).subscribe(s => {
      if (s[0]!= 0 || s[1]!= 0) {
        this.hasSixMonthsRec = true;
        this.when = 'for ' + howLong + ' months.';
        this.statisticForUser = s;
        this.setStatisticOpenClosedReq();
      }
    }, e => this.toast.warning("can't research statistic",'warning'));
  }
}
