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

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private requestService: RequestService) {

  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      let id = +params['id'];
      this.userService.get(id).subscribe((user: User) => {
        console.log(user)
        this.user = user;
      });
    });

    this.requestService.getQuantityRequest().subscribe(s => {
      this.requests = s;
    });

  }

  setStatistic(){
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
        height: 550,
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
      height: 550
    }
  };
}
