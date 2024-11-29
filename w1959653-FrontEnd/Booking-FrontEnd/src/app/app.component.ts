import { Component } from "@angular/core";
import { NavigationEnd, Router } from "@angular/router";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent {
  title = "Booking-FrontEnd";
  applyNavbar: boolean;

  constructor(private route: Router) {
    this.applyNavbar = true;
  }

  isRestrictedPage(): boolean {
    const restrictedRoutes = ["/login", "/user-registration"];
    return restrictedRoutes.includes(this.route.url);
  }
}
