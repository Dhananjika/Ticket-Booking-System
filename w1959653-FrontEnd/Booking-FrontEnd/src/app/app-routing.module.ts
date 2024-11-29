import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { UserLoginComponent } from "./Pages/user-login/user-login.component";
import { ConfigurationSetupComponent } from "./Pages/configuration-setup/configuration-setup.component";
import { UserRegistrationComponent } from "./Pages/user-registration/user-registration.component";
import { VendorDashbordComponent } from "./Pages/vendor-dashbord/vendor-dashbord.component";
import { CustomerDashboardComponent } from "./Pages/customer-dashboard/customer-dashboard.component";
import { VendorGuard } from "./Security/vendor.guard";
import { CustomerGuard } from "./Security/customer.guard";

const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: "full" },
  { path: "login", component: UserLoginComponent },
  { path: "user-registration", component: UserRegistrationComponent },
  {
    path: "configuration-setup",
    component: ConfigurationSetupComponent,
    canActivate: [VendorGuard],
  },
  {
    path: "vendor-dashboard",
    component: VendorDashbordComponent,
    canActivate: [VendorGuard],
  },
  {
    path: "dashboard",
    component: CustomerDashboardComponent,
    canActivate: [CustomerGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
