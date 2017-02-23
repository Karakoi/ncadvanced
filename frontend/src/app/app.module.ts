import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {HomeComponent} from "./home/home.component";
import {FooterComponent} from "./footer/footer.component";
import {RecoverComponent} from "./recover/recover.component";
import {RecoverService} from "./service/recover.service";
import {ToastModule} from "ng2-toastr";
import {NoContentComponent} from "./no-content/no-content.component";
import {appRoutes} from "./app.routes";
import {RouterModule} from "@angular/router";
import {AuthService} from "./service/auth.service";
import {JsonHttp} from "./service/json-http.service";
import {UserService} from "./service/user.service";
import {NavbarComponent} from "./navbar/navbar.component";
import {ProfileComponent} from "./profile/profile.component";
import {PrivatePageGuard} from "./service/private-page.guard";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    FooterComponent,
    RecoverComponent,
    NoContentComponent,
    NavbarComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpModule,
    ToastModule.forRoot(),
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    RecoverService,
    AuthService,
    JsonHttp,
    UserService,
    PrivatePageGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
