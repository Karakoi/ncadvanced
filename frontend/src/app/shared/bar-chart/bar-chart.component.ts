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

  private currentUser: User;

  ngOnInit(): void {
    this.authService.currentUser.subscribe((user: User) => {
      this.currentUser = user;
      this.buildChartsByRole(this.startDate, this.endDate, this.countTopManagers);
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
  public barChartLabels: Array<string> = [];
  public barChartType: string = 'bar';
  public barChartLegend: boolean = true;
  public list: Array<any> = [];
  public selector: boolean;

  @ViewChild(Date)
  @Input('startDate') startDate: Date;
  @ViewChild(Date)
  @Input('endDate') endDate: Date;
  @Input('countTopManagers') countTopManagers: number;

  // events
  public chartClicked(e: any): void {
    //console.log(e);
  }

  public chartHovered(e: any): void {
    //console.log(e);
  }

  clear() {
    this.barChartLabels.length = 0;
    this.barChartData = [{data: [], label: ''}];
  }

  public buildAdminChart(start: any, end: any, countTopManagers: number) {
    let count: Array<any> = [];
    this.clear();
    this.reportService.getListOfBestManagersWithClosedStatusByPeriod(start, end, countTopManagers)
      .subscribe((array: RequestDTO[]) => {
        //console.log(array);
        array.forEach(manager => {
          count.push(manager.count);
          let name = manager.managerFirstName + " " + manager.managerLastName;
          this.barChartLabels.push(name);
        });
        this.barChartData = [{data: count, label: 'Count of closed requests'}];
      });
  }

  public buildManagerChart(start: any, end: any) {
    let closedRequests: Array<any> = [];
    this.clear();
    this.reportService.getManagerStatisticsOfClosedRequestsByPeriod(start, end, this.currentUser.id)
      .subscribe((array: RequestDTO[]) => {
        //console.log(array);
        array.forEach(requestDTO => {
          closedRequests.push(requestDTO.count);
          let firstDate = requestDTO.startDateLimit[0] + "-" + requestDTO.startDateLimit[1] + "-" + requestDTO.startDateLimit[2]
          let secondDate = requestDTO.endDateLimit[0] + "-" + requestDTO.endDateLimit[1] + "-" + requestDTO.endDateLimit[2]
          this.barChartLabels.push(firstDate.concat(" : " + secondDate));
        });

        this.barChartData = [{data: closedRequests, label: 'Count your closed requests'}];
      });
  }

  isAdmin(): boolean {
    return this.currentUser.role.name === 'admin';
  }

  isManager(): boolean {
    return this.currentUser.role.name === 'office manager'
  }

  private buildChartsByRole(start: any, end: any, countTopManagers: number) {
    if (this.isAdmin()) {
      this.buildAdminChart(start, end, countTopManagers);
    } else {
      this.buildManagerChart(start, end);
    }
  }

  public barChartColors:Array<any> = [
    { // green
      backgroundColor: 'rgba(81, 147, 82, 0.7)',
      borderColor: 'rgba(47,82,40,1)'
    }
  ];

}


