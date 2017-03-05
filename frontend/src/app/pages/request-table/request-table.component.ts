import {Component, OnInit} from "@angular/core";
import {Request} from "../../model/request.model";
import {RequestService} from "../../service/request.service";
declare var $:any;

@Component({
  selector: 'request-table',
  templateUrl: 'request-table.component.html',
  styleUrls: ['request-table.component.css']
})
export class RequestTableComponent implements OnInit {
  requests: Request[];
  pageCount: number;

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
        if (a.id > b.id) return 1;
        else if (a.id < b.id) return -1;
        else return 0;
      });
  }

  createRange(number){
    var items: number[] = [];
    for(var i = 2; i <= number; i++){
      items.push(i);
    }
    return items;
  }

  load(data){
    console.log(this.pageCount);
    $('.paginate_button').removeClass('active')
    let page = data.target.text;
    console.log($(data.target.parentElement).addClass('active'))
    this.requestService.getAll(page).subscribe((requests: Request[]) => {
      this.requests = requests;
    });
  }
}
