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
import {GravatarModule} from "../../shared/gravatar/gravatar.module";
import {DateParseModule} from "../../util/date-parser/date-parse.module";
import {ChatService} from "../../service/chat.service";
import {TimeParseModule} from "../../util/time-parser/time-parse.module";
import {DeleteMessageComponent} from "./topic/message-delete/delete-mesage.component";
import {DeleteTopicComponent} from "./topic-delete/delete-topic.component";
import {TopicService} from "../../service/topic.service";
import {InlineEditorModule} from "ng2-inline-editor";
import {PaginationModule} from "ng2-bootstrap";

@NgModule({
  imports: [
    InlineEditorModule,
    DateParseModule,
    TimeParseModule,
    GravatarModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    FormTemplateModule,
    Ng2Bs3ModalModule,
    RouterModule.forChild(forumRoutes),
    PaginationModule.forRoot()
  ],
  declarations: [
    ForumComponent,
    IncomingMessageListComponent,
    MessageItemComponent,
    ChatComponent,
    MessageComponent,
    TopicComponent,
    DeleteMessageComponent,
    DeleteTopicComponent
  ],
  providers: [
    UserService,
    AuthService,
    ChatService,
    TopicService
  ]
})
export class ForumModule {
}
