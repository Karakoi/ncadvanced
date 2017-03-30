import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {HttpModule, Http, RequestOptions} from "@angular/http";
import {RouterModule} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ToastModule} from "ng2-toastr";
import {appRoutes} from "./app.routes";
import {AppComponent} from "./app.component";
import {AuthHttp, AuthConfig} from "angular2-jwt";
import {CacheService} from "ionic-cache/ionic-cache";
import {
  PrivatePageGuard,
  PublicPageGuard,
  AdminPageGuard,
  RequestService,
  ReportService,
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
import {ErrorModule} from "./pages/error/error.module";
import {ErrorService} from "./service/error.service";
import {RequestProfileModule} from "./pages/request-profile/request-profile.module";
import {ReportModule} from "./pages/report/report.module";
import {ChartsModule} from "ng2-charts";
import {TopicService} from "./service/topic.service";
import {HistoryService} from "./service/history.service";
import {Ng2GoogleChartsModule} from "ng2-google-charts";
import {SuscribeService} from "./service/subscribe.service";

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    NavbarComponent,
    NoContentComponent,
    SideBarComponent,
    SideBarDirective,
  ],
  imports: [
    ChartsModule,
    ReportModule,
    ErrorModule,
    RequestProfileModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    Ng2GoogleChartsModule,
    HttpModule,
    GravatarModule,
    ToastModule.forRoot(),
    RouterModule.forRoot(appRoutes),
  ],
  providers: [
    ErrorService,
    TopicService,
    HistoryService,
    UserService,
    RoleService,
    ReportService,
    AuthService,
    CacheService,
    EmployeeService,
    PrivatePageGuard,
    PublicPageGuard,
    AdminPageGuard,
    RequestService,
    RecoverService,
    SuscribeService,
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
