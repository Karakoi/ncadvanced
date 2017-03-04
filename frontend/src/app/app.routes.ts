import {Routes} from "@angular/router";
import {WelcomeComponent, NoContentComponent} from "./components/barrel";
import {PublicPageGuard} from "./service/public-page.guard";
import {PrivatePageGuard} from "./service/private-page.guard";
import {AdminPageGuard} from "./service/admin-page.guard";

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
    loadChildren: './pages/signup/signup.module#SignupModule',
    canActivate: [PublicPageGuard]
  },
  {
    path: 'login',
    loadChildren: './pages/login/login.module#LoginModule',
    canActivate: [PublicPageGuard]
  },
  {
    path: 'recover',
    loadChildren: './pages/recover/recover.module#RecoverModule',
    canActivate: [PublicPageGuard]
  },
  // Available for registered user
  {
    path: 'home',
    loadChildren: './pages/home/home.module#HomeModule',
    canActivate: [PrivatePageGuard]
  },
  {
    path: 'profile',
    loadChildren: './pages/profile/profile.module#ProfileModule',
    canActivate: [PrivatePageGuard]
  },
  {
    path: 'forum',
    loadChildren: './pages/forum/forum.module#ForumModule',
    canActivate: [PrivatePageGuard]
  },
  {
    path: 'topic',
    loadChildren: './pages/topic/topic.module#TopicModule',
    canActivate: [PrivatePageGuard]
  },
  {
    path: 'message',
    loadChildren: './pages/message/message.module#MessageModule',
    canActivate: [PrivatePageGuard]
  },
  {
    path: 'chat',
    loadChildren: './pages/chat/chat.module#ChatModule',
    canActivate: [PrivatePageGuard]
  },
  // Available for admin
  {
    path: 'users',
    loadChildren: './pages/user-table/user-table.module#UserTableModule',
    canActivate: [PrivatePageGuard, AdminPageGuard]
  },
  {
    path: 'requests',
    loadChildren: './pages/request-table/request-table.module#RequestTableModule',
    canActivate: [PrivatePageGuard, AdminPageGuard]
  },
  // If route does not match any previous ones
  {
    path: '**',
    component: NoContentComponent
  }
];
