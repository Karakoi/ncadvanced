import {Component, OnInit, ViewChild} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {AuthService} from "../../service/auth.service";
import {CustomValidators} from "ng2-validation";
import {BarChartComponent} from "./bar-chart/bar-chart.component";

@Component({
  selector: 'report',
  templateUrl: 'report.component.html',
  styleUrls: ['report.component.css']
})

export class ReportComponent implements OnInit {

  private reportForm: FormGroup;

  @ViewChild(BarChartComponent)
  public barChart: BarChartComponent;
  private date: any;
  private countMonths: any;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private authService: AuthService,
              private toastr: ToastsManager) {
  }

  ngOnInit(): void {
    this.initForm();
    // this.barChartComponent.generate();
  }

  // public barChartComponent: BarChartComponent;
  // public reportForm: FormGroup;
  // public startDate: Date;
  // public endDate: Date;

  // private initForm(): void {
  //   this.reportForm = new FormGroup({
  //     startDate: new FormControl()
  //   });
  // }

  public generateReport() {
    // this.barChartComponent.generate();
  }

  validateField(field: string): boolean {
    return this.reportForm.get(field).valid || !this.reportForm.get(field).dirty;
  }

  private initForm(): void {
    this.reportForm = this.formBuilder.group({
      dateOfStart: ['', CustomValidators.dateISO],
      countMonths: ['', [Validators.required, CustomValidators.number]]
    });
  }

  private go(formData) {
    this.date = formData.dateOfStart;
    this.countMonths = formData.countMonths;
    this.toastr.success("Data: ".concat(this.date.toString() + ", counts:" + formData.countMonths), "DATA:");
  }

  private go2() {
    this.barChart.buildBarChart();
  }

  private handleError(error) {
    switch (error.status) {
      case 400:
        this.toastr.error('Error', 'Error');
    }
  }
}