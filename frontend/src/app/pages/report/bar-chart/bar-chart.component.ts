import {Component, OnInit, Injectable} from "@angular/core";
import {RequestService} from "../../../service/request.service";
import {ToastsManager} from "ng2-toastr";
import {FormBuilder} from "@angular/forms";

@Component({
  selector: 'bar-chart',
  templateUrl: 'bar-chart.component.html',
  inputs: ['list']
})
@Injectable()
export class BarChartComponent implements OnInit {

  ngOnInit(): void {
    this.buildBarChart();
    // this.rForm = this.formBuilder.group({
    //   test: ''
    // });
  }

  constructor(private formBuilder: FormBuilder,
              private requestService: RequestService,
              private toastr: ToastsManager) {
  }

  public barChartOptions: any = {
    scaleShowVerticalLines: false,
    responsive: true
  };
  public barChartData: any[] = [{data: [], label: ''}];
  public barChartLabels: string[] = [];
  public barChartType: string = 'bar';
  public barChartLegend: boolean = true;
  public startDate1: string = '2017-01-05';
  public endDate: string = '2017-02-05';
  public list: Array<any> = [];

  // public rForm: FormGroup;
  // @Input('startDate') startDate: Data;
  // public test: string;

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
    if (this.barChartData) {
      this.barChartData = null;
    }
  }

  public buildBarChart() {
    // this.test = forma.test;
    // console.log(this.test);
    // this.toastr.success(this.test.toString(), "TEST");

    this.requestService.getCountRequestsByStartDate(this.startDate1, 5)
      .subscribe((array: number[]) => {
        this.list = array;
      });
    console.log(this.list);
    this.barChartData = [{data: this.list, label: 'Count created requests'}];
    let i: number = 1;
    this.list.forEach(s => {
      this.barChartLabels.push(i + " months");
      i++;
    });
  }

}
