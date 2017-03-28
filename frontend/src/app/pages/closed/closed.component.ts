import {Component} from "@angular/core";
import {RequestService} from "../../service/request.service";
import {AuthService} from "../../service/auth.service";
import {Request} from "../../model/request.model";
import {User} from "../../model/user.model";

declare let $:any;

@Component({
  selector: 'closed',
  templateUrl: 'closed.component.html',
  styleUrls: ['closed.component.css']
})
export class ClosedComponent {
  requests:Request[] = [];
  pageCount:number;
  currentUserId: number;
  pageSize: number = 20;

  settings = {
    delete: false,
    add: false,
    info: true,
    multiSelect: false,
    filterRow: true,
    assign: false,
    join: false,
    reopen: true,
    close: false,
    columns: {
      title: true,
      estimate: true,
      dateOfCreation: true,
      priorityStatus: true,
      progressStatus: false,
      reporter: true,
      assignee: false,
    }
  };

  constructor(private requestService:RequestService,
              private authService:AuthService) {
  }

  ngOnInit() {
    this.authService.currentUser.subscribe((user:User) => {
      this.currentUserId = user.id;
      this.requestService.getClosedAssigned(1, this.pageSize, this.currentUserId).subscribe((requests:Request[]) => {
        this.requests = requests;
      });
      this.requestService.getClosedAssignedPageCount(this.currentUserId).subscribe((count) => this.pageCount = count);
    });
  }

  pageChange(data){
    this.requestService.getClosedAssigned(data.page, this.pageSize, this.currentUserId).subscribe(requests => {
      this.requests = requests;
    })
  }

  perChangeLoad(pageData) {
    this.pageSize = pageData.size;
    this.requestService.getClosedAssigned(pageData.page, pageData.size, this.currentUserId).subscribe(requests => {
      this.requests = requests;
    })
  }
}
