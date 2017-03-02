import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ForumComponent} from "./forum.component";

const routes: Routes = [
  {path: '', component: ForumComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    ForumComponent
  ],
  exports: [
    ForumComponent
  ]
})
export class ForumModule {
}
