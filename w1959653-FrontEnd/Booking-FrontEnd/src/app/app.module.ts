import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";

import { AppComponent } from "./app.component";
import { UserLoginComponent } from "./Pages/user-login/user-login.component";
import { UserRegistrationComponent } from "./Pages/user-registration/user-registration.component";

import { AppRoutingModule } from "./app-routing.module";
import { ConfigurationSetupComponent } from "./Pages/configuration-setup/configuration-setup.component";
import { DashboardComponent } from "./Pages/dashboard/dashboard.component";
import { NavbarComponent } from "./Pages/navbar/navbar.component";
import { AuthService } from "./Service/AuthService/auth-service.service";
import { VendorGuard } from "./Security/vendor.guard";
import { CustomerGuard } from "./Security/customer.guard";

@NgModule({
  declarations: [
    AppComponent,
    UserLoginComponent,
    UserRegistrationComponent,
    ConfigurationSetupComponent,
    DashboardComponent,
    NavbarComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule],
  providers: [AuthService, VendorGuard, CustomerGuard],
  bootstrap: [AppComponent],
})
export class AppModule {}
