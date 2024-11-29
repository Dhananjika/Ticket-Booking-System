import { Injectable } from "@angular/core";
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
} from "@angular/router";
import { Observable } from "rxjs";
import { AuthService } from "../Service/AuthService/auth-service.service";

@Injectable({
  providedIn: "root",
})
export class CustomerGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ): Observable<boolean> | Promise<boolean> | boolean {
    console.log(
      "CustomerGuard activated, role:",
      this.authService.getUserRole(),
    );
    if (this.authService.getUserRole() === "Customer") {
      return true;
    } else {
      // Redirect unauthorized users to login
      this.router.navigate(["/login"]);
      return false;
    }
  }
}
