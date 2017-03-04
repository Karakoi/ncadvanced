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
import {LoginModule, ProfileModule, RecoverModule, SignupModule, UserTableModule} from "./pages/barrel";
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
import {ErrorModule} from "./pages/error/error.module";
import {UserProfileModule} from "./pages/user-profile/user-profile.module";
import {ErrorService} from "./service/error.service";

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
    ErrorModule,
    UserProfileModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    ToastModule.forRoot(),
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    ErrorService,
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
