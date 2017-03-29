import {Component, ViewChild, Input} from "@angular/core";
import {ReportService} from "../../service/report.service";
import {ToastsManager} from "ng2-toastr";
import {RequestDTO} from "../../model/dto/requestDTO.model";
import {User} from "../../model/user.model";

@Component({
  selector: 'line-chart',
  templateUrl: 'line-chart.component.html'
})
export class LineChartComponent {

  constructor(private reportService: ReportService,
              private toastr: ToastsManager) {
  }

  ngOnInit(): void {
      this.buildAdminChart(this.startDate, this.endDate);
  }

  // public lineChartColors:Array<any> = [
  //   { // grey
  //     backgroundColor: 'rgba(148,159,177,0.2)',
  //     borderColor: 'rgba(148,159,177,1)',
  //     pointBackgroundColor: 'rgba(148,159,177,1)',
  //     pointBorderColor: '#fff',
  //     pointHoverBackgroundColor: '#fff',
  //     pointHoverBorderColor: 'rgba(148,159,177,0.8)'
  //   },
  //   { // dark grey
  //     backgroundColor: 'rgba(77,83,96,0.2)',
  //     borderColor: 'rgba(77,83,96,1)',
  //     pointBackgroundColor: 'rgba(77,83,96,1)',
  //     pointBorderColor: '#fff',
  //     pointHoverBackgroundColor: '#fff',
  //     pointHoverBorderColor: 'rgba(77,83,96,1)'
  //   },
  //   { // grey
  //     backgroundColor: 'rgba(148,159,177,0.2)',
  //     borderColor: 'rgba(148,159,177,1)',
  //     pointBackgroundColor: 'rgba(148,159,177,1)',
  //     pointBorderColor: '#fff',
  //     pointHoverBackgroundColor: '#fff',
  //     pointHoverBorderColor: 'rgba(148,159,177,0.8)'
  //   }
  // ];
  public lineChartLegend: boolean = true;
  public lineChartType: string = 'line';
  public lineChartData: any[] = [{data: [], label: ''}, {data: [], label: ''}];
  public lineChartLabels: Array<string> = [];
  public lineChartOptions: any = {
    responsive: true
  };
  public list: Array<any> = [];

  @ViewChild(Date)
  @Input('startDate') startDate: Date;
  @ViewChild(Date)
  @Input('endDate') endDate: Date;


  clear() {
    this.lineChartLabels.length = 0;
    this.lineChartData = [{data: [], label: ''}, {data: [], label: ''}];
  }

  public buildAdminChart(start: any, end: any) {
    let closedRequests: Array<any> = [];
    let freeRequests: Array<any> = [];
    this.clear();
    this.reportService.getAllStatisticsOfClosedRequestsByPeriod(start, end)
      .subscribe((array: RequestDTO[]) => {
        console.log(array);
        array.forEach(requestDTO => {
          closedRequests.push(requestDTO.count);
          let firstDate = requestDTO.startDateLimit[0] + "-" + requestDTO.startDateLimit[1] + "-" + requestDTO.startDateLimit[2]
          let secondDate = requestDTO.endDateLimit[0] + "-" + requestDTO.endDateLimit[1] + "-" + requestDTO.endDateLimit[2]
          this.lineChartLabels.push(firstDate.concat(" : " + secondDate));
        });

        this.reportService.getAllStatisticsOfFreeRequestsByPeriod(start, end)
          .subscribe((array: RequestDTO[]) => {
            console.log(array);
            array.forEach(requestDTO => {
              freeRequests.push(requestDTO.count);
            });

            this.lineChartData = [{data: closedRequests, label: 'Count of closed requests'},
              {data: freeRequests, label: 'Count of free requests'}];
          });
      });
  }

  // events
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }
}
