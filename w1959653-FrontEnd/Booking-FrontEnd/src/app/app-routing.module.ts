import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { UserLoginComponent } from "./Pages/user-login/user-login.component";
import { ConfigurationSetupComponent } from "./Pages/configuration-setup/configuration-setup.component";
import { UserRegistrationComponent } from "./Pages/user-registration/user-registration.component";
import { VendorDashbordComponent } from "./Pages/vendor-dashbord/vendor-dashbord.component";
import { CustomerDashboardComponent } from "./Pages/customer-dashboard/customer-dashboard.component";

const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: "full" },
  { path: "login", component: UserLoginComponent },
  { path: "configuration-setup", component: ConfigurationSetupComponent },
  { path: "user-registration", component: UserRegistrationComponent },
  { path: "vendor-dashboard", component: VendorDashbordComponent },
  { path: "dashboard", component: CustomerDashboardComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
