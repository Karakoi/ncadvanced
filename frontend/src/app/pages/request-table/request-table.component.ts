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
  loaded: boolean = false;
  requests: Request[];
  pageCount: number;


  constructor(private requestService: RequestService) {
  }

  ngOnInit() {
    this.requestService.getAll(1).subscribe((requests: Request[]) => {
      this.requestService.getPageCount().subscribe((count) => {
        this.pageCount = count;
        console.log(this.pageCount);
      });
      this.requests = requests;
      this.loaded = true
    });
  }

  pageChange(data){
    this.requestService.getAll(data.page).subscribe(requests => {
      this.requests = requests;
    })
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
