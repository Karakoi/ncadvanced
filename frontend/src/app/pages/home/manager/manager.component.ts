import {Component, ViewChild} from "@angular/core";
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";
import {AssignRequestComponent} from "../../../pages/home/manager/request-assign/assign-request.component";
import {JoinRequestComponent} from "../../../pages/home/manager/request-join/join-request.component";
import {AuthService} from "../../../service/auth.service";
import {User} from "../../../model/user.model";

declare let $: any;

@Component({
  selector: 'manager-home',
  templateUrl: 'manager.component.html',
  styleUrls: ['manager.component.css']
})
export class ManagerComponent {
  selected;
  requests: Request[] = [];
  myRequests: Request[] = [];
  user: User;
  checked: number[] = [];
  pageCount: number;
  myPageCount: number;

  @ViewChild(AssignRequestComponent)
  assignRequestComponent: AssignRequestComponent;
  @ViewChild(JoinRequestComponent)
  joinRequestComponent: JoinRequestComponent;


  constructor(private requestService: RequestService,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.authService.currentUser.subscribe((user: User) => {
      this.user = user;
    });
    this.fetchFreeRequests(1);
  }

  fetchMyRequests(page: number) {
    this.requestService.getAllByAssignee(this.user.id, page).subscribe((requests: Request[]) => {
      this.myRequests = requests;
    });
    this.requestService.getRequestCountByAssignee(this.user.id).subscribe((count) => {this.myPageCount = count; console.log(count)});
  }

  pageChange(data){
    this.requestService.getFree(data.page).subscribe(requests => {
      this.requests = requests;
    })
  }

  settings = {
    delete: false,
    add: false,
    info: true,
    multiSelect: true,
    filterRow: true,
    join: true,
    assign: true,
    columns: {
      title: true,
      estimate: false,
      dateOfCreation: true,
      priorityStatus: true,
      progressStatus: true,
      reporter: true,
      assignee: false,
    }
  }

  mySettings = {
    delete: false,
    add: false,
    info: true,
    multiSelect: false,
    filterRow: true,
    assign: false,
    columns: {
      title: true,
      estimate: true,
      dateOfCreation: true,
      priorityStatus: true,
      progressStatus: true,
      reporter: true,
      assignee: false,
    }
  }


  join() {
    this.joinRequestComponent.modal.open();
  }

  updateRequests(requests: Request[]) {
    this.requests = requests;
  }

  select(data) {
    this.selected = Array.from(data);
    console.log(this.selected)
  }


  isChecked(id) {
    return this.checked.indexOf(id) > -1;
  }

  uncheckAll() {
    this.selected = [];
    console.log(this.selected)
    this.checked = [];
  }

  canUnite() {
    return this.checked.length > 1;
  }

  private fetchFreeRequests(page: number) {
    this.requestService.getFree(page).subscribe((requests: Request[]) => {
      this.requests = requests;
    });
    this.requestService.getPageCountFree().subscribe((count) => this.pageCount = count);
  }
}
