import {Component, OnInit} from "@angular/core";
import {RequestService} from "../../service/request.service";
import {UserService} from "../../service/user.service";
import {Request} from "../../model/request.model";
import {ActivatedRoute} from "@angular/router";
import {ToastsManager} from "ng2-toastr";
import {AuthService} from "../../service/auth.service";
import {User} from "../../model/user.model";
import {HistoryService} from "../../service/history.service";
import {History} from "../../model/history.model";
import {Observable} from "rxjs";

@Component({
  selector: 'request-profile',
  templateUrl: 'request-profile.component.html',
  styleUrls: ['request-profile.component.css']
})
export class RequestProfileComponent implements OnInit {

  currentUser: User;
  assigneUser: User;
  request: Request;
  showDescription: boolean = true;
  showHistory: boolean = true;
  showSubRequests: boolean = true;
  showJoinedRequests: boolean = true;
  historyRecords: History[];
  oldValue: string;
  oldValueAssigneeName: string;

  constructor(private requestService: RequestService,
              private userService: UserService,
              private route: ActivatedRoute,
              private toastr: ToastsManager,
              private authService: AuthService,
              private historyService: HistoryService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      let id = +params['id'];

      this.historyService.getHistory(id).subscribe((historyRecords: History[]) => {
        this.historyRecords = historyRecords;
        console.log(historyRecords);
      });

      this.requestService.get(id).subscribe((request: Request) => {
        this.request = request;
        console.log(request)
      });
    });
    this.authService.currentUser.subscribe((user: User) => {
      this.currentUser = user;
    });
  }

  getOldValue(history: History): void {
    var result;

    switch (history.columnName) {
      case "priority_status_id":
        result = "Ha-ha-ha";

        this.oldValue = result;
        //return result;
        break;
      case "progress_status_id":
        result = "Ha-ha-ha";

        this.oldValue = result;
        //return result;
        break;

      case "assignee_id":
      this.userService.get(+history.oldValue).subscribe((user: User) => {
          result = user.firstName;
          console.log("columnName: " + history.columnName);
          console.log("Assignee id: " + +history.oldValue);
          console.log(user);
          console.log("result from case: " + result);

        this.oldValue = result;
        //return result;
        });
        //result = "WWWWWW";
      break;

      case "parent_id":
        result = "Ha-ha-ha";

        this.oldValue = result;
        //return result;
        break;
      default:
        result = history.oldValue;

        this.oldValue = result;
        //return result;
    }

    //result = "QQQQ";
    /*console.log("return: " + result);
    return result;*/
  }

  changeShowDescription() {
    this.showDescription = !this.showDescription;
  }

  changeShowHistory() {
    this.showHistory = !this.showHistory;
  }

  changeShowSubRequests() {
    this.showSubRequests = !this.showSubRequests;
  }

  changeShowJoinedRequests() {
    this.showJoinedRequests = !this.showJoinedRequests;
  }

  updateRequest() {
    this.request.parentId = null;
    this.request.lastChanger = this.currentUser;
    this.requestService.update(this.request)
      .subscribe(() => {
        this.toastr.success("Request updated", "Success")
      });
  }
}
