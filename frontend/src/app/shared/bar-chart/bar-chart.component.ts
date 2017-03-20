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
    scaleShowVerticalLines: false,
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

  clear() {
    this.barChartLabels.length = 0;
    this.barChartData = [{data: [], label: ''}];
  }

  public build() {
    let count: Array<any> = [];
    this.clear();
    this.reportService.getListOfBestManagersWithClosedStatusByPeriod(this.startDate, this.endDate)
      .subscribe((array: RequestDTO[]) => {
        console.log(array);
        array.forEach(manager => {
          count.push(manager.count);
          let name = manager.managerFirstName + " " + manager.managerLastName;
          this.barChartLabels.push(name);
        });
        this.barChartData = [{data: count, label: 'Count of closed requests'}];
      });
  }

  // public build2() {
  //   let count: Array<any> = [];
  //   this.clear();
  //   this.reportService.getListOfBestManagersWithFreeStatusByPeriod(this.startDate, this.endDate)
  //     .subscribe((array: RequestDTO[]) => {
  //       console.log(array);
  //       array.forEach(manager => {
  //         count.push(manager.count);
  //         let name = manager.managerFirstName + " " + manager.managerLastName;
  //         this.barChartLabels.push(name);
  //       });
  //       this.barChartData = [{data: count, label: 'Count of free requests'}];
  //     });
  // }
}


