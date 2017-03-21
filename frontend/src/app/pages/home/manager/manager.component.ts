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
    this.requestService.getPageCountByAssignee(this.user.id).subscribe((count) => this.myPageCount = count);
  }

  assign(request: Request) {
    request.estimateTimeInDays = 3;
    this.assignRequestComponent.request = request;
    this.assignRequestComponent.modal.open();
  }

  join() {
    this.joinRequestComponent.modal.open();
  }

  updateRequests(requests: Request[]) {
    this.requests = requests;
  }

  toggle(id) {
    if (this.checked.indexOf(id) >= 0) {
      this.checked.splice(this.checked.indexOf(id), 1);
    } else {
      this.checked.push(id);
    }
  }

  createRange(number) {
    let items: number[] = [];
    for (let i = 2; i <= number; i++) {
      items.push(i);
    }
    return items;
  }

  isChecked(id) {
    return this.checked.indexOf(id) > -1;
  }

  uncheckAll() {
    this.checked = [];
  }

  canUnite() {
    return this.checked.length > 1;
  }

  load(data) {
    let page = this.getCurrentPage(data);
    this.fetchFreeRequests(page);
  }

  loadMyRequests(data) {
    let page = this.getCurrentPage(data);
    this.fetchMyRequests(page);
  }

  private getCurrentPage(data): number {
    $('.paginate_button').removeClass('active');
    $(data.target.parentElement).addClass('active');
    return +data.target.text;
  }

  private fetchFreeRequests(page: number) {
    this.requestService.getFree(page).subscribe((requests: Request[]) => {
      this.requests = requests;
    });
    this.requestService.getPageCountFree().subscribe((count) => this.pageCount = count);
  }
}
