import {Component, OnInit} from "@angular/core";
import {RequestService} from "../../service/request.service";
import {Request} from "../../model/request.model";
import {ActivatedRoute} from "@angular/router";
import {ToastsManager} from "ng2-toastr";
import {AuthService} from "../../service/auth.service";
import {User} from "../../model/user.model";
import {HistoryService} from "../../service/history.service";
import {History} from "../../model/history.model";

@Component({
  selector: 'request-profile',
  templateUrl: 'request-profile.component.html',
  styleUrls: ['request-profile.component.css']
})
export class RequestProfileComponent implements OnInit {

  currentUser: User;
  request: Request;
  showDescription: boolean = true;
  showHistory: boolean = true;
  showSubRequests: boolean = true;
  showJoinedRequests: boolean = true;
  historyRecords: History[];

  constructor(private requestService: RequestService,
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
