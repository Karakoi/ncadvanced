import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {HttpModule, Http, RequestOptions} from "@angular/http";
import {RouterModule} from "@angular/router";
import {ToastModule} from "ng2-toastr";
import {appRoutes} from "./app.routes";
import {AppComponent} from "./app.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SideBarDirective} from "./directive/barrel";
import {AuthHttp, AuthConfig} from "angular2-jwt";
import {
  PrivatePageGuard,
  PublicPageGuard,
  UserService,
  AuthService,
  RecoverService,
  AdminPageGuard,
  RequestService
} from "./service/barrel";
import {
  FooterComponent,
  NavbarComponent,
  NoContentComponent,
  SideBarComponent,
  WelcomeComponent
} from "./components/barrel";

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    NavbarComponent,
    NoContentComponent,
    SideBarComponent,
    WelcomeComponent,
    SideBarDirective
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    ToastModule.forRoot(),
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    RecoverService,
    AuthService,
    UserService,
    PrivatePageGuard,
    PublicPageGuard,
    AdminPageGuard,
    RequestService,
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
