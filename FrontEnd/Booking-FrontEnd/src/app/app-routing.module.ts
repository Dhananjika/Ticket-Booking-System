import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { UserLoginComponent } from "./Pages/user-login/user-login.component";
import { ConfigurationSetupComponent } from "./Pages/configuration-setup/configuration-setup.component";
import { UserRegistrationComponent } from "./Pages/user-registration/user-registration.component";
import { DashboardComponent } from "./Pages/dashboard/dashboard.component";
import { VendorGuard } from "./Security/vendor.guard";
import { CustomerGuard } from "./Security/customer.guard";

const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: "full" },
  { path: "login", component: UserLoginComponent },
  { path: "user-registration", component: UserRegistrationComponent },
  { path: "dashboard", component: DashboardComponent },
  {
    path: "configuration-setup",
    component: ConfigurationSetupComponent,
    canActivate: [VendorGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
