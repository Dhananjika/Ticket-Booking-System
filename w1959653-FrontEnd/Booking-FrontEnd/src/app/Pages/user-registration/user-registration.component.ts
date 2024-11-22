import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { UserModule } from "src/app/module/user-login/user-module";
import { HttpClient } from "@angular/common/http";
import { TicketServiceService } from "src/app/Service/ticket-service.service";
import { Router } from "@angular/router";
import { HttpParams } from "@angular/common/http";

@Component({
  selector: "app-user-registration",
  templateUrl: "./user-registration.component.html",
  styleUrls: ["./user-registration.component.css"],
})
export class UserRegistrationComponent implements OnInit {
  useEmail: boolean;
  registerUser: UserModule;

  constructor(
    private http: HttpClient,
    private env: TicketServiceService,
    private route: Router,
  ) {
    this.useEmail = false;
    this.registerUser = new UserModule();
  }

  ngOnInit() {}

  submitForm() {
    console.log(this.registerUser);

    this.http
      .post<string>(this.env.BaseURL + "login/userLogin", this.registerUser)
      .subscribe(
        (value) => {
          if (value === "Vendor") {
            this.route.navigate(["/vendor-dashboard"]);
          } else if (value === "Customer") {
            this.route.navigate(["/dashboard"]);
          } else {
          }
        },
        (error) => {
          console.error("Error:", error); // Log the error for debugging
        },
      );
  }

  changeUserName() {
    if (this.useEmail) {
      this.registerUser.username = this.registerUser.email;
    } else {
      this.registerUser.username = null;
    }
  }
}
