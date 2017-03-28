import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {DeadlineComponent} from "../deadline/deadline.component";
import {CalendarComponent} from "ap-angular2-fullcalendar/src/calendar/calendar";

const routes: Routes = [
  {path: '', component: DeadlineComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    DeadlineComponent,
    CalendarComponent
  ],
  exports: [
    DeadlineComponent,
    CalendarComponent
  ]
})
export class DeadlineModule {
}
