import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {TopicComponent} from "./topic.component";

const routes: Routes = [
  {path: '', component: TopicComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    TopicComponent
  ],
  exports: [
    TopicComponent
  ]
})
export class TopicModule {
}
