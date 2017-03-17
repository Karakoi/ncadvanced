import {Component, OnInit,} from '@angular/core';
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";

@Component({

  selector: 'admin-home',
  templateUrl: 'admin.component.html',
})
export class AdminComponent implements OnInit {

  request: Request[];
  progress: number[];
  testTable: Array<any> = [];

  constructor(private requestService: RequestService) {
    //   this.requestService.getStatisticRequest().subscribe((s => {
    //     this.progress = s;
    //   }));
  }

  ngOnInit(): void {
    this.requestService.getAll(1).subscribe((requests: Request[]) => {
      this.request = requests;
      this.getRequest();
    });
  }

  pieChartRequest = {
    chartType: 'PieChart',
    dataTable: [
      ['Request', 'Info'],
      ['In progress', 16],
      ['Free', 22],
      ['Registered', 37],
      ['Reopen', 28],
      ['Joined', 10]
    ],
    options: {
      title: 'Request statistic',
      wight: 900,
      height: 700
    }
  };

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
    }
  };
}
