import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {ToastModule} from "ng2-toastr";
import {appRoutes} from "./app.routes";
import {AppComponent} from "./app.component";
import {SideBarDirective} from "./directive/sidebar.directive";
import {FormsModule} from '@angular/forms'
import {
  FooterComponent,
  HomeComponent,
  NavbarComponent,
  NoContentComponent,
  RequestComponent,
  SideBarComponent,
  WelcomeComponent
} from "./components/index";
import {PrivatePageGuard, PublicPageGuard, UserService, JsonHttp, AuthService, RecoverService} from "./service/index";
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
    SideBarDirective
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
