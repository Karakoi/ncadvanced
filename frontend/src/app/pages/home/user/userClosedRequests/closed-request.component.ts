import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../../../service/auth.service";
import {EmployeeService} from "../../../../service/employee.service";
import {User} from "../../../../model/user.model";
import {Request} from "../../../../model/request.model";


@Component({
  selector: 'user-home',
  templateUrl: 'closed-request.component.html',
  styleUrls: ['closed-request.component.css']
})
export class ClosedRequest implements OnInit {
  private loaded: boolean = false;
  private requests: Request[];
  private selected: Set<number> = new Set();
  private currentUser: User;
  private totalItems: number = 20;
  private per: number = 20;

  constructor(private authService: AuthService,
              private employeeService: EmployeeService) {
  }

  settings = {
    delete: false,
    add: true,
    info: true,
    multiSelect: true,
    filterRow: true,
    columns: {
      title: true,
      dateOfCreation: true,
      priorityStatus: true,
      progressStatus: true,
      reporter: false,
      assignee: true,
    }
  }


  ngOnInit() {
    this.authService.currentUser.subscribe(u => {
      this.currentUser = u;
      this.employeeService.getClosedRequestsByReporter(u.id, 1).subscribe(requests => {
        this.requests = requests;
        this.employeeService.countClosedRequestsByReporter(u.id).subscribe(count => {
          this.totalItems = count;
          this.loaded = true;
        })
      });
    })
  }

  pageChange(data) {
    this.employeeService.getClosedRequestsByReporter(this.currentUser.id, data.page).subscribe(requests => {
      this.requests = requests;
    })
  }

  select(data) {
    this.selected = data;
  }

  reopen() {
    let sel = Array.from(this.selected);
    this.employeeService.reopenRequests(sel).subscribe(
      (success) => {
        this.requests = this.requests.map(r => r).filter(r => !this.selected.has(r.id))
        this.selected.clear();
    })
  }
}
