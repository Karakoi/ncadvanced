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
}
