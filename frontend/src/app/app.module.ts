import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {ToastModule} from "ng2-toastr";
import {appRoutes} from "./app.routes";
import {AppComponent} from "./app.component";
import {FormsModule} from "@angular/forms";
import {SideBarDirective, RequestFormDirective} from "./directive/barrel";
import {
  FooterComponent,
  HomeComponent,
  NavbarComponent,
  NoContentComponent,
  RequestComponent,
  SideBarComponent,
  WelcomeComponent
} from "./components/barrel";
import {PrivatePageGuard, PublicPageGuard, UserService, JsonHttp, AuthService, RecoverService} from "./service/barrel";
import {RequestFormModule} from "./pages/request-form/request-form.module";

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HomeComponent,
    NavbarComponent,
    NoContentComponent,
    RequestComponent,
    SideBarComponent,
    WelcomeComponent,
    SideBarDirective,
    RequestFormDirective
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RequestFormModule,
    ToastModule.forRoot(),
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    RecoverService,
    AuthService,
    JsonHttp,
    UserService,
    PrivatePageGuard,
    PublicPageGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
