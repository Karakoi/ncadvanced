import {Component, OnInit, ViewChild} from "@angular/core";
import {RequestService} from "../../service/request.service";
import {Request} from "../../model/request.model";
import {ActivatedRoute} from "@angular/router";
import {ToastsManager} from "ng2-toastr";
import {AuthService} from "../../service/auth.service";
import {User} from "../../model/user.model";
import {HistoryService} from "../../service/history.service";
import {History} from "../../model/history.model";
import {DeleteSubRequestComponent} from "./sub-request-delete/delete-sub-request.component";
import {AddSubRequestComponent} from "./sub-request-add/add-sub-request.component";

@Component({
  selector: 'request-profile',
  templateUrl: 'request-profile.component.html',
  styleUrls: ['request-profile.component.css']
})
export class RequestProfileComponent implements OnInit {

  currentUser: User;
  request: Request;
  type: string;
  showDescription: boolean = true;
  showHistory: boolean = true;
  showSubRequests: boolean = true;
  showJoinedRequests: boolean = true;
  historyRecords: History[];
  subRequests: Request[];
  joinedRequests: Request[];

  @ViewChild(DeleteSubRequestComponent)
  deleteSubRequestComponent: DeleteSubRequestComponent;

  @ViewChild(AddSubRequestComponent)
  addSubRequestComponent: AddSubRequestComponent;

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
        this.type = this.getRequestType(request);
        /*console.log(request)*/
      });

      this.requestService.getSubRequests(id).subscribe((subRequests: Request[]) => {
        this.subRequests = subRequests;
        /*console.log(subRequests)*/
      });

      this.requestService.getJoinedRequests(id).subscribe((joinedRequests: Request[]) => {
        this.joinedRequests = joinedRequests;
        /*console.log(joinedRequests)*/
      });
    });
    this.authService.currentUser.subscribe((user: User) => {
      this.currentUser = user;
    });
  }


  showHistoryMessage(history: History): string{
    let text: string;
    switch (history.columnName){

      case "title":
        text = "Title was changed from \"" + history.oldValue + "\" to \"" + history.newValue + "\"";
        break;
      case "estimate_time_in_date":
        text = "Estimate time (in day) was changed from \"" + history.oldValue + "\" to \"" + history.newValue + "\"";
        break;

      case "description":
        text = "Description was changed from \"" +
          history.demonstrationOfOldValue + "\" to \"" + history.demonstrationOfNewValue + "\"";
        break;
      case "priority_status_id":
        text = "Priority was changed from \"" +
          history.demonstrationOfOldValue + "\" to \"" + history.demonstrationOfNewValue + "\"";
        break;
      case "progress_status_id":
        text = "Progress status was changed from \"" +
          history.demonstrationOfOldValue + "\" to \"" + history.demonstrationOfNewValue + "\"";
        break;

      case "assignee_id":
        text = "This request was assigned";
        break;

      case "parent_id":
        if(history.newValue == null){
          text = "This request was unjoined from \"" + history.demonstrationOfOldValue + "\" request";
        } else {
          text = "This request was joined in \"" + history.demonstrationOfNewValue + "\" request";
        }
        break;

      default:
        text = "Some changes";
    }

    return text;
  }

  /*showHistoryValue(generalValue: string, demonstrationValue: string){
    if(demonstrationValue != null){
      return demonstrationValue;
    } else {
      if(generalValue != null){
        return generalValue;
      } else {
        return "empty value";
      }
    }
  }*/

  openAddSubRequestModal(): void {
    this.addSubRequestComponent.modal.open();
  }

  openDeleteSubRequestModal(subRequest: Request): void {
    this.deleteSubRequestComponent.subRequest = subRequest;
    this.deleteSubRequestComponent.modal.open();
  }

  updateSubRequests(subRequests: Request[]) {
    this.subRequests = subRequests;
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
    if (this.request.assignee.id === 0){
      this.request.assignee = <User>{};
    }
    this.request.lastChanger = this.currentUser;
    this.requestService.update(this.request)
      .subscribe(() => {
        this.toastr.success("Request updated", "Success")
      });
  }

  getRequestType(request): string  {
    if (request.progressStatus.name == null && request.priorityStatus.name == null) {
      return "Sub request"
    } else if (request.progressStatus.name == 'Joined') {
      return "Joined request";
    } else {
      return "Request"
    }
  }

  isFree(request):boolean {
    if (request.progressStatus.name == 'Free') {
      return false;
    } else {
      return true;
    }
  }

  isInProgress(request):boolean {
    if (request.progressStatus.name == 'In progress') {
      return true;
    } else {
      return false;
    }
  }
}
