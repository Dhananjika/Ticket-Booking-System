import { HttpParams } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { UserModule } from "src/app/module/user-login/user-module";
import { AuthService } from "src/app/Service/AuthService/auth-service.service";
import { TicketServiceService } from "src/app/Service/TicketService/ticket-service.service";
declare var $: any;

@Component({
  selector: "app-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.css"],
})
export class NavbarComponent implements OnInit {
  username: string;
  password: string;
  routeLink: string;
  userModule: UserModule;

  constructor(
    private env: TicketServiceService,
    private router: Router,
    private authService: AuthService,
  ) {
    this.userModule = new UserModule();
  }

  ngOnInit() {
    this.env.username$.subscribe((username: string) => {
      console.log("Username emitted:", username);
      this.username = username;
    });

    this.env.password$.subscribe((password: string) => {
      console.log("Password emitted:", password);
      this.password = password;
    });

    console.log(this.username);
    console.log(this.password);
    console.log(this.authService.getUserRole());

    if (this.authService.getUserRole() == "Vendor") {
      const url = "login/getVendorDetails";
      const params = new HttpParams()
        .set("username", this.username)
        .set("password", this.password);

      this.env
        .sendGetRequestWithParamsResponseEntity<UserModule>(url, params)
        .subscribe((response) => {
          console.log(response);
          this.userModule = response;
        });
    } else if (this.authService.getUserRole() == "Customer") {
      const url = "login/getCustomerDetails";
      const params = new HttpParams()
        .set("username", this.username)
        .set("password", this.password);

      this.env
        .sendGetRequestWithParamsResponseEntity<UserModule>(url, params)
        .subscribe((response) => {
          console.log(response);
          this.userModule = response;
        });
    }
  }

  Logout(): void {
    this.env.cleanUsername();
    this.authService.removeUserRole();
    console.log("role:", this.authService.getUserRole());
    this.router.navigate(["/login"]);
  }

  showUser() {
    const modal = document.getElementById("userDetailsModal");
    if (modal) {
      $(modal).modal("show");
    }
  }

  removeAccount() {
    const url = "login/removeAccount";
    const params = new HttpParams().set(
      "userID",
      this.userModule.userID.toString(),
    );

    this.env.sendDeleteRequestWithParam(url, params).subscribe((response) => {
      if (response === "Successfully removed the account") {
        this.Logout();
      }
    });
  }
}
