import {Component, OnInit,} from '@angular/core';
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";
import {Observable} from "rxjs";
import {ToastsManager} from "ng2-toastr";

@Component({

  selector: 'admin-home',
  templateUrl: 'admin.component.html',
})
export class AdminComponent implements OnInit {
  request: Request[];
  progress: Array<number>;
  testTable: Array<any> = [];

  constructor(private requestService: RequestService, private toast: ToastsManager) {
      this.requestService.getQuantityRequest().subscribe((s => {
        this.progress = s;
      }));
  }

  setStatistic(){
   this.pieChartRequest = {
      chartType: 'PieChart',
      dataTable: [
        ['Request', 'Info'],
        ['In progress', this.progress[3]],
        ['Free', this.progress[1]],
        ['Registered', this.progress[0]],
        ['Reopen', this.progress[4]],
        ['Joined', this.progress[2]],
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

  load(): void {
    this.requestService.getAll(2).subscribe((rec: Request[])=>{
      this.request = rec;
      this.getRequest();
    })
  }

  ngOnInit(): void {
    this.requestService.getAll(1).subscribe((requests: Request[]) => {
      this.request = requests;
      this.getRequest();
    });
  }

  getRequest(): void {
    this.request.forEach(s => {
      this.testTable.push({
        name: s.title,
        data: s.dateOfCreation,
        priority_status: s.progressStatus.name,
        progress_status: s.progressStatus.name,
        reporter: s.reporter.firstName,
        assignee: s.assignee.firstName
      });
    });
  }

  settings = {
    columns: {
      name: {
        title: 'Title'
      },
      data: {
        title: 'Data of creation'
      },
      priority_status: {
        title: 'Priority status'
      },
      progress_status: {
        title: 'Progress status'
      },
      reporter: {
        title: 'Reporter'
      },
      assignee: {
        title: 'Assignee'
      },
    },
    actions: {
      add: false,
      delete: false,
      edit:false
    },
  };
}
