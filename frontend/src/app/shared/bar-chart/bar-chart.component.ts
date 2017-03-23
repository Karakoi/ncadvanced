import {Component, OnInit, Injectable, Input, ViewChild} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {RequestService} from "../../service/request.service";
import {ReportService} from "../../service/report.service";
import {RequestDTO} from "../../model/dto/requestDTO.model";
import {AuthService} from "../../service/auth.service";
import {User} from "../../model/user.model";

@Component({
  selector: 'bar-chart',
  templateUrl: 'bar-chart.component.html',
  inputs: ['list']
})
@Injectable()
export class BarChartComponent implements OnInit {

  private user: User;

  ngOnInit(): void {
    this.authService.currentUser.subscribe((user: User) => {
      this.user = user;
    });
  }

  constructor(private requestService: RequestService,
              private reportService: ReportService,
              private authService: AuthService,
              private toastr: ToastsManager) {
  }

  public barChartOptions: any = {
    scaleShowVerticalLines: false,
    responsive: true
  };
  public barChartData: any[] = [{data: [], label: ''}];
  // public barChartData2: any[] = [{data: [], label: ''}, {data: [], label: ''}];
  public barChartLabels: Array<string> = [];
  public barChartType: string = 'bar';
  public barChartLegend: boolean = true;
  public list: Array<any> = [];
  public selector: boolean;

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
    // this.barChartData2 = [{data: [], label: ''}, {data: [], label: ''}];
  }

  public buildAdminChart() {
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

  public buildManagerChart() {
    console.log(this.user.id)
    let closedRequests: Array<any> = [];
    this.clear();
    this.reportService.getManagerStatisticsOfClosedRequestsByPeriod(this.startDate, this.endDate, this.user.id)
      .subscribe((array: RequestDTO[]) => {
        console.log(array);
        array.forEach(requestDTO => {
          closedRequests.push(requestDTO.count);
          let firstDate = requestDTO.startDateLimit[0] + "-" + requestDTO.startDateLimit[1] + "-" + requestDTO.startDateLimit[2]
          let secondDate = requestDTO.endDateLimit[0] + "-" + requestDTO.endDateLimit[1] + "-" + requestDTO.endDateLimit[2]
          this.barChartLabels.push(firstDate.concat(" : " + secondDate));
        });

            this.barChartData = [{data: closedRequests, label: 'Count your closed requests'}];
      });
  }

}


