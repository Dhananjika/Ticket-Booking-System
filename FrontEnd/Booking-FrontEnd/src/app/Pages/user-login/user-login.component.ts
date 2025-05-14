import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { UserModule } from "src/app/module/user-login/user-module";
import { HttpClient } from "@angular/common/http";
import { TicketServiceService } from "src/app/Service/TicketService/ticket-service.service";
import { Router } from "@angular/router";
import { HttpParams } from "@angular/common/http";
import { AuthService } from "../..//Service/AuthService/auth-service.service";

@Component({
  selector: "app-user-login",
  templateUrl: "./user-login.component.html",
  styleUrls: ["./user-login.component.css"],
})
export class UserLoginComponent implements OnInit {
  loginModule: UserModule;
  showPassword: boolean;
  successMessage: String;
  errorMessage: String;

  constructor(
    private env: TicketServiceService,
    private route: Router,
    private auth: AuthService,
  ) {
    this.loginModule = new UserModule();
    this.showPassword = false;
    this.successMessage = null;
    this.errorMessage = null;
  }

  ngOnInit() {}

  submitLogin() {
    console.log(this.loginModule);

    const params = new HttpParams()
      .set("username", this.loginModule.username)
      .set("password", this.loginModule.password);

    const url = "login/userLogin";

    this.env.sendPostRequestWithParams(url, params).subscribe(
      (response) => {
        if (response === "Vendor" || response === "Customer") {
          this.auth.setUserRole(response);
          this.env.setUsername(
            this.loginModule.username,
            this.loginModule.password,
          );
          this.route.navigate(["/dashboard"]);
        } else {
          this.errorMessage = response;
        }
      },
      (error) => {
        console.error("Error:", error);
      },
    );
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }
}
