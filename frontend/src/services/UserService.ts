import UserSignupRequest from "@/entities/UserSignupRequest";
import UserLoginRequest from "@/entities/UserLoginRequest";


export default abstract class UserService {
  private static endpoint = "/api";


  static async signup(user: UserSignupRequest): Promise<string> {

    return fetch(`${this.endpoint}/users/signup`, {
      keepalive: true,
      method: "POST",
      body: JSON.stringify(user),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error(res.statusText);
        return res.json();
      })
      .then((res) => res.serviceToken);
  }

  static async login(user: UserLoginRequest): Promise<string> {

    return fetch(`${this.endpoint}/users/login`, {
      keepalive: true,
      method: "POST",
      body: JSON.stringify(user),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error(res.statusText);
        return res.json();
      })
      .then((res) => res.serviceToken);
  }
}
