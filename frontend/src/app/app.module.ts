import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {ToastModule} from "ng2-toastr";
import {appRoutes} from "./app.routes";
import {AppComponent} from "./app.component";
import {WelcomeModule, HomeModule} from "./pages/index";
import {FooterComponent, NoContentComponent, NavbarComponent, UserTable, SideBarComponent} from "./components/index";
import {PrivatePageGuard, PublicPageGuard, UserService, JsonHttp, AuthService, RecoverService} from "./service/index";
import {SideBarDirective} from "./directive/sidebar.directive";

@NgModule({
  declarations: [
    AppComponent,
    SideBarComponent,
    SideBarDirective,
    FooterComponent,
    NoContentComponent,
    NavbarComponent,
    UserTable
  ],
  imports: [
    BrowserModule,
    HttpModule,
    WelcomeModule,
    HomeModule,
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
