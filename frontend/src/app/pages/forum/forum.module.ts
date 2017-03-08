import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {ForumComponent, ChatComponent, MessageComponent, TopicComponent} from "./barrel";
import {forumRoutes} from "./forum.routes";
import {AuthService, UserService} from "../../service/barrel";
import {FormTemplateModule} from "../../shared/form-template/form-template.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    FormTemplateModule,
    RouterModule.forChild(forumRoutes)
  ],
  declarations: [
    ForumComponent,
    ChatComponent,
    MessageComponent,
    TopicComponent
  ],
  providers: [
    UserService,
    AuthService
  ]
})
export class ForumModule {
}