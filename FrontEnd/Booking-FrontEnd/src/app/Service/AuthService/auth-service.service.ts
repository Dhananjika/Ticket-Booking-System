import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private userRole: string | null = null;

  setUserRole(role: string): void {
    this.userRole = role;
    sessionStorage.setItem("role", role);
  }

  getUserRole(): string | null {
    return this.userRole || sessionStorage.getItem("role");
  }

  removeUserRole(): void {
    this.userRole = null;
    sessionStorage.removeItem("role");
  }
}
