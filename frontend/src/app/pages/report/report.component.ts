import {Component, OnInit, ViewChild} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {AuthService} from "../../service/auth.service";
import {CustomValidators} from "ng2-validation";
import {BarChartComponent} from "../../shared/bar-chart/bar-chart.component";

@Component({
  selector: 'report',
  templateUrl: 'report.component.html',
  styleUrls: ['report.component.css']
})

export class ReportComponent implements OnInit {

  private reportForm: FormGroup;

  @ViewChild(BarChartComponent)
  public barChart: BarChartComponent;
  private startdate: any;
  private enddate: any;
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

  validateField(field: string): boolean {
    return this.reportForm.get(field).valid || !this.reportForm.get(field).dirty;
  }

  private initForm(): void {
    this.reportForm = this.formBuilder.group({
      dateOfStart: ['', CustomValidators.dateISO],
      dateOfEnd: ['', CustomValidators.dateISO],
    });
  }

  private saveDates(formData) {
    if (formData.dateOfEnd > formData.dateOfStart) {
      this.startdate = formData.dateOfStart;
      this.enddate = formData.dateOfEnd;
      this.toastr.success("Data START: ".concat(this.startdate.toString() + ", Data END:" + this.enddate.toString()), "DATA:");
    }
    else {
      this.toastr.error("Error. Uncorrect dates: End date must be bigger than the start date");
    }
  }

  private generateReport() {
    this.barChart.build();
  }

}
