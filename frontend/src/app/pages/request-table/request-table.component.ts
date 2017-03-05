import {Component, OnInit} from "@angular/core";
import {Request} from "../../model/request.model";
import {RequestService} from "../../service/request.service";


@Component({
  selector: 'request-table',
  templateUrl: 'request-table.component.html',
  styleUrls: ['request-table.component.css']
})
export class RequestTableComponent implements OnInit {
  requests: Request[];

  constructor(private requestService: RequestService) {
  }

  ngOnInit() {
    this.requestService.getAll().subscribe((requests: Request[]) => {
      this.requests = requests;
    });
  }

  get sorted(): Request[] {
    return this.requests
      .map(request => request)
      .sort((a, b) => {
        if (a.id > b.id) return 1;
        else if (a.id < b.id) return -1;
        else return 0;
      });
  }
}
