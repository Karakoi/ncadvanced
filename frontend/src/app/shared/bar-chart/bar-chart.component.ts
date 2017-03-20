import {Component, OnInit, Injectable, Input, ViewChild} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {RequestService} from "../../service/request.service";
import {ReportService} from "../../service/report.service";
import {RequestDTO} from "../../model/dto/requestDTO.model";

@Component({
  selector: 'bar-chart',
  templateUrl: 'bar-chart.component.html',
  inputs: ['list']
})
@Injectable()
export class BarChartComponent implements OnInit {

  ngOnInit(): void {
  }

  constructor(private requestService: RequestService,
              private reportService: ReportService,
              private toastr: ToastsManager) {
  }

  public barChartOptions: any = {
    scaleShowVerticalLines: true,
    responsive: true
  };
  public barChartData: any[] = [{data: [], label: ''}];
  public barChartLabels: Array<string> = [];
  public barChartType: string = 'bar';
  public barChartLegend: boolean = true;
  public list: Array<any> = [];

  @ViewChild(Date)
  @Input('startDate') startDate: Date;
  @ViewChild(Date)
  @Input('endDate') endDate: Date;

  // events
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }

  // public countRequests: number;
  // public barChartLabels2: string[] = [];
  // public barChartData2: any[] = [{data: [5], label: 'Series 5'}];
  // generate() {
  //   this.requestService.getCountRequestsByPeriod(this.startDate, this.endDate)
  //     .subscribe((count: number) => {
  //       this.countRequests = count;
  //     });
  //   console.log(this.countRequests);
  //   this.barChartData2 = [{data: [this.countRequests], label: 'Count Requests'}];
  //   this.barChartLabels2.push("1 month");
  // }
  clear() {
    this.barChartLabels.length = 0;
    this.barChartData = [{data: [], label: ''}];
  }

  public build() {
    let count: Array<any> = [];
    this.clear();
    this.reportService.getListCountRequestsByPeriod(this.startDate, this.endDate)
      .subscribe((array: RequestDTO[]) => {
        console.log(array);
        array.forEach(requestDTO => {
          count.push(requestDTO.count);
          let firstDate = requestDTO.startDateLimit[0] + "-" + requestDTO.startDateLimit[1] + "-" + requestDTO.startDateLimit[2]
          let secondDate = requestDTO.endDateLimit[0] + "-" + requestDTO.endDateLimit[1] + "-" + requestDTO.endDateLimit[2]
          this.barChartLabels.push(firstDate.concat(" : " + secondDate));
        });
        this.barChartData = [{data: count, label: 'Count created requests'}];
      });
  }
}


