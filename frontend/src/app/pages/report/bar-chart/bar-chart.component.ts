import {Component, OnInit, Injectable, Input, ViewChild} from "@angular/core";
import {RequestService} from "../../../service/request.service";
import {ToastsManager} from "ng2-toastr";

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
  @Input('countMonths') countMonths: any;

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

  public buildBarChart() {
    if (this.startDate) {
      this.clear();
      this.requestService.getCountRequestsByStartDate(this.startDate, this.countMonths)
        .subscribe((array: number[]) => {
          this.barChartData = [{data: array, label: 'Count created requests'}];
          let i: number = 1;
          array.forEach(s => {
            this.barChartLabels.push(i + " months");
            i++;
          });
        });
    }
  }
}
