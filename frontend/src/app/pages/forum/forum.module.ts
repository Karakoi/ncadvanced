import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {ForumComponent, ChatComponent, MessageComponent, TopicComponent} from "./barrel";
import {forumRoutes} from "./forum.routes";
import {AuthService, UserService} from "../../service/barrel";
import {FormTemplateModule} from "../../shared/form-template/form-template.module";
import {IncomingMessageListComponent} from "./incoming-messages/incoming-message-list.component";
import {MessageItemComponent} from "./incoming-messages/incom-message/incoming-message.component";
import {Ng2Bs3ModalModule} from "ng2-bs3-modal/ng2-bs3-modal";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    FormTemplateModule,
    Ng2Bs3ModalModule,
    RouterModule.forChild(forumRoutes)
  ],
  declarations: [
    ForumComponent,
    IncomingMessageListComponent,
    MessageItemComponent,
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