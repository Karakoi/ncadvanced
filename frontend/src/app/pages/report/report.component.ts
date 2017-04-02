import {Component, OnInit, ViewChild} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {AuthService, AuthEvent} from "../../service/auth.service";
import {CustomValidators} from "ng2-validation";
import {BarChartComponent} from "../../shared/bar-chart/bar-chart.component";
import {LineChartComponent} from "../../shared/line-chart/line-chart.component";
import {ReportService} from "../../service/report.service";
import {User} from "../../model/user.model";
import {Subject} from "rxjs";
import * as FileSaver from "file-saver";
import {Md5} from "ts-md5/dist/md5";

@Component({
  selector: 'report',
  templateUrl: 'report.component.html',
  styleUrls: ['report.component.css']
})

export class ReportComponent implements OnInit {

  private role: string;
  private currentUser: User;
  private reportForm: FormGroup;
  private encryptedEmail: any;

  @ViewChild(BarChartComponent)
  public barChart: BarChartComponent;

  @ViewChild(LineChartComponent)
  public lineChart: LineChartComponent;
  private startDate: Date;
  private endDate: Date;
  private isGenerated: boolean = false;
  private countManagers: number[] = [1, 3, 5, 10];
  private countTopManagers: number;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private reportService: ReportService,
              private authService: AuthService,
              private toastr: ToastsManager) {
  }

  ngOnInit(): void {
    this.authService.currentUser.subscribe((user: User) => {
      this.currentUser = user;
    });
    this.role = this.authService.role;
    this.authService.events.subscribe((event: Subject<AuthEvent>) => {
      if (event.constructor.name === 'DidLogin') {
        this.role = this.authService.role;
      }
    });
    this.initForm();
  }

  validateField(field: string): boolean {
    return this.reportForm.get(field).valid || !this.reportForm.get(field).dirty;
  }

  private initForm(): void {
    this.reportForm = this.formBuilder.group({
      dateOfStart: ['', CustomValidators.dateISO],
      dateOfEnd: ['', CustomValidators.dateISO],
      countManagersSelector: [''],
    });
  }

  private saveDates(formData) {
    if (formData.dateOfEnd > formData.dateOfStart) {
      this.countTopManagers = formData.countManagersSelector;
      this.startDate = formData.dateOfStart;
      this.endDate = formData.dateOfEnd;

      this.isDatesDifferenceLessStep(this.startDate, this.endDate);

      console.log(this.startDate);
      console.log(this.endDate);
      if (!this.isGenerated) {
        this.isGenerated = true;
      } else {
        this.generateReportByRole(this.startDate, this.endDate);
      }
      this.toastr.success("START Date: ".concat(this.startDate.toString() + ", END Date:" + this.endDate.toString()), "DATA:");
    }
    else {
      this.toastr.error("Error. Incorrect dates: End date must be bigger than the start date");
    }
  }

  private isDatesDifferenceLessStep(start: Date, end: Date): boolean {
    let startDate = new Date(start);
    let endDate = new Date(end);
    let countYears = endDate.getFullYear() - startDate.getFullYear();
    let countMonths = (endDate.getMonth() + 1) - (startDate.getMonth() + 1);
    if (countMonths == 0 && countYears == 0) {
      this.toastr.warning("Warning. The minimum date difference is one month.");
      this.toastr.warning("The end date will be rounded to the minimum step boundary");
      return true;
    } else {
      return false;
    }
  }

  private generateReportByRole(start: any, end: any) {
    if (this.isAdmin()) {
      this.barChart.buildAdminChart(start, end, this.countTopManagers);
      this.lineChart.buildAdminChart(start, end);
    } else {
      this.barChart.buildManagerChart(start, end);
    }
  }

  private generateAdminPDF() {
    this.encryptedEmail = Md5.hashStr(this.currentUser.email).toString();
    this.reportService.getAdminPDFReport(this.startDate, this.endDate, this.countTopManagers, this.encryptedEmail).subscribe(
      (res: any) => {
        let blob = res.blob();
        let filename = 'admin_report_from_' + this.startDate + '_to_' + this.endDate + '.pdf';
        FileSaver.saveAs(blob, filename);
      }
    );
  }

  private generateManagerPDF() {
    this.encryptedEmail = Md5.hashStr(this.currentUser.email).toString();
    this.reportService.getManagerPDFReport(this.startDate, this.endDate, this.currentUser.id, this.encryptedEmail).subscribe(
      (res: any) => {
        let blob = res.blob();
        let filename = 'manager_report_from_' + this.startDate + '_to_' + this.endDate + '.pdf';
        FileSaver.saveAs(blob, filename);
      }
    );
  }

  isAdmin(): boolean {
    return this.role === 'admin';
  }

  isManager(): boolean {
    return this.role === 'office manager'
  }

}
