import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {HttpModule, Http, RequestOptions} from "@angular/http";
import {RouterModule} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ToastModule} from "ng2-toastr";
import {appRoutes} from "./app.routes";
import {AppComponent} from "./app.component";
import {AlertModule} from 'ng2-bootstrap';
import {AuthHttp, AuthConfig} from "angular2-jwt";
import {CacheService} from "ionic-cache/ionic-cache";
import {
  PrivatePageGuard,
  PublicPageGuard,
  AdminPageGuard,
  RequestService,
  RecoverService,
  EmployeeService,
  AuthService,
  UserService,
  UserPageGuard,
  ManagerPageGuard
} from "./service/barrel";
import {FooterComponent, NavbarComponent, NoContentComponent, SideBarComponent} from "./components/barrel";
import {GravatarModule} from "./shared/gravatar/gravatar.module";
import {SideBarDirective} from "./directive/sidebar.directive";
import {RoleService} from "./service/role.service";

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    NavbarComponent,
    NoContentComponent,
    SideBarComponent,
    SideBarDirective
  ],
  imports: [
    AlertModule.forRoot(),
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    GravatarModule,
    ToastModule.forRoot(),
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    UserService,
    RoleService,
    AuthService,
    CacheService,
    EmployeeService,
    PrivatePageGuard,
    PublicPageGuard,
    AdminPageGuard,
    RequestService,
    RecoverService,
    ManagerPageGuard,
    UserPageGuard,
    {
      provide: AuthHttp,
      useFactory: authHttpServiceFactory,
      deps: [Http, RequestOptions]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

export function authHttpServiceFactory(http: Http, options: RequestOptions) {
  return new AuthHttp(new AuthConfig(), http, options);
}
