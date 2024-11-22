import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";

import { AppComponent } from "./app.component";
import { UserLoginComponent } from "./Pages/user-login/user-login.component";
import { UserRegistrationComponent } from "./Pages/user-registration/user-registration.component";

import { AppRoutingModule } from "./app-routing.module";
import { ConfigurationSetupComponent } from "./Pages/configuration-setup/configuration-setup.component";
import { VendorDashbordComponent } from "./Pages/vendor-dashbord/vendor-dashbord.component";
import { CustomerDashboardComponent } from "./Pages/customer-dashboard/customer-dashboard.component";

@NgModule({
  declarations: [
    AppComponent,
    UserLoginComponent,
    UserRegistrationComponent,
    ConfigurationSetupComponent,
    VendorDashbordComponent,
    CustomerDashboardComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
