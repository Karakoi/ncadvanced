import {BrowserModule} from "@angular/platform-browser";
import {NgModule, OnInit} from "@angular/core";
import {HttpModule, Http, RequestOptions} from "@angular/http";
import {RouterModule, ActivatedRoute} from "@angular/router";
import {ToastModule} from "ng2-toastr";
import {appRoutes} from "./app.routes";
import {AppComponent} from "./app.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SideBarDirective} from "./directive/barrel";
import {AuthHttp, AuthConfig} from "angular2-jwt";
import {LoginModule, ProfileModule, RecoverModule, SignupModule, UserTableModule} from "./pages/barrel";
import {
  PrivatePageGuard,
  PublicPageGuard,
  UserService,
  AuthService,
  RecoverService,
  AdminPageGuard
} from "./service/barrel";
import {
  FooterComponent,
  NavbarComponent,
  NoContentComponent,
  RequestDetailsComponent,
  SideBarComponent,
  WelcomeComponent
} from "./components/barrel";

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    NavbarComponent,
    NoContentComponent,
    RequestDetailsComponent,
    SideBarComponent,
    WelcomeComponent,
    SideBarDirective
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    LoginModule,
    ProfileModule,
    RecoverModule,
    SignupModule,
    UserTableModule,
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
