import {Component, ViewChild} from "@angular/core";
import {CloseRequestComponent} from "../../pages/assigned/request-close/close-request.component";
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

  @ViewChild(CloseRequestComponent)
  closeRequestComponent:CloseRequestComponent;

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

  close(request:Request) {
    this.closeRequestComponent.request = request;
    this.closeRequestComponent.modal.open();
  }


  updateRequests(requests: Request[]) {
    this.requests = requests;
  }

  createRange(number) {
    let items:number[] = [];
    for (let i = 2; i <= number; i++) {
      items.push(i);
    }
    return items;
  }

  load(data) {
    $('.paginate_button').removeClass('active');
    let page = data.target.text;
    $(data.target.parentElement).addClass('active');
    this.requestService.getInProgressAssigned(page, this.currentUserId).subscribe((requests:Request[]) => {
      this.requests = requests;
    });
  }

}
