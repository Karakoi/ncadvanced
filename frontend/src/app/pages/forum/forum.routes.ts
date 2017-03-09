import {Routes} from "@angular/router";
import {ForumComponent} from "./forum.component";
import {TopicComponent} from "./topic/topic.component";
import {MessageComponent} from "./message/message.component";
import {ChatComponent} from "./chat/chat.component";
import {IncomingMessageListComponent} from "./incoming-messages/incoming-message-list.component";

export const forumRoutes: Routes = [
  {
    path: '',
    component: ForumComponent
  },
  {
    path: 'topic',
    component: TopicComponent
  },
  {
    path: 'message',
    component: MessageComponent
  },
  {
    path: 'chat',
    component: ChatComponent
  },
  {
    path: 'inbox',
    component: IncomingMessageListComponent
  }
];
