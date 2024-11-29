import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "src/app/Service/AuthService/auth-service.service";
import { TicketServiceService } from "src/app/Service/TicketService/ticket-service.service";

@Component({
  selector: "app-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.css"],
})
export class NavbarComponent implements OnInit {
  username: string;
  routeLink: string;

  constructor(
    private env: TicketServiceService,
    private router: Router,
    private authService: AuthService,
  ) {}

  ngOnInit() {
    this.env.username$.subscribe((username: string) => {
      this.username = username;
    });

    if (this.authService.getUserRole() === "Vendor") {
      this.routeLink = "/vendor-dashboard";
    } else if (this.authService.getUserRole() === "Customer") {
      this.routeLink = "/dashboard";
    }
  }

  Logout(): void {
    this.env.cleanUsername(this.username);
    this.authService.removeUserRole();
    console.log("role:", this.authService.getUserRole());
    this.router.navigate(["/login"]);
  }
}
