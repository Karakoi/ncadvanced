import {Component, OnInit, ViewChild} from "@angular/core";
import {Request} from "../../model/request.model";
import {RequestService} from "../../service/request.service";
import {RequestFormComponent} from "../../shared/request/request-form/request-form.component";
// import * as FileSaver from "file-saver";
import {DeleteRequestComponent} from "./request-delete/delete-request.component";

declare let $: any;

@Component({
  selector: 'request-table',
  templateUrl: 'request-table.component.html',
  styleUrls: ['request-table.component.css']
})
export class RequestTableComponent implements OnInit {

  requests: Request[];
  pageCount: number;
  term: any;
  orderType: boolean;
  orderField: string;
  searchTypes: any;

  @ViewChild(RequestFormComponent)
  requestForm: RequestFormComponent;

  @ViewChild(DeleteRequestComponent)
  deleteRequestComponent: DeleteRequestComponent;

  constructor(private requestService: RequestService) {
    this.orderType = true;
    this.orderField = 'title';
    this.searchTypes = {
      title: "",
      priorityStatus: "",
      progressStatus: "",
      reporterName: "",
      assigneeName: "",
      date: ""
    };
  }

  changeOrderParams(type, field) {
    this.orderType = type;
    this.orderField = field;
  }

  ngOnInit() {
    this.requestService.getAll(1).subscribe((requests: Request[]) => {
      this.requests = requests;
    });
    this.requestService.getPageCount().subscribe((count) => this.pageCount = count);
  }

  openDeleteRequestModal(request: Request): void {
    this.deleteRequestComponent.request = request;
    this.deleteRequestComponent.modal.open();
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
      requests.forEach(e => {
        if (e.priorityStatus.name == null) e.priorityStatus.name = "";
        if (e.progressStatus.name == null) e.progressStatus.name = "";
        if (e.assignee.firstName == null) e.assignee.firstName = "";
        if (e.assignee.lastName == null) e.assignee.lastName = "";
      });
      this.requests = requests;
    });
  }

  updateRequests(request: Request[]) {
    this.requests = request;
  }

  openFormModal(): void {
    this.requestForm.modal.open();
  }

  // getPDFReport() {
  //   this.reportService.getPDFReport().subscribe(
  //     data => {
  //       console.log(data);
  //       let blob = new Blob([data], {type: 'application/pdf'});
  //       console.log(blob);
  //       FileSaver.saveAs(blob, "report.pdf");
  //       this.toastr.success("Report was created successfully", "Success!");
  //     }, e => this.handleError(e));
  // }

  // private handleError(error) {
  //   switch (error.status) {
  //     case 500:
  //       this.toastr.error("Can't create report", 'Error');
  //   }
  // }
}
