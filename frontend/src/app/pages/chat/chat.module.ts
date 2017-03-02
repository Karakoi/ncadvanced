import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ChatComponent} from "./chat.component";

const routes: Routes = [
  {path: '', component: ChatComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    ChatComponent
  ],
  exports: [
    ChatComponent
  ]
})
export class ChatModule {
}
