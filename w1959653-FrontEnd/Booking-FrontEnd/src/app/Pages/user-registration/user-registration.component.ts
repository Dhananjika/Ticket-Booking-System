import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { UserModule } from "src/app/module/user-login/user-module";
import { HttpClient } from "@angular/common/http";
import { TicketServiceService } from "src/app/Service/TicketService/ticket-service.service";
import { Router } from "@angular/router";
import { HttpParams } from "@angular/common/http";
import { AuthService } from "../../Service/AuthService/auth-service.service";

@Component({
  selector: "app-user-registration",
  templateUrl: "./user-registration.component.html",
  styleUrls: ["./user-registration.component.css"],
})
export class UserRegistrationComponent implements OnInit {
  useEmail: boolean;
  registerUser: UserModule;
  successMessage: string;
  errorMessage: string;

  constructor(
    private http: HttpClient,
    private env: TicketServiceService,
    private route: Router,
    private auth: AuthService,
  ) {
    this.useEmail = false;
    this.registerUser = new UserModule();
    this.successMessage = null;
    this.errorMessage = null;
  }

  ngOnInit() {}

  submitForm() {
    console.log(this.registerUser);
    debugger;

    if (this.registerUser.userType === "Vendor") {
      const params = new HttpParams()
        .set("username", this.registerUser.username)
        .set("password", this.registerUser.password)
        .set("vendorID", this.registerUser.vendorID);

      const url = "login/vendorRegister";

      this.env.sendPostRequestWithParams(url, params).subscribe(
        (response) => {
          this.auth.setUserRole(this.registerUser.userType);
          this.env.setUsername(
            this.registerUser.username,
            this.registerUser.password,
          );
          console.log("Response:", response);
          this.route.navigate(["/dashboard"]);
        },
        (error) => {
          console.error("Error:", error);
        },
      );
    } else if (this.registerUser.userType == "Customer") {
      const url = "login/customerRegister";

      const params = new HttpParams()
        .set("username", this.registerUser.username)
        .set("password", this.registerUser.password)
        .set("name", this.registerUser.name)
        .set("email", this.registerUser.email);

      this.env.sendPostRequestWithParams(url, params).subscribe(
        (response) => {
          this.auth.setUserRole(this.registerUser.userType);
          this.env.setUsername(
            this.registerUser.username,
            this.registerUser.password,
          );
          console.log("Response:", response);
          this.route.navigate(["/dashboard"]);
        },
        (error) => {
          console.error("Error:", error);
        },
      );
    }
  }

  changeUserName() {
    if (this.useEmail) {
      this.registerUser.username = this.registerUser.email;
    } else {
      this.registerUser.username = null;
    }
  }
}
