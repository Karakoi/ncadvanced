import {Component, OnInit, ViewChild} from "@angular/core";
import {Request} from "../../model/request.model";
import {RequestService} from "../../service/request.service";
import {RequestDetailsComponent} from "../../shared/request/request-details/request-details.component";
import {RequestFormComponent} from "../../shared/request/request-form/request-form.component";

declare let $: any;

@Component({
  selector: 'request-table',
  templateUrl: 'request-table.component.html',
  styleUrls: ['request-table.component.css']
})
export class RequestTableComponent implements OnInit {
  requests: Request[];
  pageCount: number;

  @ViewChild(RequestDetailsComponent)
  requestDetails: RequestDetailsComponent;

  @ViewChild(RequestFormComponent)
  requestForm: RequestFormComponent;

  constructor(private requestService: RequestService) {
  }

  ngOnInit() {
    this.requestService.getAll(1).subscribe((requests: Request[]) => {
      this.requests = requests;
    });
    this.requestService.getPageCount().subscribe((count) => this.pageCount = count);
  }

  get sorted(): Request[] {
    return this.requests
      .map(request => request)
      .sort((a, b) => {
        if (a.dateOfCreation > b.dateOfCreation) return 1;
        else if (a.dateOfCreation < b.dateOfCreation) return -1;
        else return 0;
      });
  }

  createRange(number) {
    let items: number[] = [];
    for (let i = 2; i <= number; i++) {
      items.push(i);
    }
    return items;
  }

  load(data) {
    $('.paginate_button').removeClass('active');
    let page = data.target.text;
    $(data.target.parentElement).addClass('active');
    this.requestService.getAll(page).subscribe((requests: Request[]) => {
      this.requests = requests;
    });
  }

  updateRequests(users: Request[]) {
    this.requests = users;
  }

  openDetailsModal(request: Request): void {
    this.requestDetails.modal.open();
    this.requestDetails.request = request;
  }

  openFormModal(): void {
    this.requestForm.modal.open();
  }
}
