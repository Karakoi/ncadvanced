import {Routes} from "@angular/router";
import {WelcomeComponent, NoContentComponent, HomeComponent, RequestComponent} from "./components/index";
import {ForumComponent} from "./pages/forum/forum.component";
import {TopicComponent} from "./pages/topic/topic.component";
import {MessageComponent} from "./pages/message/message.component";

export const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'overseer',
    pathMatch: 'full'
  },
  {
    path: 'overseer',
    component: WelcomeComponent
  },
  // Available for unregistered user
  {
    path: 'signup',
    loadChildren: './pages/signup/signup.module#SignupModule'
  },
  {
    path: 'login',
    loadChildren: './pages/login/login.module#LoginModule'
  },
  {
    path: 'recover',
    loadChildren: './pages/recover/recover.module#RecoverModule'
  },
  // Available for registered user
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'forum',
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
    path: 'profile',
    loadChildren: './pages/profile/profile.module#ProfileModule'
  },
  {
    path: 'request',
    component: RequestComponent
  },
  // If route does not match any previous ones
  {
    path: '**',
    component: NoContentComponent
  }
];
