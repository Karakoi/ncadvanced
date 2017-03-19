import {Component, OnInit} from '@angular/core';
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";
import {ToastsManager} from "ng2-toastr";
import 'rxjs/Rx';

declare let $: any;

@Component({

  selector: 'admin-home',
  templateUrl: 'admin.component.html',
})
export class AdminComponent implements OnInit {
  requests: Request[];
  progress: Array<number>;
  pageCount: number;
  orderType: boolean;
  orderField: string;
  searchTypes: any;

  constructor(private requestService: RequestService, private toast: ToastsManager) {
      this.requestService.getQuantityRequest().subscribe((s => {
        this.progress = s;
      }));

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

  setStatistic(){
   this.pieChartRequest = {
      chartType: 'PieChart',
      dataTable: [
        ['Request', 'Info'],
        ['In progress: '+ this.progress[3], this.progress[3]],
        ['Free: ' + this.progress[1],this.progress[1]],
        ['Registered: ' + this.progress[0], this.progress[0]],
        ['Reopen: ' + this.progress[4], this.progress[4]],
        ['Joined: ' + this.progress[2],this.progress[2]],
      ],
      options: {
        title: 'Request statistic',
        wight: 900,
        height: 700
      }
    };
  }

  pieChartRequest = {
    chartType: 'PieChart',
    dataTable: [
      ['Request', 'Info'],
      ['Click to see statistic',100],
    ],
    options: {
      title: 'Request statistic',
      wight: 900,
      height: 700
    }
  };

  ngOnInit(): void {
    this.requestService.getAll(1).subscribe((requests: Request[]) => {
      this.requests = requests;
    });
    this.requestService.getPageCount().subscribe((count) => this.pageCount = count);
  }

  changeOrderParams(type, field) {
    this.orderType = type;
    this.orderField = field;
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
}
