import {Component} from "@angular/core";
import {RequestService} from "../../service/request.service";
import {AuthService} from "../../service/auth.service";
import {Request} from "../../model/request.model";
import {User} from "../../model/user.model";

declare let $:any;

@Component({
  selector: 'assigned',
  templateUrl: 'assigned.component.html',
  styleUrls: ['assigned.component.css']
})
export class AssignedComponent {
  requests:Request[] = [];
  pageCount:number;
  currentUserId: number;

  settings = {
    delete: false,
    add: false,
    info: true,
    multiSelect: false,
    filterRow: true,
    assign: false,
    join: false,
    close: true,
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
      this.requestService.getInProgressAssigned(1, this.currentUserId).subscribe((requests:Request[]) => {
        this.requests = requests;
      });
      this.requestService.getInProgressAssignedPageCount(this.currentUserId).subscribe((count) => this.pageCount = count);
    });
  }

  pageChange(data){
    this.requestService.getInProgressAssigned(data.page, this.currentUserId).subscribe(requests => {
      this.requests = requests;
    })
  }

}
