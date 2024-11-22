import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { UserModule } from "src/app/module/user-login/user-module";
import { HttpClient } from "@angular/common/http";
import { TicketServiceService } from "src/app/Service/ticket-service.service";
import { Router } from "@angular/router";
import { HttpParams } from "@angular/common/http";

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
    private http: HttpClient,
    private env: TicketServiceService,
    private route: Router,
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

    this.http
      .post<string>(this.env.BaseURL + "login/userLogin", null, {
        params: params,
        responseType: "text" as "json",
      })
      .subscribe(
        (value) => {
          if (value === "Vendor") {
            this.route.navigate(["/vendor-dashboard"]);
          } else if (value === "Customer") {
            this.route.navigate(["/dashboard"]);
          } else {
            this.errorMessage = value;
          }
        },
        (error) => {
          console.error("Error:", error); // Log the error for debugging
          this.errorMessage = "Error connecting to the server."; // Show generic error if the server fails
        },
      );
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  closeBox() {
    this.successMessage = null; // Hide the box when OK is clicked
  }
}
