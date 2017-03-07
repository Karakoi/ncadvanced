import {Routes} from "@angular/router";
import {
  NoContentComponent,
  LoginComponent,
  SignupComponent,
  RecoverComponent,
  ChatComponent,
  MessageComponent,
  ForumComponent,
  TopicComponent
} from "./components/barrel";
import {PublicPageGuard} from "./service/public-page.guard";
import {PrivatePageGuard} from "./service/private-page.guard";
import {AdminPageGuard} from "./service/admin-page.guard";

export const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  // Available for unregistered user
  {
    path: 'signup',
    component: SignupComponent,
    canActivate: [PublicPageGuard]
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [PublicPageGuard]
  },
  {
    path: 'recover',
    component: RecoverComponent,
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
    component: ForumComponent,
    canActivate: [PrivatePageGuard]
  },
  {
    path: 'topic',
    component: TopicComponent,
    canActivate: [PrivatePageGuard]
  },
  {
    path: 'message',
    component: MessageComponent,
    canActivate: [PrivatePageGuard]
  },
  {
    path: 'chat',
    component: ChatComponent,
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
